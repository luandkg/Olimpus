package Olimpus.Analisador;

import java.util.ArrayList;

import Olimpus.Compilador.AST;

public class Analisador {

	private ArrayList<AST> mASTS;
	private ArrayList<AST> mACTIONS;
	private ArrayList<AST> mFUNCTIONS;
	private ArrayList<AST> mTAGS;
	private ArrayList<AST> mALL;

	private AST mOLIMPUS;

	private ArrayList<String> mErros;

	public Analisador() {

		mASTS = new ArrayList<>();

		mALL = new ArrayList<>();

		mACTIONS = new ArrayList<>();
		mFUNCTIONS = new ArrayList<>();
		mTAGS = new ArrayList<>();

		mOLIMPUS = null;

		mErros = new ArrayList<>();

	}

	public ArrayList<String> getErros() {
		return mErros;
	}

	public void init(ArrayList<AST> eASTs) {
		mASTS = eASTs;

		mOLIMPUS = null;

		mALL.clear();

		mACTIONS.clear();
		mFUNCTIONS.clear();
		mTAGS.clear();

		mErros.clear();

		for (AST ASTC : mASTS) {
			if (ASTC.getNome().contentEquals("ACTION")) {
				mACTIONS.add(ASTC);
				mALL.add(ASTC);

				Action_Duplicados(ASTC);

			} else if (ASTC.getNome().contentEquals("FUNCTION")) {
				mFUNCTIONS.add(ASTC);
				mALL.add(ASTC);
			} else if (ASTC.getNome().contentEquals("OLIMPUS")) {
				mOLIMPUS = ASTC;
			} else if (ASTC.getNome().contentEquals("TAGS")) {
				mTAGS.add(ASTC);
				mALL.add(ASTC);
			}
		}

		Duplicados();

		
		ArrayList<String> Nomes_Functions = new ArrayList<String>();
		ArrayList<String> TypeDefs = new ArrayList<String>();

		for (AST ASTC : mFUNCTIONS) {
			Nomes_Functions.add(ASTC.getValor());
		}
		
		for (AST ASTC : mACTIONS) {
			Parametrizacao(ASTC,Nomes_Functions,TypeDefs);
		}

		for (AST ASTC : mFUNCTIONS) {
			Parametrizacao(ASTC,Nomes_Functions,TypeDefs);
		}

	}

	public void Parametrizacao(AST ASTPai,ArrayList<String> Nomes_Functions,ArrayList<String> Function_Set_Anterior) {

		ArrayList<String> Function_Set = new ArrayList<String>();
		
		for (String ASTI : Function_Set_Anterior) {
			Function_Set.add(ASTI);	
		}
		
		for (AST ASTI : ASTPai.getASTS()) {
			if (ASTI.seuNome("DO")) {

				if(Nomes_Functions.contains(ASTI.getValor())) {
				} else 	if(Function_Set.contains(ASTI.getValor())) {

				}else {
					mErros.add(" -->> Funtion " + ASTI.getValor() + " : Nao Encontrada !");
				}
				

			} else if (ASTI.seuNome("CONDITION")) {

				AST ASTCorpo = ASTI.getAST("BODY");
				Parametrizacao(ASTCorpo,Nomes_Functions,Function_Set);
			} else if (ASTI.seuNome("STEP")) {

				AST ASTCorpo = ASTI.getAST("BODY");
				Parametrizacao(ASTCorpo,Nomes_Functions,Function_Set);
			} else if (ASTI.seuNome("LOOP")) {

				AST ASTCorpo = ASTI.getAST("BODY");
				Parametrizacao(ASTCorpo,Nomes_Functions,Function_Set);
			} else if (ASTI.seuNome("FUNCTION_SET")) {
				
				Function_Set.add(ASTI.getValor());
				
			}
		}

	}

	public void Duplicados() {

		ArrayList<String> eNomes = new ArrayList<String>();

		for (AST ASTC : mALL) {
			if (eNomes.contains(ASTC.getValor()) == false) {
				int i = 0;
				eNomes.add(ASTC.getValor());
				for (AST ASTC2 : mALL) {

					if (ASTC.getValor().contentEquals(ASTC2.getValor())) {
						i += 1;
					}

				}

				if (i > 1) {
					mErros.add(ASTC.getNome() + " " + ASTC.getValor() + " : Duplicado !");
				}

			}

		}

	}

	public void Action_Duplicados(AST ActionC) {

		ArrayList<String> eNomes = new ArrayList<String>();

		for (AST ASTC : ActionC.getASTS()) {
			if (ASTC.getNome().contentEquals("TAGS")) {
				if (eNomes.contains(ASTC.getValor()) == false) {

					eNomes.add(ASTC.getValor());

					int i = 0;

					for (AST ASTC2 : ActionC.getASTS()) {
						if (ASTC2.getNome().contentEquals("TAGS")) {
							if (ASTC.getValor().contentEquals(ASTC2.getValor())) {
								i += 1;
							}
						}

					}

					if (i > 1) {
						mErros.add(ASTC.getNome() + " " + ASTC.getValor() + " : Duplicado !");
					}

				}

			}

		}

	}

}
