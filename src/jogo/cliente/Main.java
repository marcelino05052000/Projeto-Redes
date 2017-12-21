package jogo.cliente;

import java.awt.EventQueue;
import java.net.Socket;

import jogo.Conexao;
import jogo.Lado;
import jogo.Variaveis;
import jogo.mensagem.Erros;
import jogo.mensagem.Avisos;
import jogo.mensagem.Mensagem;
import servidor.preset;

public class Main {

	public static void main(String[] args) {

		final Tela tela = new Tela();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					tela.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		try {
			Variaveis estado = Variaveis.Cliente_Conectando_Ao_Servidor;
			Conexao conexao = null;
			Lado lado = null;

			while (true) {
				switch (estado) {
				case Cliente_Conectando_Ao_Servidor: {
					tela.configurarCamposInicioJogo();
					tela.setTextoEstado("Conectando ao servidor");

					conexao = new Conexao(new Socket(preset.Host, preset.Porta));

					System.out.println(
							"Jogador conectado. IP:porta={ " + conexao.getSocket().getInetAddress().getHostAddress()
									+ ":" + conexao.getSocket().getPort() + "}");

					estado = Variaveis.Cliente_Escolhendo_Lado;

					tela.setTextoEstado("Conectado com sucesso");
					Thread.sleep(300);

					break;
				}
				case Cliente_Escolhendo_Lado: {
					tela.setTextoEstado("Escolha seu lado");
					tela.liberaEscolhaLado(true);
					tela.habilitaEscolha(false);

					while (true) {
						lado = tela.getLadoEscolhido();

						if (lado == null) {
							Thread.sleep(100);
						} else {
							break;
						}
					}
					while (true) {

						conexao.enviarMensagem(Avisos.criaMensagemEscolhaLado(lado));

						Mensagem msg = conexao.lerMensagem(1000);

						if (msg != null) {

							if (msg.isMensagemEscolhaLado() && (msg.getCampoLado() == lado)) {

								estado = Variaveis.Cliente_Escolhendo_Jogada;

								break;

							} else if (msg.isMensagemErro() && (msg.getCampoTipoErro() == Erros.Lado_Ja_Escolhido)) {
								tela.mostraErro("Lado já foi escolhido por outro jogador. Por favor, escolha outro");
								tela.limparSelecaoLado();

								break;

							} else {
								conexao.limparEntrada();
							}

						} else {
							Thread.sleep(100);
						}

					}

					break;
				}
				case Cliente_Escolhendo_Jogada: {

					tela.setTextoEstado("Sua vez! Jogue!");
					tela.liberaEscolhaLado(false);
					tela.habilitaEscolha(true);

					int valorJogada = -1;

					while (true) {
						while (!tela.getJogadaRealizada()) {
							Thread.sleep(100);
						}

						try {
							valorJogada = Integer.parseInt(tela.getValorJogada());
						} catch (Exception e) {
							tela.mostraErro("Jogada inválida");
							tela.limpaEscolha();
						}

						if (valorJogada != -1)
							break;
					}

					while (true) {

						conexao.enviarMensagem(Avisos.criaMensagemJogada(lado, valorJogada));

						Mensagem msg = conexao.lerMensagem(1000);
						if (msg != null) {

							if (msg.isMensagemJogada() && (msg.getCampoJogada() == valorJogada)) {

								estado = Variaveis.Cliente_Esperando_Jogada_Adversaria;

								break;

							} else if (msg.isMensagemErro() && (msg.getCampoTipoErro() == Erros.Jogada_Invalida)) {

								tela.mostraErro("Jogada inválida");

								break;

							} else {
								conexao.limparEntrada();
							}

						} else {
							Thread.sleep(100);
						}

					}

					break;
				}
				case Cliente_Esperando_Jogada_Adversaria: {

					tela.setTextoEstado("Esperando jogada do adversário");
					tela.liberaEscolhaLado(false);
					tela.habilitaEscolha(false);

					Mensagem msg = conexao.lerMensagem(1000);

					if (msg != null) {

						if (msg.isMensagemJogada() && (msg.getCampoLado() != lado)) {

							tela.setLadoAdversaria(msg.getCampoJogada());
							System.out.println(
									"Adversário (" + msg.getCampoLado() + ") jogou: " + msg.getCampoJogada());

						} else if (msg.isMensagemVencedor()) {

							if (msg.getCampoLado() == lado) {
								tela.mostraInformação("Você venceu");

							} else if (msg.getCampoLado() == Lado.Empate) {
								tela.mostraInformação("Empate");

							} else {
								tela.mostraInformação("Você perdeu. Vencedor: " + msg.getCampoLado());

							}

							tela.configurarCamposInicioJogo();
							estado = Variaveis.Cliente_Escolhendo_Lado;
						}

					} else {
						Thread.sleep(100);
					}

					break;
				}
				default: {
					estado = Variaveis.Cliente_Escolhendo_Lado;
					break;
				}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			tela.mostraErro(e.getMessage());
		} finally {
		}
	}

}
