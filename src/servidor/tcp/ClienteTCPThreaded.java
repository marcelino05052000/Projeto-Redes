package servidor.tcp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import servidor.preset;

public class ClienteTCPThreaded extends Thread{

	private int id; 
	private String host;
	private int port;

	public ClienteTCPThreaded(int id, String host, int porta) {
		this.id = id;
		this.host = host;
		this.port = porta;
	}

	@Override
	public void run() {

		Socket target = null;
		
		try {

			target = new Socket(host, port);
			
			System.out.println("Cliente conectado a " + host+":"+port);
			
			ObjectInputStream entrada = new ObjectInputStream(target.getInputStream());
			OutputStream saida = new ObjectOutputStream(target.getOutputStream());

			byte[] buf = new byte[preset.BUFFER_SIZE];

			final byte[] req = new byte[]{preset.KEY};

			while(true){

				saida.write(req);

				if(entrada.available() >= buf.length){
					entrada.read(buf);
				}else{
					Thread.sleep(10);
				}
			}
		} catch(Exception e) {
			try {
				target.close();
			} catch (IOException e1) {
			}
		}finally {
			System.err.println("Conexão fechada do cliente TCP " + id);
		}

	}


}
