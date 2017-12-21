package servidor.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import servidor.preset;

public class MainServidorTCPMultiThreaded {

	public static void main(String[] args) throws IOException {

		ServerSocket servidor = null;
		int contadorConexao = 0;
		Integer contadorPacotesRespondidos = new Integer(0);
		long millisStart = System.currentTimeMillis();

		try {
			servidor = new ServerSocket(preset.Porta);
			System.out.println("Servidor TCP ouvindo a porta " + preset.Porta);

			while(true) {
				Socket cliente = servidor.accept();
				
				new ServidorTCPThreaded(contadorConexao++, cliente, contadorPacotesRespondidos, millisStart).start();
			}

		} catch(Exception e) {
			System.out.println("Erro: " + e.getMessage());
			servidor.close();
		} finally {

		} 
	}

}
