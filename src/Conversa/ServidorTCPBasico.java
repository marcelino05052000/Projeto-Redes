package Conversa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;

public class ServidorTCPBasico {

	public static void main(String[] args) {
		
		try {
			int porta = 12345;
			ServerSocket servidor = new ServerSocket(porta);
			System.out.println("Servidor ouvindo a porta " + porta);

			int contador = 0;

			while(true) {
				
				Socket cliente = servidor.accept();
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress()+":"+cliente.getPort());

				ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				saida.flush();
				
				switch(contador++ % 4){

				case 0:
					saida.writeObject(new Date());					
					break;
					
				case 1:
					saida.writeObject("Tretas infinitas");
					break;
					
				case 2:
					LinkedList<String> lista = new LinkedList<String>();
					lista.add("A");
					lista.add("B");
					lista.add("C");
					saida.writeObject(lista);
					break;
					
				case 3:
					int[] array = {1,2,3,4,5};
					saida.writeObject(array);
					break;
				}
				
				entrada.close();
				saida.close();

				System.out.println("Cliente desconectado: " + cliente.getInetAddress().getHostAddress()+":"+cliente.getPort());
				cliente.close();
			}
				
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	} 

	}

}
