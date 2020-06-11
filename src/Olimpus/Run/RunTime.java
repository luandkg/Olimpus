package Olimpus.Run;

import java.util.ArrayList;

import Olimpus.Compilador.AST;

public class RunTime {

	private ArrayList<AST> mASTS;
	private ArrayList<String> mErros;

	public RunTime() {

		mASTS = new ArrayList<>();

		mErros = new ArrayList<>();

	}

	public ArrayList<String> getErros() {
		return mErros;
	}

	public void init(ArrayList<AST> eASTs) {
		mASTS = eASTs;

		mErros.clear();

		Escopo OlimpusInternal = new Escopo(this,null);
		AST mOLIMPUS = null;

		for (AST ASTC : mASTS) {
			if (ASTC.getNome().contentEquals("ACTION")) {
				OlimpusInternal.Action_Apply(ASTC);
			} else if (ASTC.getNome().contentEquals("FUNCTION")) {
				OlimpusInternal.Function_Apply(ASTC);
			} else if (ASTC.getNome().contentEquals("OLIMPUS")) {
				mOLIMPUS = ASTC;
			} else if (ASTC.getNome().contentEquals("TAGS")) {
				OlimpusInternal.Tags_Apply(ASTC);
			}
		}

		if (mOLIMPUS != null) {
			for (AST ASTC : mOLIMPUS.getASTS()) {
				if (OlimpusInternal.run(ASTC) == false) {
					break;
				}

				if (mErros.size() > 0) {
					break;
				}

			}
		} else {

			mErros.add("Olimpus Vazio !");
		}

	}

}
