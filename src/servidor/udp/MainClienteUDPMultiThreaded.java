package servidor.udp;

import servidor.preset;

public class MainClienteUDPMultiThreaded {
	
	public static void main(String[] args) {

		try {
			
			for(int i = 0 ; i < preset.Conexoes ; i++){
				new ClienteUDPThreaded(i, preset.Host, preset.Porta).start();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
