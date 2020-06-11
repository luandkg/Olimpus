package Olimpus.Compilador;

import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;

public class Comparacoes {

	private Compiler mCompiler;
	private AST mASTRaiz;

	public Comparacoes(Compiler eCompiler, AST eASTRaiz) {
		mCompiler = eCompiler;
		mASTRaiz = eASTRaiz;
	}

	private void AST_ATT(String eNome) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome(eNome);

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.NUMERO) {
				valorado = true;
				valor = (TokenC2.Conteudo());
				AST_Corrente.setSub("Num");
			} else if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
				AST_Corrente.setSub("Var");
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado uma atribuicao !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);

	}

	public void AST_GREAT() {

		AST_ATT("GREAT");

	}
	
	public void AST_LESS() {

		AST_ATT("LESS");

	}
	
	public void AST_EQUAL() {

		AST_ATT("EQUAL");

	}
	
	public void AST_MIS() {

		AST_ATT("MIS");

	}
}
