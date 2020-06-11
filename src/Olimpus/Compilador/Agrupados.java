package Olimpus.Compilador;

import java.util.ArrayList;

import Olimpus.Lexer.Lexer;
import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;
import Olimpus.Utils.Requerimento;

public class Agrupados {

	private Compiler mCompiler;
	private ArrayList<String> mErros;

	public Agrupados(Compiler eCompiler,ArrayList<String> eErros) {
		mCompiler=eCompiler;
		mErros=eErros;
	}
	
	public boolean Continuar() {
		return mCompiler.Continuar();
	}
	
	public void Proximo() {
		 mCompiler.Proximo();
	}
	
	

	public void AST_ACTION(AST ASTPai) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("ACTION");

		Proximo();
		
		boolean nomeado = false;
		boolean aberto = false;

		String nome = "";

		if (Continuar()) {
			Token TokenC2 = mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(nome);

		if (!nomeado) {
			mErros.add("Era esperado o nome da variavel !");
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

			Comandos mComandos = new Comandos(mCompiler, mErros);

			while (Continuar()) {
				Token TokenC = mCompiler.getTokenCorrente();

				boolean esp = mComandos.STATEMENTS( AST_Corrente,TokenC, true);

				if (esp) {
					break;

				}

				Proximo();
			}

		}

		ASTPai.getASTS().add(AST_Corrente);
	}

	public void AST_FUNCTION (AST ASTPai) {

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("FUNCTION");

		Proximo();
		boolean nomeado = false;
		boolean aberto = false;

		String nome = "";

		if (Continuar()) {
			Token TokenC2= mCompiler.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.ID) {
				nomeado = true;
				nome = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(nome);

		if (!nomeado) {
			mErros.add("Era esperado o nome da Fun��o !");
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

			Statements STA = new Statements(mCompiler, AST_Corrente);

			while (Continuar()) {
				Token TokenC = mCompiler.getTokenCorrente();

				if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("action")) {

					AST_ACTION(AST_Corrente);
				} else if (TokenC.Tipo() == TokenTipo.FECHA) {
					break;
				} else {

					mErros.add("Era esperado blocos de A��es !");

				}

				Proximo();
			}

		}

		 ASTPai.getASTS().add(AST_Corrente);
	}

	

public void AST_REQUIRED(AST ASTPai,ArrayList<Requerimento> mRequerimentos,ArrayList<String>mRequisitados,String mLocal) {
		
		
		//	AST AST_Corrente = new AST();
		//	AST_Corrente.setNome("REQUIRED");
		//	AST_Raiz.getASTS().add(AST_Corrente);

			Proximo();
			if (Continuar()) {

				Token TokenC2 =  mCompiler.getTokenCorrente();
				if (TokenC2.Tipo() == TokenTipo.ID) {

				//	AST_Corrente.setValor(TokenC2.Conteudo());

					boolean enc = false;

					for (Requerimento Req : mRequerimentos) {

						if (Req.getNome().contentEquals(TokenC2.Conteudo())) {

							mRequisitados.add(TokenC2.Conteudo());

							Lexer LexerC = new Lexer();
							LexerC.init(Req.getConteudo());
							Compiler CompilerC = new Compiler();
							CompilerC.init(LexerC.Tokens(), mLocal);
						
							
							for (String ErroC  : LexerC.getErros()) {
								this.mErros.add(ErroC);
							}
							
							for (String ErroC  : CompilerC.getErros()) {
								this.mErros.add(ErroC);
							}
							
							//CompilerC.ImprimirArvoreDeInstrucoes();
							
							
							for(AST mAST : CompilerC.getASTS()) {
							//	this.getASTS().add(mAST);
								if(mAST.getNome().contentEquals("ACTION")) {
									ASTPai.getASTS().add(mAST);
								} else if(mAST.getNome().contentEquals("FUNCTION")) {
									ASTPai.getASTS().add(mAST);
								}
								
						
							}
							
							
							
							//System.out.println("---------------------------------");

							enc = true;
							break;
						}

					}

					if (enc == false) {

						mErros.add("Requisicao nao encontrada : " + TokenC2.Conteudo());

					}

				} else {

					mErros.add("Era esperado uma requisicao ");
				}

			}
			
	}

public void AST_TAGS(AST ASTPai) {

	AST AST_Corrente = new AST();
	AST_Corrente.setNome("TAGS");

	Proximo();
	boolean nomeado = false;
	boolean aberto = false;

	String nome = "";

	if (Continuar()) {
		Token TokenC2 = mCompiler.getTokenCorrente();
		if (TokenC2.Tipo() == TokenTipo.ID) {
			nomeado = true;
			nome = (TokenC2.Conteudo());
			AST_Corrente.setValor(nome);
			AST_Corrente.setSub("Var");
		}
	}

	if (!nomeado) {
		mErros.add("Era esperado o nome da tag !");
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
		boolean fechado = false;

		Statements STA = new Statements(mCompiler, AST_Corrente);
		int eTag = 0;

		while (Continuar()) {
			Token TokenC = mCompiler.getTokenCorrente();

			if (TokenC.Tipo() == TokenTipo.ID) {

				AST AST_TAG = new AST();
				AST_TAG.setNome("TAGS");
				AST_TAG.setValor(TokenC.Conteudo());
				AST_TAG.setSub("" + eTag);

				AST_Corrente.getASTS().add(AST_TAG);
				eTag += 1;

			} else if (TokenC.Tipo() == TokenTipo.FECHA) {
				fechado = true;
				break;
			}

			Proximo();
		}

		if (!fechado) {
			mErros.add("Era esperado fechar chaves !");
		}

	}

	ASTPai.getASTS().add(AST_Corrente);
}


}
