package Olimpus.Compilador;

import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;

public class Statements {

	private Compiler mCompiler;
	private AST mASTRaiz;
	
	public Statements(Compiler eCompiler,AST eASTRaiz) {
		mCompiler=eCompiler;
		mASTRaiz=eASTRaiz;
	}
	
	
	public AST getRaiz() {
		return mASTRaiz;
	}
	
	
	public void AST_STACK() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("STACK");

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_STACK_VALUE() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("STACK_VALUE");

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
	public void AST_STACK_VALUE_INT() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("STACK_VALUE_INT");

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
	public void AST_STACK_VALUE_DEC() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("STACK_VALUE_DEC");

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_WHITE() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("WHITE");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.NUMERO) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um numero !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_TAB() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("TAB");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.NUMERO) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um numero !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_STATUS() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("STATUS");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 =mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.TEXTO) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um Texto !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_CLEAN() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("CLEAN");

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_SNAPSHOT() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("SNAPSHOT");

		mASTRaiz.getASTS().add(AST_Corrente);
	}

	public void AST_ROLLBACK() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("ROLLBACK");

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
	public void AST_CALL() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("CALL");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado o nome de uma ação !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
	public void AST_DO() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("DO");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado o nome de uma função !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
	public void AST_TO() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("TO");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado o nome de uma variavel !");
		}

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
	public void AST_RUN() {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("RUN");

		mASTRaiz.getASTS().add(AST_Corrente);
	}
	
}
