package Olimpus.Compilador;

import java.util.ArrayList;

public class AST {

	private String mNome;
	private String mValor;
	private String mSub;

	private ArrayList<AST> mASTS;

	public AST() {

		mASTS = new ArrayList<>();

		mNome = "";
		mValor = "";
		mSub = "";

	}

	public ArrayList<AST> getASTS() {
		return mASTS;
	}

	public String getNome() {
		return mNome;
	}

	public void setNome(String eNome) {
		mNome = eNome;
	}

	public String getValor() {
		return mValor;
	}

	public void setValor(String eValor) {
		mValor = eValor;
	}

	public String getSub() {
		return mSub;
	}

	public void setSub(String eSub) {
		mSub = eSub;
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

	public AST criarAST(String eAST) {
		AST mRet = new AST();
mRet.setNome(eAST);
		mASTS.add(mRet);
		

		return mRet;
	}
	
	
	public AST copiar() {
		
		
		AST copia = new AST();
		copia.setNome(this.getNome());
		copia.setValor(this.getValor());
		copia.setSub(this.getSub());

		for (AST a : getASTS()) {
			
			copia.getASTS().add(a.copiar());
			
		}
		
		return copia;
		
		
	}
	
	
	
	
	
	
	public void ImprimirArvoreDeInstrucoes() {

		System.out.println( this.getNome() + " -> " + this.getValor());

		for (AST a : getASTS()) {

			System.out.println(" " + a.getNome() + " -> " + a.getValor());

			ImprimirSubArvoreDeInstrucoes("   ", a);

		}

	}

	private void ImprimirSubArvoreDeInstrucoes(String ePref, AST ASTC) {

		for (AST a : ASTC.getASTS()) {

			System.out.println(ePref + a.getNome() + " -> " + a.getValor());

			ImprimirSubArvoreDeInstrucoes(ePref + "   ", a);

		}

	}
	
	
	public boolean seuNome(String eNome) {
		return this.mNome.contentEquals(eNome);
	}

	public boolean seuValor(String eValor) {
		return this.mValor.contentEquals(eValor);
	}

	public boolean seuSub(String eSub) {
		return this.mSub.contentEquals(eSub);
	}
	
}
