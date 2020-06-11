package Olimpus.Compilador;

import java.util.ArrayList;

import Olimpus.Lexer.Lexer;
import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;
import Olimpus.Utils.Requerimento;

public class Comandos {

	private Compiler mCompiler;
	private ArrayList<String> mErros;

	public Comandos(Compiler eCompiler, ArrayList<String> eErros) {
		mCompiler = eCompiler;
		mErros = eErros;
	}

	public boolean Continuar() {
		return mCompiler.Continuar();
	}

	public void Proximo() {
		mCompiler.Proximo();
	}

	public boolean STATEMENTS(AST ASTAqui, Token TokenC, boolean esperandoSair) {

		boolean ret = false;

		Statements STA = new Statements(mCompiler, ASTAqui);

		Operacoes mOperacoes = new Operacoes(mCompiler, STA.getRaiz());
		Comparacoes mComparacoes = new Comparacoes(mCompiler, STA.getRaiz());

		Memoria mMemoria = new Memoria(mCompiler, STA.getRaiz());

		if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("def")) {
			mOperacoes.AST_DEF();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("add")) {
			mOperacoes.AST_ADD();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("sub")) {
			mOperacoes.AST_SUB();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("mux")) {
			mOperacoes.AST_MUX();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("div")) {
			mOperacoes.AST_DIV();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("neg")) {
			mOperacoes.AST_NEG();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("mod")) {
			mOperacoes.AST_MOD();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("dif")) {
			mOperacoes.AST_DIF();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("redif")) {
			mOperacoes.AST_REDIF();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("stack")) {
			STA.AST_STACK();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("stack_value")) {
			STA.AST_STACK_VALUE();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("stack_value_int")) {
			STA.AST_STACK_VALUE_INT();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("stack_value_dec")) {
			STA.AST_STACK_VALUE_DEC();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("tab")) {
			STA.AST_TAB();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("white")) {

			STA.AST_WHITE();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("make")) {
			mMemoria.AST_MAKE();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("apply")) {
			mMemoria.AST_APPLY();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("use")) {
			mMemoria.AST_USE();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("const")) {
			mMemoria.AST_CONST();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("__MEMORY__")) {
			mMemoria.AST_MEMORY();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("status")) {

			STA.AST_STATUS();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("clean")) {

			STA.AST_CLEAN();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("snapshot")) {

			STA.AST_SNAPSHOT();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("rollback")) {

			STA.AST_ROLLBACK();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("ret")) {

			mOperacoes.AST_RET();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("with")) {

			mOperacoes.AST_WITH();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("call")) {

			STA.AST_CALL();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("do")) {

			STA.AST_DO();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("run")) {

			STA.AST_RUN();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("to")) {

			STA.AST_TO();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("great")) {

			mComparacoes.AST_GREAT();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("less")) {

			mComparacoes.AST_LESS();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("equal")) {

			mComparacoes.AST_EQUAL();
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("mis")) {

			mComparacoes.AST_MIS();

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("condition_true")) {

			AST_CONDITION(STA.getRaiz(), "TRUE");

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("condition_false")) {

			AST_CONDITION(STA.getRaiz(), "FALSE");
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("step")) {

			AST_STEP(STA.getRaiz());
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("loop")) {

			AST_LOOP(STA.getRaiz());

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("cancel")) {
			AST_CANCEL(STA.getRaiz());
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("continue")) {
			AST_CONTINUE(STA.getRaiz());

	

		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("function_get")) {

			AST_FUNCTION_GET(STA.getRaiz());
		} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("function_set")) {

			AST_FUNCTION_SET(STA.getRaiz());

		} else if (TokenC.Tipo() == TokenTipo.ARROBA) {

			mCompiler.AST_ARROBA(STA.getRaiz());

		} else if (esperandoSair == true && TokenC.Tipo() == TokenTipo.FECHA) {
			ret = true;
		} else {

			mErros.add("Token Desconhecido " + TokenC.Tipo().toString() + " : " + TokenC.Conteudo());

		}

		return ret;
	}

	private void AST_CONDITION(AST ASTPai, String eModo) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("CONDITION");

		AST AST_Passador = AST_Corrente.criarAST("PASS");
		AST AST_Mode = AST_Corrente.criarAST("MODE");
		AST AST_Body = AST_Corrente.criarAST("BODY");

		AST_Mode.setValor(eModo);

		Proximo();
		boolean nomeado = false;
		boolean aberto = false;

		String nome = "";

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
				AST_Passador.setSub("Var");
			} else if (TokenC2.Tipo() == TokenTipo.NUMERO) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
				AST_Passador.setSub("Num");
			}
		}

		AST_Passador.setValor(nome);

		if (!nomeado) {
			mErros.add("Era esperado o argumento da condição !");
		}

		Proximo();

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ABRE) {
				aberto = true;
			}
		}

		if (!aberto) {
			mErros.add("Era esperado abrir chaves !");
		} else {

			Proximo();

			while (Continuar()) {
				Token TokenC = mCompiler.getTokenCorrente();

				boolean esp = STATEMENTS(AST_Body, TokenC, true);

				if (esp) {
					break;

				}

				Proximo();
			}

		}

		ASTPai.getASTS().add(AST_Corrente);
	}

	private void AST_STEP(AST ASTPai) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("STEP");

		AST AST_Passador = AST_Corrente.criarAST("PASS");
		AST AST_Body = AST_Corrente.criarAST("BODY");

		Proximo();
		boolean nomeado = false;
		boolean aberto = false;

		String nome = "";

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
				AST_Passador.setValor(nome);
				AST_Passador.setSub("Var");
			} else if (TokenC2.Tipo() == TokenTipo.NUMERO) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
				AST_Passador.setValor(nome);
				AST_Passador.setSub("Num");
			}
		}

		if (!nomeado) {
			mErros.add("Era esperado a quantidade de repetições !");
		}

		Proximo();

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ABRE) {
				aberto = true;
			}
		}

		if (!aberto) {
			mErros.add("Era esperado abrir chaves !");
		} else {

			Proximo();

			while (Continuar()) {
				Token TokenC = mCompiler.getTokenCorrente();

				boolean esp = STATEMENTS(AST_Body, TokenC, true);

				if (esp) {
					break;

				}

				Proximo();
			}

		}

		ASTPai.getASTS().add(AST_Corrente);
	}

	private void AST_LOOP(AST ASTPai) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("LOOP");

		AST AST_Passador = AST_Corrente.criarAST("PASS");
		AST AST_Body = AST_Corrente.criarAST("BODY");

		Proximo();
		boolean nomeado = false;
		boolean aberto = false;

		String nome = "";

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
				AST_Passador.setValor(nome);
				AST_Passador.setSub("Var");
			} else if (TokenC2.Tipo() == TokenTipo.NUMERO) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
				AST_Passador.setValor(nome);
				AST_Passador.setSub("Num");
			}
		}

		if (!nomeado) {
			mErros.add("Era esperado a condição de repetição !");
		}

		Proximo();

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ABRE) {
				aberto = true;
			}
		}

		if (!aberto) {
			mErros.add("Era esperado abrir chaves !");
		} else {

			Proximo();

			while (Continuar()) {
				Token TokenC = mCompiler.getTokenCorrente();

				boolean esp = STATEMENTS(AST_Body, TokenC, true);

				if (esp) {
					break;

				}

				Proximo();
			}

		}

		ASTPai.getASTS().add(AST_Corrente);
	}

	private void AST_CANCEL(AST ASTPai) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("CANCEL");
		ASTPai.getASTS().add(AST_Corrente);

	}

	private void AST_CONTINUE(AST ASTPai) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("CONTINUE");
		ASTPai.getASTS().add(AST_Corrente);

	}




	public void AST_FUNCTION_GET(AST ASTPai) {

		Token TokenC = mCompiler.getTokenCorrente();

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("FUNCTION_GET");

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
			mCompiler.getErros().add("Era esperado o nome de uma ACTION ou FUNCTION !");
		}

		ASTPai.getASTS().add(AST_Corrente);

	}

	public void AST_FUNCTION_SET(AST ASTPai) {

		Token TokenC = mCompiler.getTokenCorrente();

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("FUNCTION_SET");

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
			mCompiler.getErros().add("Era esperado um nome para uma ACTION ou FUNCTION !");
		}

		ASTPai.getASTS().add(AST_Corrente);

	}
}
