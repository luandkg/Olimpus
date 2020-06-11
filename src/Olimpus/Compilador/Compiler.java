package Olimpus.Compilador;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import Olimpus.Lexer.Lexer;
import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;
import Olimpus.Utils.Decompilador;
import Olimpus.Utils.Documento;
import Olimpus.Utils.Requerimento;

public class Compiler {

	private int mIndex;
	private int mTamanho;

	private ArrayList<Token> mTokens;
	private ArrayList<AST> mASTS;
	private ArrayList<String> mErros;

	private ArrayList<String> mRequisitados;

	private String mLocal;
	private AST AST_OLIMPUS;

	private ArrayList<Requerimento> mRequerimentos;

	public Compiler() {

		mIndex = 0;
		mTamanho = 0;
		mTokens = new ArrayList<>();
		mASTS = new ArrayList<>();
		mErros = new ArrayList<>();
		mLocal = "";
		mRequerimentos = new ArrayList<>();
		mRequisitados = new ArrayList<>();

		RequirimentosCarregar();

	}

	public boolean Continuar() {
		return mIndex < mTamanho;
	}

	public void Proximo() {
		mIndex += 1;
	}

	public Token getTokenCorrente() {
		return mTokens.get(mIndex);
	}

	public ArrayList<AST> getASTS() {
		return mASTS;
	}

	public AST getAST(String eAST) {
		AST mRet = null;

		for (AST mAST : mASTS) {
			if (mAST.getNome().contentEquals(eAST)) {
				mRet = mAST;
				break;
			}
		}

		return mRet;
	}

	public ArrayList<String> getErros() {
		return mErros;
	}

	public ArrayList<String> getRequisitados() {
		return mRequisitados;
	}

	public int Instrucoes() {
		int t = 0;

		for (AST a : getASTS()) {
			if (a.getASTS().size() > 0) {
				t += subInstrucoes(a);
			}
			t += 1;
		}

		return t;
	}

	private int subInstrucoes(AST ASTC) {
		int t = 0;

		for (AST a : ASTC.getASTS()) {
			if (a.getASTS().size() > 0) {
				t += subInstrucoes(a);
			}
			t += 1;
		}

		return t;
	}

	public void init(ArrayList<Token> eTokens, String eLocal) {
		mIndex = 0;

		mTokens.clear();
		mASTS.clear();
		mErros.clear();
		mRequisitados.clear();

		mLocal = eLocal;

		for (Token TokenC : eTokens) {
			if (TokenC.Tipo() != TokenTipo.COMENTARIO) {
				mTokens.add(TokenC);
			}
		}

		mTamanho = mTokens.size();

		AST AST_Raiz = new AST();
		AST_Raiz.setNome("OLIMPUS");

		mASTS.add(AST_Raiz);

		AST_OLIMPUS = AST_Raiz;

		Statements STA = new Statements(this, AST_Raiz);
		Agrupados mAgrupados = new Agrupados(this, getErros());

		while (Continuar()) {
			Token TokenC = mTokens.get(mIndex);

			if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("action")) {

				mAgrupados.AST_ACTION(AST_Raiz);

			} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("function")) {

				mAgrupados.AST_FUNCTION(AST_Raiz);

			} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("required")) {

				mAgrupados.AST_REQUIRED(AST_Raiz, mRequerimentos, mRequisitados, mLocal);

			} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("tags")) {

				mAgrupados.AST_TAGS(AST_Raiz);

			} else if (TokenC.Tipo() == TokenTipo.ID && TokenC.MesmoConteudo("call")) {

				STA.AST_CALL();
			} else if (TokenC.Tipo() == TokenTipo.ARROBA) {

				AST_ARROBA(STA.getRaiz());

			} else {

				// mComandos.STATEMENTS(AST_OLIMPUS, TokenC, false);

				mErros.add("Comando Deconhecido : " + TokenC.Conteudo());
			}

			mIndex += 1;
		}

		// EXPORTANDO ACTIONS E FUNCTIONS

		for (AST mAST : AST_Raiz.getASTS()) {
			if (mAST.getNome().contentEquals("ACTION")) {
				mASTS.add(mAST);
			} else if (mAST.getNome().contentEquals("FUNCTION")) {
				mASTS.add(mAST);
			} else if (mAST.getNome().contentEquals("TAGS")) {
				mASTS.add(mAST);
			}
		}

		// REMOVENDO REFERENCIAS INTERNAS DAS ACTIONS E FUNCTIONS

		for (AST mAST : mASTS) {

			if (mAST.getNome().contentEquals("ACTION")) {

				if (AST_OLIMPUS.getASTS().contains(mAST)) {
					AST_OLIMPUS.getASTS().remove(mAST);
				}

			} else if (mAST.getNome().contentEquals("FUNCTION")) {

				if (AST_OLIMPUS.getASTS().contains(mAST)) {
					AST_OLIMPUS.getASTS().remove(mAST);
				}

			} else if (mAST.getNome().contentEquals("TAGS")) {

				if (AST_OLIMPUS.getASTS().contains(mAST)) {
					AST_OLIMPUS.getASTS().remove(mAST);
				}

			}
		}

	}

	public void AST_ARROBA(AST ASTPai) {

		Token TokenC = this.getTokenCorrente();

		AST AST_Corrente = new AST();
		AST_Corrente.setNome("ARROBA");

		this.Proximo();
		boolean valorado = false;
		String valor = "0";

		if (this.Continuar()) {
			Token TokenC2 = this.getTokenCorrente();
			if (TokenC2.Tipo() == TokenTipo.TEXTO) {
				valorado = true;
				valor = (TokenC2.Conteudo());
			}
		}

		AST_Corrente.setValor(TokenC.Conteudo());
		AST_Corrente.setSub(valor);

		if (!valorado) {
			this.getErros().add("Era esperado um Texto !");
		}

		ASTPai.getASTS().add(AST_Corrente);

	}

	public String ImprimirObjeto() {

		String Objeto = "";

		for (AST a : getASTS()) {

			Objeto += ":" + a.getNome() + ": [ Valor='" + a.getValor() + "' Sub='" + a.getSub() + "' ] { \n";

			if (a.getASTS().size() > 0) {
				Objeto += "\n";

				Objeto += ImprimirSubObjeto("   ", a);

				Objeto += " }\n";
			} else {
				Objeto += "}\n";
			}

		}

		return Objeto;

	}

	private String ImprimirSubObjeto(String ePref, AST ASTC) {

		String Objeto = "";

		for (AST a : ASTC.getASTS()) {

			Objeto += ePref + ":" + a.getNome() + ": [ Valor='" + a.getValor() + "' Sub='" + a.getSub() + "' ] { ";

			if (a.getASTS().size() > 0) {
				Objeto += "\n";

				Objeto += ImprimirSubObjeto(ePref + "   ", a);

				Objeto += ePref + " }\n";
			} else {
				Objeto += "}\n";
			}

		}

		return Objeto;

	}

	public String ImprimirArvoreDeInstrucoes() {
		
		Decompilador DecompiladorC = new Decompilador();
		
		return DecompiladorC.ImprimirArvoreDeInstrucoes_De(getASTS());
	}
	
	public String ImprimirArvoreDeInstrucoes_De(ArrayList<AST> lsAST) {
	
	Decompilador DecompiladorC = new Decompilador();
		
		return DecompiladorC.ImprimirArvoreDeInstrucoes_De(lsAST);
		
	}
	
	
	
	public String MontandoArvoreDeInstrucoes() {

		Documento DocumentoC = new Documento();

		DocumentoC.adicionar("");

		for (AST a : getASTS()) {

			DocumentoC.adicionar("" + a.getNome() + "[\"" + a.getValor() + "\":\"" + a.getSub() + "\"] { ");

			MontandoSubArvoreDeInstrucoes("", a, DocumentoC);
			DocumentoC.adicionar("}");
			
		}

		DocumentoC.adicionar("");

		
		return DocumentoC.getConteudo().replace("\n", "");
	}

	private void MontandoSubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

		for (AST a : ASTC.getASTS()) {

			DocumentoC.adicionar( a.getNome() + "[\"" + a.getValor() + "\":\"" + a.getSub() + "\"] {");

			MontandoSubArvoreDeInstrucoes("", a, DocumentoC);
			DocumentoC.adicionar("}");

		}

	}
	
	public void CompilacaoArvoreDeInstrucoes(String eArquivo) {
		
		String documento = MontandoArvoreDeInstrucoes();
		
		byte[] bytes = documento.getBytes(StandardCharsets.UTF_8); 
		
		int t = bytes.length;
		//System.out.println("Tam : " + t);
		
		int i= 0 ;
		int o = documento.length();
		
		int auxilador = 53;
		
		while(i<o) {
			int novo =(int) bytes[i] ;
			
			novo +=auxilador;
			if(novo>=255) {novo-=255;}
			
			
			bytes[i] =(byte) novo;
			i+=1;
		}
		
		Path path = Paths.get(eArquivo);
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	public String tamanhoObjeto(String eArquivo) {

        File file = new File(eArquivo);	
        
        long t = file.length();
              
        
		return t + "";
	}
	
	
	public String mapaObjeto(String eArquivo) {
		
		String saida = "";
		
		Path dpath = Paths.get(eArquivo);

		
		try {
			byte[] l = Files.readAllBytes(dpath);
			
			int li = 0;
			int lo = l.length;
			
			int d = 0;
			
			while(li<lo) {
				int novo =(int) l[li] ;
				
				saida += " " + novo;
				
				
				if(d>=50) {
					d=0;
					saida+="\n";
				}
				
				li+=1;
				d+=1;
			}
			
	
			
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return saida;
		
	}
	
	
	public ArrayList<AST> Decompilar(String eArquivo) {
		
		ArrayList<AST> ASTSaida = new ArrayList<AST>();
		
		
		Path dpath = Paths.get(eArquivo);
		int auxilador = 53;

		String saida = "";
		
		try {
			byte[] l = Files.readAllBytes(dpath);
			
			int li = 0;
			int lo = l.length;
			
			while(li<lo) {
				int novo =(int) l[li] ;
				
				novo -=auxilador;
				if(novo<0) {novo+=256;}
				
				
				l[li] =(byte) novo;
				li+=1;
			}
			
			//try {
			//	Files.write(dpath, l);
			//} catch (IOException e) {
			//	e.printStackTrace();
			//}
			
			saida  = new String(l, "UTF-8");
			
		//	System.out.println("Decompilado : " + saida);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		Decompilador DC = new Decompilador();
		
		ASTSaida=DC.decompilar(saida);
		
		return ASTSaida;
		
	}
	
	

	public void RequirimentosCarregar() {

		Documento operator = new Documento();

		operator.adicionarLinha("function somar {");

		operator.adicionarLinha("	action param {");
		operator.adicionarLinha("		make a");
		operator.adicionarLinha("		make b");
		operator.adicionarLinha("	}");

		operator.adicionarLinha("	action return {");

		operator.adicionarLinha("		def a");
		operator.adicionarLinha("		add b ");

		operator.adicionarLinha("		make c");
		operator.adicionarLinha("		apply c");

		operator.adicionarLinha("		ret c");
		operator.adicionarLinha("	}");

		operator.adicionarLinha("}");

		mRequerimentos.add(new Requerimento("operador", operator.getConteudo()));

	}

}
