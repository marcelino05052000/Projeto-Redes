package jogo.mensagem;

import jogo.Lado;

public class Avisos {

	public static final Mensagem criaMensagemJogada(Lado lado, int valorJogada) {
		Mensagem msg = new Mensagem(new int[Mensagem.MSG_SIZE]);
		
		msg.getCampos()[0] = TipoMensagem.Msg_Jogada.ordinal();
		msg.getCampos()[1] = lado.ordinal();
		msg.getCampos()[2] = valorJogada;

		return msg;
	}
	
	public static final Mensagem criaMensagemEscolhaLado(Lado lado) {
		Mensagem msg = new Mensagem(new int[Mensagem.MSG_SIZE]);

		msg.getCampos()[0] = TipoMensagem.Msg_Escolha_Lado.ordinal();
		msg.getCampos()[1] = lado.ordinal();

		return msg;
	}

	public static final Mensagem criaMensagemErro(Erros erro) {
		Mensagem msg = new Mensagem(new int[Mensagem.MSG_SIZE]);

		msg.getCampos()[0] = TipoMensagem.Msg_Erro.ordinal();
		msg.getCampos()[1] = erro.ordinal();

		return msg;
	}

	public static final Mensagem criaMensagemVencedor(Lado lado) {
		Mensagem msg = new Mensagem(new int[Mensagem.MSG_SIZE]);

		msg.getCampos()[0] = TipoMensagem.Msg_Vencedor.ordinal();
		msg.getCampos()[1] = lado.ordinal();

		return msg;
	}
	
}
