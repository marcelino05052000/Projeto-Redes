package Conversa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BatePapoServidor {
	
	public static void main(String[] args) {
		
		try {
			int porta = 12345;
			ServerSocket servidor = new ServerSocket(porta);
			System.out.println("Servidor ouvindo a porta " + porta);

			final int quantidadeClientes = 2;
			
			Socket[] clientes = new Socket[quantidadeClientes];
			ObjectOutputStream[] saida = new ObjectOutputStream[quantidadeClientes];
			ObjectInputStream[] entrada = new ObjectInputStream[quantidadeClientes];

			for(int i = 0 ; i < quantidadeClientes ; i++){
				clientes[i] = servidor.accept();
				
				System.out.println("Cliente conectado: " + clientes[i].getInetAddress().getHostAddress()+":"+clientes[i].getPort());

				entrada[i] = new ObjectInputStream(clientes[i].getInputStream());
				saida[i] = new ObjectOutputStream(clientes[i].getOutputStream());
				saida[i].flush();
			}
			
			while(true) {
				
				for(int i = 0 ; i < clientes.length ; i++){
					
					if(entrada[i].available() == 0){
						continue;
					}

					String msg = entrada[i].readObject().toString();
					
					for(int k = 0 ; k < clientes.length ; k++){
						if(i != k){
							saida[k].writeObject(msg);
						}
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {	} 
		
	}

}
