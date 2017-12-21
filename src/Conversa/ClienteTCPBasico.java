package Conversa;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class ClienteTCPBasico {

	public static void main(String[] args) {
		
		try {
			Socket servidor = new Socket("127.0.0.1",12345);
			System.out.println("Conexão aberta com o servidor " + servidor.getInetAddress().getHostAddress() + " na porta " + servidor.getPort());
			
			ObjectInputStream entrada = new ObjectInputStream(servidor.getInputStream());
			ObjectOutputStream saida = new ObjectOutputStream(servidor.getOutputStream());
			saida.flush();
			
			Object obj = entrada.readObject();
			
			if(obj instanceof Date){
				Date dataAtual = (Date) obj;
				JOptionPane.showMessageDialog(null,"Data recebida do servidor: " + dataAtual.toString());
				
			} else if(obj instanceof String){
				String str = (String) obj;
				JOptionPane.showMessageDialog(null,"String recebida do servidor: \"" + str + "\"");
				
			} else if(obj instanceof LinkedList){
				LinkedList list = (LinkedList) obj;
				JOptionPane.showMessageDialog(null,"Lista recebida do servidor: " + list.toString());
				
			} else if(obj instanceof int[]){
				int[] array = (int[]) obj;
				JOptionPane.showMessageDialog(null,"Array recebido do servidor: " + Arrays.toString(array));	
			}
			
			entrada.close();
			saida.close();
			
			servidor.close();
			System.out.println("Conexão encerrada");
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
