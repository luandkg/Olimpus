package Olimpus.Run;

import Olimpus.Compilador.AST;

public class Run_Loop {

	
	public boolean init(AST ASTC,Escopo mEscopo,RunTime mRunTime) {
		boolean ret = true;
		
		AST AST_Passador = ASTC.getAST("PASS");
		AST AST_Body = ASTC.getAST("BODY");

		float tmp = 0;
		if (AST_Passador.getSub().contentEquals("Num")) {
			tmp = Float.parseFloat(AST_Passador.getValor());
		} else if (AST_Passador.getSub().contentEquals("Var")) {
			tmp = mEscopo.Use(AST_Passador.getValor());
		}

		if (tmp == 1) {

			while (tmp == 1) {

				Escopo mInternal = new Escopo(mRunTime,mEscopo);
			//	mInternal.setEscopoAnterior(mInternalAnterior);

			
				
				boolean sair = false;

				for (AST ASTI : AST_Body.getASTS()) {
					if (mInternal.run(ASTI) == false) {
						ret = false;
						break;
					}

					if (mInternal.getCancel()) {
						sair = true;
						break;
					}
					if (mInternal.getContinue()) {
						// System.out.println("Contuando....");
						break;
					}
				}

				if (sair) {
					break;
				}

				if (AST_Passador.getSub().contentEquals("Num")) {
					tmp = Float.parseFloat(AST_Passador.getValor());
				} else if (AST_Passador.getSub().contentEquals("Var")) {
					tmp = mEscopo.Use(AST_Passador.getValor());
				}

			}

		} else {
			mRunTime.getErros().add("O repetidor deve ser um numero positivo !");
		}
		return ret;
	}
}
