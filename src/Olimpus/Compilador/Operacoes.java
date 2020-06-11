package Olimpus.Compilador;

import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;

public class Operacoes {

	private Compiler mCompiler;
	private AST mASTRaiz;

	public Operacoes(Compiler eCompiler, AST eASTRaiz) {
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

	public void AST_DEF() {

		AST_ATT("DEF");

	}

	public void AST_ADD() {

		AST_ATT("ADD");

	}

	public void AST_SUB() {

		AST_ATT("SUB");

	}

	public void AST_MUX() {

		AST_ATT("MUX");

	}

	public void AST_DIF() {

		AST_ATT("DIF");

	}

	public void AST_REDIF() {

		AST_ATT("REDIF");

	}

	public void AST_DIV() {

		AST_ATT("DIV");

	}

	public void AST_RET() {

		AST_ATT("RET");

	}

	public void AST_WITH() {

		AST_ATT("WITH");

	}

	public void AST_NEG() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("NEG");

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_MOD() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("MOD");

		mASTRaiz.getASTS().add(AST_Corrente);
	}

}
