package servidor.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import servidor.preset;

public class ClienteUDPThreaded extends Thread{

	private int id;
	private String host;
	private int port;
	
	public ClienteUDPThreaded(int id, String host, int port) {
		this.id = id;
		this.host = host;
		this.port = port;
	}
	
	@Override
	public void run() {

		DatagramSocket clientSocket = null;
		
		try {
			InetAddress destinationAddress = InetAddress.getByName(host);
			int destinationPort = port;
			byte[] sendData = new byte[]{preset.KEY};
			byte[] receiveData = new byte[preset.BUFFER_SIZE];
			
			clientSocket = new DatagramSocket();

			while(true){
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destinationAddress, destinationPort);
				clientSocket.send(sendPacket);
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
			}

		} catch(Exception e) {
			clientSocket.close();
			
		}finally {
			System.err.println("Conexão fechada do cliente UDP " + id);
		}

	}

}
