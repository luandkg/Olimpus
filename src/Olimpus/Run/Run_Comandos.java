package Olimpus.Run;

import Olimpus.Compilador.AST;

public class Run_Comandos {

	
	public void Run_Def(AST ASTC,Escopo mInternalAnterior) {
		
		if (ASTC.getSub().contentEquals("Num")) {
			mInternalAnterior.setVALOR( Float.parseFloat(ASTC.getValor()));

		} else if (ASTC.getSub().contentEquals("Var")) {
			mInternalAnterior.setVALOR(  mInternalAnterior.Use(ASTC.getValor()));
		}

		
	}
	
	public void Run_Add(AST ASTC,Escopo mInternalAnterior) {
		
		if (ASTC.getSub().contentEquals("Num")) {
			float tmp = mInternalAnterior.getValor() + Float.parseFloat(ASTC.getValor());
			mInternalAnterior.setVALOR( tmp);
		} else if (ASTC.getSub().contentEquals("Var")) {
	
			float tmp = mInternalAnterior.getValor() +  mInternalAnterior.Use(ASTC.getValor());
			mInternalAnterior.setVALOR( tmp);
			
		}
		
	}
	
	
	public void Run_Sub(AST ASTC,Escopo mInternalAnterior) {
		
		if (ASTC.getSub().contentEquals("Num")) {
			float tmp = mInternalAnterior.getValor() - Float.parseFloat(ASTC.getValor());
			mInternalAnterior.setVALOR( tmp);
		} else if (ASTC.getSub().contentEquals("Var")) {
	
			float tmp = mInternalAnterior.getValor() -  mInternalAnterior.Use(ASTC.getValor());
			mInternalAnterior.setVALOR( tmp);
			
		}
		
	}
	
	
	public void Run_Stack_Value_Dec(AST ASTC,Escopo mInternalAnterior) {
	
		String v = "" + mInternalAnterior.getValor();
		int i = 0;
		int o = v.length();
		String vs = "";
		boolean a = false;

		if (mInternalAnterior.getValor() < 0) {
			vs += "-";
		}

		vs += "0.";

		while (i < o) {
			String l = "" + v.charAt(i);
			if (l.contentEquals(".")) {
				a = true;
				i += 1;
				break;
			} else {
			}
			i += 1;
		}

		if (a) {

			while (i < o) {
				String l = "" + v.charAt(i);
				vs += l;
				i += 1;
			}

		}

		System.out.print(vs);
	
	
	}
	public void Run_Stack_Value_Int(AST ASTC,Escopo mInternalAnterior) {
		

		String v = "" + mInternalAnterior.getValor();
		int i = 0;
		int o = v.length();
		String vs = "";

		while (i < o) {
			String l = "" + v.charAt(i);
			if (l.contentEquals(".")) {
				break;
			} else {
				vs += l;
			}
			i += 1;
		}

		System.out.print(vs);
		
	}
	
	public void Run_Stack_Value_Status(AST ASTC,Escopo mInternalAnterior) {
		
		String mtabs = "";

		if (mInternalAnterior.getTabulacao() > 0) {
			for (int t = 0; t < mInternalAnterior.getTabulacao(); t++) {
				mtabs += "\t";
			}

		}

		System.out.print("\n" + mtabs + ASTC.getValor());
		
	}
	
	
}
