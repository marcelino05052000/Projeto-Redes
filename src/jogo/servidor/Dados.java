package jogo.servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jogo.Conexao;
import jogo.Lado;
import jogo.Variaveis;
import servidor.preset;

public class Dados {
	
	private List<Conexao> clientes;
	private Variaveis estado;
	private Map<Conexao,Lado> mapaLados;
	private Map<Conexao,Integer> mapaJogadas;
	
	public Dados(Variaveis estado) {
		super();
		
		this.estado = estado;
		
		this.clientes = new ArrayList<>(preset.Quant_Jogadores);
		
		this.mapaLados = new HashMap<>();
		this.mapaJogadas = new HashMap<>();
	}
	
	public List<Conexao> getClientes() {
		return clientes;
	}

	public void setClientes(List<Conexao> clientes) {
		this.clientes = clientes;
	}
	
	public Variaveis getEstado() {
		return estado;
	}

	public void setEstado(Variaveis estado) {
		this.estado = estado;
	}

	public Map<Conexao, Lado> getMapaLados() {
		return mapaLados;
	}

	public void setMapaLados(Map<Conexao, Lado> mapaPersonagensJogadores) {
		this.mapaLados = mapaPersonagensJogadores;
	}
	
	public Map<Conexao, Integer> getMapaJogadas() {
		return mapaJogadas;
	}

	public void setMapaJogadas(Map<Conexao, Integer> mapaJogadasJogadores) {
		this.mapaJogadas = mapaJogadasJogadores;
	}
	
	public boolean todosClientesConectados() {
		return (this.clientes.size() == preset.Quant_Jogadores);
	}
	
	public boolean todosLadosEscolhidos() {
		return (mapaLados.size() == preset.Quant_Jogadores);
	}
	
	public boolean todasJogadasRealizadas() {
		return (mapaJogadas.size() == preset.Quant_Jogadores); 
	}
	
	

}
