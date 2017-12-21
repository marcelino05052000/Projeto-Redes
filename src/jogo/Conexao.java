package jogo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import jogo.mensagem.Mensagem;

public class Conexao {
	
	private Socket socket;
	private Entrada entrada;
	private Saida saida;
	private int id = 0;

	public Conexao(Socket socket) throws IOException {
		this.socket = socket;

		this.saida = new Saida(socket);
		this.entrada = new Entrada(socket);
		
		this.saida.start();
		this.entrada.start();
	}

	public Conexao(Socket socket, int id) throws IOException {
		this(socket);
		this.id = id;
	}

	public Socket getSocket() {
		return socket;
	}

	public int getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "socket("+id+"){"+ socket.getInetAddress().getHostAddress() + ":" + socket.getPort()+ "}";
	}

	public synchronized Mensagem lerMensagem(){
		return this.entrada.lerMensagem();
	}
	
	public synchronized Mensagem lerMensagem(long timeoutMs) throws InterruptedException{

		Mensagem msg = null;
		long ini = System.currentTimeMillis();
		
		while(((msg = this.entrada.lerMensagem()) == null) && (System.currentTimeMillis() - ini < timeoutMs)) {
			Thread.sleep(50);
		}
		return msg;
	}

	public synchronized void enviarMensagem(Mensagem mensagem){
		this.saida.enviarMensagem(mensagem);
	}
	
	public synchronized void limparSaida() throws IOException {
		this.saida.limparSaida();
	}
	
	public synchronized void limparEntrada() {
		this.entrada.limparEntrada();
	}
	
	public synchronized void limpar() throws IOException {
		this.limparEntrada();
		this.limparSaida();
	}


	public class Entrada extends Thread{

		private ObjectInputStream entrada;
		private Queue<Mensagem> fila;

		public Entrada(Socket socket) throws IOException {
			this.entrada = new ObjectInputStream(socket.getInputStream());
			this.fila = new ArrayBlockingQueue<>(10);
		}

		@Override
		public void run() {

			try {

				while(true) {

					Mensagem m = (Mensagem) this.entrada.readObject();
					this.fila.offer(m);

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		public Mensagem lerMensagem() {
			return this.fila.poll();
		}
		
		public void limparEntrada() {
			this.fila.clear();
		}
	}

	public class Saida extends Thread{

		private ObjectOutputStream saida;
		private Queue<Mensagem> fila;

		public Saida(Socket socket) throws IOException {
			this.saida = new ObjectOutputStream(socket.getOutputStream());
			this.fila = new ArrayBlockingQueue<>(10);
			
			this.saida.flush();
		}

		@Override
		public void run() {

			try {

				while(true) {

					if(this.fila.size() > 0) {
						Mensagem m = this.fila.remove();
						this.saida.writeObject(m);
					}else {
						Thread.sleep(100);
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void enviarMensagem(Mensagem m) {
			this.fila.offer(m);	
		}
		
		public void limparSaida() throws IOException {
			this.fila.clear();
			this.saida.flush();
		}

	}

}
