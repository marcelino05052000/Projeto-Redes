package servidor.tcp;

import servidor.preset;

public class MainClienteTCPMultiThreaded {
	
	public static void main(String[] args) {

		try {
			
			for(int i = 0 ; i < preset.Conexoes ; i++){
				new ClienteTCPThreaded(i, preset.Host, preset.Porta).start();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
