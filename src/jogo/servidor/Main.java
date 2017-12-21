package jogo.servidor;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import jogo.Conexao;
import jogo.Lado;
import jogo.Variaveis;
import jogo.mensagem.Avisos;
import jogo.mensagem.Erros;
import jogo.mensagem.Mensagem;
import servidor.preset;

public class Main {

	public static void main(String[] args) {

		Dados dados = new Dados(Variaveis.Servidor_Esperando_Conexão);
		int contadorPrint = -2;

		try {

			ServerSocket servidor = new ServerSocket(preset.Porta);
			System.out.println("Servidor TCP ouvindo a porta " + preset.Porta);
			int idConexao = 0;

			while(true) {

				switch (dados.getEstado()) {
				case Servidor_Esperando_Conexão: {

					Conexao novoCliente = new Conexao(servidor.accept(), idConexao++);

					dados.getClientes().add(novoCliente);

					System.out.println("S#: Jogador "+(dados.getClientes().size())+" conectado. IP:porta={" + novoCliente.getSocket().getInetAddress().getHostAddress() + ":" + novoCliente.getSocket().getPort()+ "}");

					if(!dados.todosClientesConectados()) {

					} else {
						dados.setEstado(Variaveis.Servidor_Esperando_Lado);
						contadorPrint = -1;

					}
					break;
				}
				case Servidor_Esperando_Lado: {

					for(Conexao cliente : dados.getClientes()) {

						Mensagem msg = cliente.lerMensagem();

						if(msg != null) {

							if(msg.isMensagemEscolhaLado()) {

								Lado ladoEscolhido = msg.getCampoLado();
								System.out.println("Cliente " +cliente+ " escolheu seu lado: " + ladoEscolhido);

								if((!dados.getMapaLados().containsValue(ladoEscolhido)) || (dados.getMapaLados().get(cliente) == ladoEscolhido)) {

									dados.getMapaLados().put(cliente, ladoEscolhido);

									cliente.enviarMensagem(Avisos.criaMensagemEscolhaLado(ladoEscolhido));
									System.out.println("Criou mensagem OK escolha lado " + ladoEscolhido + " para o cliente " + cliente);

								}else {

									cliente.enviarMensagem(Avisos.criaMensagemErro(Erros.Lado_Ja_Escolhido));
									System.out.println("Criou mensagem ERRO escolha lado " + ladoEscolhido + " para o cliente " + cliente+". Lado já escolhido!!!");

								}

								System.out.println("mapaLados="+dados.getMapaLados().entrySet());
							}

						} 

					}

					if(dados.todosLadosEscolhidos()) {

						dados.setEstado(Variaveis.Servidor_Esperando_Jogada);
						contadorPrint = -1;

					} else {
						Thread.sleep(100);
					}

					break;
				}
				case Servidor_Esperando_Jogada: {

					for(Conexao cliente : dados.getClientes()) {

						Mensagem msg = cliente.lerMensagem();

						if(msg != null) {

							if(msg.isMensagemJogada()) {

								int valorJogada = msg.getCampoJogada();

								if(valorJogada >= 0) {

									dados.getMapaJogadas().put(cliente, valorJogada);

									Lado lado = dados.getMapaLados().get(cliente);

									cliente.enviarMensagem(Avisos.criaMensagemJogada(lado, valorJogada));

								}else {

									cliente.enviarMensagem(Avisos.criaMensagemErro(Erros.Jogada_Invalida));
								}

							}

						} else {
							Thread.sleep(100);		
						}

					}

					if(dados.todasJogadasRealizadas()) {

						for(Conexao cliente : dados.getClientes()) {

							for(Conexao clienteJogada : dados.getClientes()) {

								if(!cliente.equals(clienteJogada)) {

									Lado lado = dados.getMapaLados().get(clienteJogada);
									int jogada = dados.getMapaJogadas().get(clienteJogada);

									cliente.enviarMensagem(Avisos.criaMensagemJogada(lado, jogada));

								}

							}

						}

						dados.setEstado(Variaveis.Servidor_Analizando_Jogadas);
						contadorPrint = -1;
					}

					break;
				}
				case Servidor_Analizando_Jogadas: {

					int soma = 0;
					for(Integer jogada : dados.getMapaJogadas().values()) {
						soma += jogada;
					}

					Conexao clienteVencedor = null;
					for(Conexao cliente : dados.getClientes()) {

						if(soma % 2 == 0) {

							if(dados.getMapaLados().get(cliente).equals(Lado.Par)) {
								clienteVencedor = cliente;
								break;
							}

						}else {

							if(dados.getMapaLados().get(cliente).equals(Lado.Impar)) {
								clienteVencedor = cliente;
								break;
							}
						}
					}

					for(Conexao cliente : dados.getClientes()) {

						Lado ladoVencedor = dados.getMapaLados().get(clienteVencedor);

						cliente.enviarMensagem(Avisos.criaMensagemVencedor(ladoVencedor));
					}

					dados.setEstado(Variaveis.Servidor_Acaba_Jogo);
					contadorPrint = -1;

					break;
				}
				case Servidor_Acaba_Jogo:
				default: {

					dados.setEstado(Variaveis.Servidor_Esperando_Lado);

					dados.getMapaJogadas().clear();
					dados.getMapaLados().clear();

					contadorPrint = -1;

					break;
				}

				}
			}

		} catch(BindException b) {
			System.out.println("Erro: a porta já está sendo utilizada");
		} catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
		} finally {

		} 	
	}
}
