package AppOlimpus;

import Olimpus.Olimpus;

public class AppOlimpus {

	public static void main(String[] args) {

		String arquivo = "res/07 - types.olimpus";
		String saida = "res/Olimpus.olimpud";

		Olimpus OlimpusC = new Olimpus();
		
		OlimpusC.init(arquivo,saida);
		
		
	}
	
	
}
