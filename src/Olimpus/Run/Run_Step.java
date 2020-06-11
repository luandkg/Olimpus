package Olimpus.Run;

import Olimpus.Compilador.AST;

public class Run_Step {

	public boolean init(AST ASTC,Escopo mEscopo,RunTime mRunTime, Escopo mInternalAnterior) {
		
		boolean ret = true;
		
		AST AST_Passador = ASTC.getAST("PASS");
		AST AST_Body = ASTC.getAST("BODY");

		float tmp = 0;
		if (AST_Passador.getSub().contentEquals("Num")) {
			tmp = Float.parseFloat(AST_Passador.getValor());
		} else if (AST_Passador.getSub().contentEquals("Var")) {
			tmp = mEscopo.Use(AST_Passador.getValor());
		}

		if (tmp > 0) {

			for (int i = 0; i < tmp; i++) {

				Escopo mInternal = new Escopo(mRunTime,mInternalAnterior);
			//	mInternal.setEscopoAnterior(mInternalAnterior.getMemoria());

				for (AST ASTI : AST_Body.getASTS()) {
					if (mInternal.run(ASTI) == false) {
						ret = false;
						break;
					}
				}

				mInternalAnterior.setCancel(mInternal.getCancel());
				mInternalAnterior.setContinue(mInternal.getContinue());

			}

		} else {
			mRunTime.getErros().add("O repetidor deve ser um numero positivo !");
		}
	
		return ret;
	}
	
}
