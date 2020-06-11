package Olimpus;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

// COMPILADOR OLIMPUS

// DESENVOLVEDOR : LUAN FREITAS

import java.util.Calendar;

import Olimpus.Analisador.Analisador;
import Olimpus.Compilador.AST;
import Olimpus.Compilador.Compiler;
import Olimpus.Lexer.Lexer;
import Olimpus.Lexer.Texto;
import Olimpus.Lexer.Token;
import Olimpus.Run.RunTime;
import Olimpus.Utils.Decompilador;

public class Olimpus {

	public void init(String arquivo,String saida) {

		
		
		String conteudo = Texto.Ler(arquivo);
		File arq = new File(arquivo);
		String local = arq.getParent() + "/";

		
		// System.out.println("####### CONTEUDO #######");

		// System.out.println(conteudo);
		System.out.println("################ OLIMPUS ################");
		System.out.println("");
		System.out.println(" - AUTOR	: LUAN FREITAS");
		System.out.println(" - VERSION  	: 1.0");
		System.out.println(" - STATUS  	: ALPHA");

		System.out.println("");

		System.out.println("################# LEXER ##################");
		System.out.println("");

		Lexer LexerC = new Lexer();
		System.out.println("\t Iniciado : " + getDate().toString());
		LexerC.init(conteudo);
		System.out.println("\t - Chars : " + conteudo.length());
		System.out.println("\t - Tokens : " + LexerC.Tokens().size());
		System.out.println("\t - Erros : " + LexerC.getErros().size());
		System.out.println("\t Finalizado : " + getDate().toString());

		for (Token TokenC : LexerC.Tokens()) {
			//System.out.println("\t\t" + TokenC.Conteudo());
		}
		
		
		if (LexerC.getErros().size() > 0) {
			System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

			for (String Erro : LexerC.getErros()) {
				System.out.println("\t\t" + Erro);
			}
		}

		System.out.println("");

		if (LexerC.getErros().size() == 0) {

			System.out.println("############### COMPILADOR ###############");
			System.out.println("");

			 ArrayList<String> mArquivos = new ArrayList<>();
			 mArquivos.add(arquivo);
			 
			Compiler CompilerC = new Compiler();
			System.out.println("\t Iniciado : " + getDate().toString());
			CompilerC.init(LexerC.Tokens(),local);
			System.out.println("\t - Instrucoes : " + CompilerC.Instrucoes());
			System.out.println("\t - Erros : " + CompilerC.getErros().size());
			System.out.println("\t - Requisitados : ");

			for (String Req : CompilerC.getRequisitados()) {
				System.out.println("\t\t - " + Req);
			}
			
			System.out.println("\t Finalizado : " + getDate().toString());
			
			
			
			
			if (CompilerC.getErros().size() > 0) {
				System.out.println("\n\t ERROS DE COMPILACAO : ");

				for (String Erro : CompilerC.getErros()) {
					System.out.println("\t\t" + Erro);
				}
			}

			
			
		
			
			if (CompilerC.getErros().size() == 0) {

				
		

			
				System.out.println("");
				System.out.println("################ ANALISE ################");
				System.out.println("");
				
				
				
				Analisador AnaliseC = new Analisador();
				String AI = getDate().toString();
				AnaliseC.init(CompilerC.getASTS());
				String AF = getDate().toString();



				System.out.println("\t - Iniciado : " + AI);
				System.out.println("\t - Finalizado : " + AF);

				System.out.println("\t - Erros : " + AnaliseC.getErros().size());

				if (AnaliseC.getErros().size() > 0) {
					System.out.println("\n\t ERROS DE ANALISE : ");

					for (String Erro : AnaliseC.getErros()) {
						System.out.println("\t\t" + Erro);
					}
				} else {
					
					
					
					System.out.println("");
					System.out.println("################ OBJETO ################");
					System.out.println("");
					
					
					String instrucoes = CompilerC.ImprimirArvoreDeInstrucoes();
					
					
					CompilerC.CompilacaoArvoreDeInstrucoes(saida);

					
					System.out.println("\t Iniciado : " + getDate().toString());
					System.out.println("\t - Tamanho : " + CompilerC.tamanhoObjeto(saida));
					System.out.println("\t Finalizado : " + getDate().toString());
					
					System.out.println("");

					
					ArrayList<AST> ASTSaida = CompilerC.Decompilar(saida);
				
					System.out.println("");
			

					System.out.println("");

					
					
					System.out.println( CompilerC.ImprimirArvoreDeInstrucoes_De(ASTSaida));
					
					
					
					System.out.println("");
					System.out.println("################ RUNTIME ################");
					System.out.println("");

					RunTime RunTimeC = new RunTime();
					String DI = getDate().toString();
					//RunTimeC.init(CompilerC.getASTS());
					
					RunTimeC.init(ASTSaida);
					
					String DF = getDate().toString();

					System.out.println("");
					System.out.println("----------------------------------------------");
					System.out.println("");

					System.out.println("\t - Iniciado : " + DI);
					System.out.println("\t - Finalizado : " + DF);

					System.out.println("\t - Erros : " + RunTimeC.getErros().size());

					if (RunTimeC.getErros().size() > 0) {
						System.out.println("\n\t ERROS DE EXECUCAO : ");

						for (String Erro : RunTimeC.getErros()) {
							System.out.println("\t\t" + Erro);
						}
					}

					System.out.println("");
					System.out.println("----------------------------------------------");

					
					
				}
				
				
		
				
				
			}

		}

	}

	public String getDate() {

		Calendar c = Calendar.getInstance();

		int dia = c.get(Calendar.DAY_OF_MONTH);
		int mes = c.get(Calendar.MONTH) + 1;
		int ano = c.get(Calendar.YEAR);

		int hora = c.get(Calendar.HOUR);
		int minutos = c.get(Calendar.MINUTE);
		int segundos = c.get(Calendar.SECOND);

		return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

	}

}
