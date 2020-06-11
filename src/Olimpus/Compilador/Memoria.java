package Olimpus.Compilador;

import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;

public class Memoria {


	private Compiler mCompiler;
	private AST mASTRaiz;
	
	public Memoria(Compiler eCompiler,AST eASTRaiz) {
		mCompiler=eCompiler;
		mASTRaiz=eASTRaiz;
	}
	
	public void AST_MAKE( ) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("MAKE");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 =mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um Identificador !");
		}
		mASTRaiz.getASTS().add(AST_Corrente);

	}
	
	public void AST_CONST( ) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("CONST");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 =mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um Identificador !");
		}
		mASTRaiz.getASTS().add(AST_Corrente);

	}
	
	
	public void AST_APPLY( ) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("APPLY");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 =mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um Identificador !");
		}
		mASTRaiz.getASTS().add(AST_Corrente);

	}
	
	public void AST_MEMORY( ) {
		
		AST AST_Corrente = new AST();
		AST_Corrente.setNome("__MEMORY__");
		mASTRaiz.getASTS().add(AST_Corrente);

		
	}
	
	public void AST_USE( ) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("USE");

		mCompiler.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (mCompiler.Continuar()) {
			Token TokenC2 =mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(valor);

		if (!valorado) {
			mCompiler.getErros().add("Era esperado um Identificador !");
		}
		mASTRaiz.getASTS().add(AST_Corrente);

	}
	

}
