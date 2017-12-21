package jogo.mensagem;

import jogo.Lado;

public class Mensagem {
	
	private static final long serialVersionUID = -3056642974461498964L;
	
	public static final int MSG_SIZE = 3;
	
	private int[] campos;
	
	public Mensagem(int[] campos) {
		this.campos = campos;
	}
	
	public int[] getCampos() {
		return this.campos;
	}
	
	public void setCampos(int[] campos) {
		this.campos = campos;
	}

	public String toString() {

		TipoMensagem tipo = TipoMensagem.values()[this.campos[0]];

		String str = "[" + tipo + ", ";

		switch (tipo) {
		case Msg_Jogada:
		case Msg_Cenario:
		default:
			str  += this.campos[1];
			break;
		case Msg_Escolha_Lado:
		case Msg_Vencedor:
			str  += Lado.values()[this.campos[1]];
			break;
		case Msg_Erro:
			str  += Erros.values()[this.campos[1]];
			break;
		}

		str += "]";

		return str;
	}

	public boolean isMensagemJogada() {
		return this.campos[0] == TipoMensagem.Msg_Jogada.ordinal();
	}

	public boolean isMensagemEscolhaLado() {
		return this.campos[0] == TipoMensagem.Msg_Escolha_Lado.ordinal();
	}

	public boolean isMensagemTabuleiro() {
		return this.campos[0] == TipoMensagem.Msg_Cenario.ordinal();
	}

	public boolean isMensagemVencedor() {
		return this.campos[0] == TipoMensagem.Msg_Vencedor.ordinal();
	}

	public boolean isMensagemErro() {
		return this.campos[0] == TipoMensagem.Msg_Erro.ordinal();
	}

	public Lado getCampoLado() {
		if(!isMensagemEscolhaLado() && !isMensagemVencedor() && !isMensagemJogada()) 
			return null;

		return Lado.values()[this.campos[1]];
	}

	public Erros getCampoTipoErro() {
		if(!isMensagemErro()) 
			return null;

		return Erros.values()[this.campos[1]];
	}

	public int getCampoJogada() {
		return this.campos[2];
	}

	public int getCampoTabuleiro() {
		return this.campos[1];
	}
}
