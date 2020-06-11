package Olimpus.Run;

import Olimpus.Compilador.AST;

public class Run_Condition {

	public boolean init(AST ASTC,Escopo mEscopo,RunTime mRunTime) {
		
		boolean ret = true;

	//	System.out.println("Escopo Anterior : " + mEscopo);
	//	mEscopo.Listar();
		
		AST AST_Passador = ASTC.getAST("PASS");
		AST AST_Mode = ASTC.getAST("MODE");
		AST AST_Body = ASTC.getAST("BODY");

		float tmp = 0;
		if (AST_Passador.getSub().contentEquals("Num")) {
			tmp = Float.parseFloat(AST_Passador.getValor());
		} else if (AST_Passador.getSub().contentEquals("Var")) {
			tmp = mEscopo.Use(AST_Passador.getValor());
		}

		if (AST_Mode.getValor().contentEquals("TRUE") && tmp == 1) {

			Escopo mInternal = new Escopo(mRunTime,mEscopo);

			
		
			for (AST ASTI : AST_Body.getASTS()) {
				if (mInternal.run(ASTI) == false) {
					ret = false;
					break;
				}
			}

			mEscopo.setCancel(mInternal.getCancel());
			mEscopo.setContinue(mInternal.getContinue());

		} else if (AST_Mode.getValor().contentEquals("FALSE") && tmp == -1) {

			Escopo mInternal = new Escopo(mRunTime,mEscopo);

		
			
			for (AST ASTI : AST_Body.getASTS()) {
				if (mInternal.run(ASTI) == false) {
					ret = false;
					break;
				}
			}

			mEscopo.setCancel(mInternal.getCancel());
			mEscopo.setContinue(mInternal.getContinue());

		}
		
		return ret;
	}
	
	
	
}
