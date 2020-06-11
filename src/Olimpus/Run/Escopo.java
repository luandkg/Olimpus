package Olimpus.Run;

import java.util.ArrayList;

import Olimpus.Compilador.AST;

public class Escopo {

	private ArrayList<ItemCash> mItens;
	private RunTime mRunTime;
	private Escopo mEscopoAnterior;

	private ArrayList<AST> mFunction_Set;
	private ArrayList<AST> mCall_Set;
	private ArrayList<AST> mTag_Set;

	private float VALOR;

	private int TABULACAO;
	private float SNAPSHOT;
	private float RETORNAVEL;
	private String FUNCTION_GET;

	//private Escopo mEscopo;

	private String DO;
	private ArrayList<String> withs;

	private boolean mCancel;
	private boolean mContinue;
	
	public Escopo(RunTime eRunTime, Escopo eEscopoAnterior) {
		mItens = new ArrayList<>();
		mRunTime = eRunTime;
		mEscopoAnterior = eEscopoAnterior;

		mFunction_Set = new ArrayList<AST>();
		mCall_Set = new ArrayList<AST>();
		mTag_Set = new ArrayList<AST>();

		mRunTime = eRunTime;

		VALOR = 0;
		TABULACAO = 0;
		SNAPSHOT = 0;
		RETORNAVEL = 0;
		FUNCTION_GET = "";


		DO = "";
		withs = new ArrayList<>();
		
		if(mEscopoAnterior!=null) {
			
			setEscopoAnterior(mEscopoAnterior);
			
		}
	}
	
	public Escopo getEscopoAnterior() {
		return mEscopoAnterior;
	}

	public void setEscopoAnterior(Escopo eEscopoAnterior) {
		mEscopoAnterior = eEscopoAnterior;


		mCancel = false;
		mContinue = false;
		
		for (AST i : mEscopoAnterior.mCall_Set) {

			mCall_Set.add(i);

		}

		for (AST i : mEscopoAnterior.mFunction_Set) {

			mFunction_Set.add(i);

		}

		for (AST i : mEscopoAnterior.mTag_Set) {

			mTag_Set.add(i);

		}


	}

	public boolean run(AST ASTC) {

		boolean ret = true;

		// System.out.println("RUNNING " + ASTC.getNome() + " :: " + ASTC.getValor());

		// System.out.println("Running : " + DO);
		
		Run_Comandos ComandosSimples = new Run_Comandos();
		
		if (DO.length() > 0) {

			if (ASTC.getNome().contentEquals("WITH") || ASTC.getNome().contentEquals("RUN")
					|| ASTC.getNome().contentEquals("TO")) {

			} else {
				mRunTime.getErros().add("Era esperado o comando WITH, RUN ou TO ");
				return false;
			}

		}

		if (ASTC.getNome().contentEquals("DEF")) {

			ComandosSimples.Run_Def(ASTC, this);

		} else if (ASTC.getNome().contentEquals("ADD")) {

			ComandosSimples.Run_Add(ASTC, this);


		} else if (ASTC.getNome().contentEquals("SUB")) {

			ComandosSimples.Run_Sub(ASTC, this);


		} else if (ASTC.getNome().contentEquals("MUX")) {

			if (ASTC.getSub().contentEquals("Num")) {
				VALOR *= Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				VALOR *= Use(ASTC.getValor());
			}

		} else if (ASTC.getNome().contentEquals("DIV")) {

			float divisor = 0;

			if (ASTC.getSub().contentEquals("Num")) {
				divisor = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				divisor = Use(ASTC.getValor());
			}
			if (divisor != 0) {
				VALOR /= divisor;
			} else {
				mRunTime.getErros().add("Operação Invalida : O Divisor nao pode ser nulo !");
				ret = false;
			}

		} else if (ASTC.getNome().contentEquals("DIF")) {

			float diferenca = 0;

			if (ASTC.getSub().contentEquals("Num")) {
				diferenca = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				diferenca = Use(ASTC.getValor());
			}

			if (VALOR < diferenca) {
				VALOR = diferenca - VALOR;
			} else {
				VALOR = 0;
			}
		} else if (ASTC.getNome().contentEquals("REDIF")) {

			float diferenca = 0;

			if (ASTC.getSub().contentEquals("Num")) {
				diferenca = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				diferenca = Use(ASTC.getValor());
			}

			if (VALOR > diferenca) {
				VALOR = VALOR - diferenca;
			} else {
				VALOR = 0;
			}
		} else if (ASTC.getNome().contentEquals("NEG")) {

			VALOR = VALOR * (-1);
		} else if (ASTC.getNome().contentEquals("MOD")) {

			if (VALOR < 0) {
				VALOR = VALOR * (-1);
			}

		} else if (ASTC.getNome().contentEquals("GREAT")) {

			float tmp = 0;
			if (ASTC.getSub().contentEquals("Num")) {
				tmp = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				tmp = Use(ASTC.getValor());
			}

			if (VALOR > tmp) {
				VALOR = 1;
			} else {
				VALOR = -1;
			}

		} else if (ASTC.getNome().contentEquals("LESS")) {

			float tmp = 0;
			if (ASTC.getSub().contentEquals("Num")) {
				tmp = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				tmp = Use(ASTC.getValor());
			}

			if (VALOR < tmp) {
				VALOR = 1;
			} else {
				VALOR = -1;
			}

		} else if (ASTC.getNome().contentEquals("EQUAL")) {

			float tmp = 0;
			if (ASTC.getSub().contentEquals("Num")) {
				tmp = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				tmp = Use(ASTC.getValor());
			}

			if (VALOR == tmp) {
				VALOR = 1;
			} else {
				VALOR = -1;
			}
		} else if (ASTC.getNome().contentEquals("MIS")) {

			float tmp = 0;
			if (ASTC.getSub().contentEquals("Num")) {
				tmp = Float.parseFloat(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				tmp = Use(ASTC.getValor());
			}

			if (VALOR != tmp) {
				VALOR = 1;
			} else {
				VALOR = -1;
			}

		} else if (ASTC.getNome().contentEquals("__MEMORY__")) {

			Listar();

		} else if (ASTC.getNome().contentEquals("STACK")) {

			String mtabs = "";

			if (TABULACAO > 0) {
				for (int t = 0; t < TABULACAO; t++) {
					mtabs += "\t";
				}

			}

			System.out.println(mtabs + VALOR);
		} else if (ASTC.getNome().contentEquals("STACK_VALUE")) {

			System.out.print(VALOR);
		} else if (ASTC.getNome().contentEquals("STACK_VALUE_INT")) {

			ComandosSimples.Run_Stack_Value_Int(ASTC, this);

		} else if (ASTC.getNome().contentEquals("STACK_VALUE_DEC")) {

			ComandosSimples.Run_Stack_Value_Dec(ASTC, this);
		
		} else if (ASTC.getNome().contentEquals("STATUS")) {

			ComandosSimples.Run_Stack_Value_Status(ASTC, this);

			
		

		} else if (ASTC.getNome().contentEquals("TAB")) {
			TABULACAO = Integer.parseInt(ASTC.getValor());
		} else if (ASTC.getNome().contentEquals("WHITE")) {
			System.out.println("");

			int l = Integer.parseInt(ASTC.getValor());
			if (l > 0) {
				for (int ii = 0; ii < l; ii++) {
					System.out.println("");
				}
			}

		} else if (ASTC.getNome().contentEquals("MAKE")) {

			Make(ASTC.getValor());
		} else if (ASTC.getNome().contentEquals("CONST")) {

			Const(ASTC.getValor(), VALOR);

		} else if (ASTC.getNome().contentEquals("APPLY")) {

			Apply(ASTC.getValor(), VALOR);
		} else if (ASTC.getNome().contentEquals("USE")) {

			VALOR = Use(ASTC.getValor());

		} else if (ASTC.getNome().contentEquals("CLEAN")) {

			VALOR = 0;
		} else if (ASTC.getNome().contentEquals("SNAPSHOT")) {

			SNAPSHOT = VALOR;
		} else if (ASTC.getNome().contentEquals("ROLLBACK")) {

			VALOR = SNAPSHOT;
		} else if (ASTC.getNome().contentEquals("CALL")) {

			if (Caller(ASTC) == false) {
				ret = false;
			}

		} else if (ASTC.getNome().contentEquals("DO")) {

			DO = ASTC.getValor();
			withs.clear();

		} else if (ASTC.getNome().contentEquals("WITH")) {

			if (ASTC.getSub().contentEquals("Num")) {
				withs.add(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Var")) {
				withs.add(String.valueOf(Use(ASTC.getValor())));
			}
		} else if (ASTC.getNome().contentEquals("RUN")) {

			if (Runner(ASTC, DO, withs, this) == false) {
				ret = false;
			}

			DO = "";
			withs.clear();
		} else if (ASTC.getNome().contentEquals("TO")) {

			if (RunnerTo(ASTC, DO, withs, this, ASTC.getValor()) == false) {
				ret = false;
			}

			DO = "";
			withs.clear();
		} else if (ASTC.getNome().contentEquals("RET")) {

			// System.out.println("RET : " + ASTC.getValor() + " : " + ASTC.getSub());

			if (ASTC.getSub().contentEquals("Var")) {
				RETORNAVEL = Use(ASTC.getValor());
			} else if (ASTC.getSub().contentEquals("Num")) {
				RETORNAVEL = Float.parseFloat(ASTC.getValor());
			}

		} else if (ASTC.getNome().contentEquals("ACTION")) {
		} else if (ASTC.getNome().contentEquals("FUNCTION")) {
		} else if (ASTC.getNome().contentEquals("TAGS")) {

		} else if (ASTC.getNome().contentEquals("CONDITION")) {

			Run_Condition C = new Run_Condition();

			ret = C.init(ASTC, this, mRunTime);

		} else if (ASTC.getNome().contentEquals("STEP")) {

			Run_Step C = new Run_Step();

			ret = C.init(ASTC, this, mRunTime,this);
			
		} else if (ASTC.getNome().contentEquals("LOOP")) {

			Run_Loop C = new Run_Loop();

			ret = C.init(ASTC, this, mRunTime);

		} else if (ASTC.getNome().contentEquals("CANCEL")) {
			this.setCancel(true);
		} else if (ASTC.getNome().contentEquals("CONTINUE")) {
			this.setContinue(true);

		} else if (ASTC.getNome().contentEquals("ARROBA")) {

			System.out.println(" ->> " + ASTC.getValor() + " :: " + ASTC.getSub());

		} else if (ASTC.getNome().contentEquals("FUNCTION_GET")) {
			FUNCTION_GET = ASTC.getValor();

			ret = VerificarType(FUNCTION_GET);

		} else if (ASTC.getNome().contentEquals("FUNCTION_SET")) {

			if (FUNCTION_GET.length() > 0) {

				ret = DefinicaoType(FUNCTION_GET, ASTC.getValor());

			} else {
				mRunTime.getErros().add("Houve um erro com o FUNCTION_SET : " + ASTC.getValor());
			}

		} else {

			mRunTime.getErros().add("AST " + ASTC.getNome());
			ret = false;

		}

		return ret;
	}
	
	public void AplicarTags() {
		for (AST i : mTag_Set) {
			for (AST Tag : i.getASTS()) {

				this.Make(i.getValor() + "_" + Tag.getValor());
				this.Apply(i.getValor() + "_" + Tag.getValor(), Float.parseFloat(Tag.getSub()));

			}
		}
	}

	public void Make(String eNome) {
		boolean enc = false;

		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
			}

		}

		if (enc) {
			mRunTime.getErros().add("Variavel Duplicada : " + eNome);
		} else {
			mItens.add(new ItemCash(eNome));

		}
	}

	public void Const(String eNome, float eValor) {
		boolean enc = false;

		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
			}

		}

		if (enc) {
			mRunTime.getErros().add("Variavel Duplicada : " + eNome);
		} else {
			ItemCash Novo = new ItemCash(eNome);
			Novo.setTipo(1);
			Novo.setValor(eValor);
			mItens.add(Novo);

		}
	}

	public void Apply(String eNome, float eValor) {
		boolean enc = false;

		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				if (i.getTipo() == 0) {
					i.setValor(eValor);
					//System.out.println("Aplicando Valor em : " + eNome + " -->> " + eValor);
				} else {
					mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
				}
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
				if (IC.getTipo() == 0) {
					IC.setValor(eValor);
				} else {
					mRunTime.getErros().add("A constante nao pode ser alterada : " + eNome);
				}

			}

		}

		if (!enc) {
			mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);

		}
	}

	public float Use(String eNome) {
		boolean enc = false;
		float ret = 0;

		//Listar();
		
		for (ItemCash i : mItens) {
			if (i.getNome().contentEquals(eNome)) {
				enc = true;
				ret = i.getValor();
				break;
			}
		}

		if (!enc) {

			ItemCash IC = BuscarAnterior(eNome);
			if (IC != null) {
				enc = true;
				ret = IC.getValor();

			}

		}

		if (!enc) {
			mRunTime.getErros().add("Variavel nao Encontrada : " + eNome);
		}

		return ret;
	}

	public ItemCash BuscarAnterior(String eNome) {
		ItemCash IC = null;
		boolean enc = false;

	//	System.out.println("Buscando anterior : " + eNome);
		
		if (mEscopoAnterior != null) {
			for (ItemCash i : mEscopoAnterior.mItens) {
				if (i.getNome().contentEquals(eNome)) {
					enc = true;
					IC = i;
					break;
				}
			}

		}

		if (!enc) {
			if (mEscopoAnterior != null) {
				return mEscopoAnterior.BuscarAnterior(eNome);
			}

		}

		return IC;
	}

	public void Listar() {
		for (ItemCash i : mItens) {

			System.out.println(" - " + i.getNome() + " = " + i.getValor());

		}

	}

	public void Function_Apply(AST eAST) {

		mFunction_Set.add(eAST);

	}

	public void Call_Apply(AST eAST) {

		mCall_Set.add(eAST);

	}

	public void Tags_Apply(AST eAST) {

		mTag_Set.add(eAST);

	}

	public void Functions_Listar() {

		for (AST AST_FUNCTION : mFunction_Set) {

			System.out.println(" FS -> " + AST_FUNCTION.getValor());

		}

	}

	public boolean VerificarType(String eNome) {

		boolean enc = false;
		boolean ret = true;

		if (enc == false) {

			for (AST AST_FUNCTION : mFunction_Set) {

				if (AST_FUNCTION.getValor().contentEquals(eNome)) {

					enc = true;
					break;
				}

			}

		}

		if (enc == false) {
			mRunTime.getErros().add("Bloco não encontrado : " + eNome);
			ret = false;
		}

		return ret;

	}

	public boolean DefinicaoType(String eTypeGet, String eTypeDef) {

		if (mRunTime.getErros().size() > 0) {
			return false;
		}

		// System.out.println("Fnction SET :: " + eTypeGet + " -> " + eTypeDef);

		boolean enc = false;
		boolean ret = true;

		AST Copiando = null;
		boolean enc_action = false;
		boolean duplicado = false;

		Copiando = null;
		enc_action = false;
		duplicado = false;

		for (AST AST_ACTION : mFunction_Set) {

			if (AST_ACTION.getValor().contentEquals(eTypeGet)) {

				Copiando = AST_ACTION.copiar();

				enc_action = true;

			}

			if (AST_ACTION.getValor().contentEquals(eTypeDef)) {
				duplicado = true;
				break;
			}

		}

		if (duplicado == true) {
			mRunTime.getErros().add("Já existe uma FUNCTION chamada : " + eTypeDef);
			return false;
		} else {
			if (enc_action == true) {

				Copiando.setValor(eTypeDef);
				mFunction_Set.add(Copiando);
				return true;
			}
		}

		return false;

	}

	public boolean Runner(AST ASTRaiz, String Function_DO, ArrayList<String> withs, Escopo eInternal) {

		if (mRunTime.getErros().size() > 0) {
			return false;
		}

		boolean enc = false;
		boolean ret = true;

		for (AST AST_FUNCTION : mFunction_Set) {

			if (AST_FUNCTION.getValor().contentEquals(Function_DO)) {

				Escopo mInternal = new Escopo(mRunTime,null);
				// mInternal.setEscopoAnterior(OlimpusInternal.getMemoria());

				for (AST FS : mFunction_Set) {
					mInternal.Function_Apply(FS);
				}
				for (AST FS : mCall_Set) {
					mInternal.Call_Apply(FS);
				}

				for (AST FS : mTag_Set) {
					mInternal.Tags_Apply(FS);
				}

				mInternal.AplicarTags();

				AST AST_Param = null;
				AST AST_Return = null;

				for (AST ASTC : AST_FUNCTION.getASTS()) {

					if (ASTC.getValor().contentEquals("param")) {
						AST_Param = ASTC;
					}
					if (ASTC.getValor().contentEquals("return")) {
						AST_Return = ASTC;
					}

				}

				int Entrada = 0;
				int CParamentros = 0;

				for (AST ASTC : AST_Param.getASTS()) {

					if (ASTC.getNome().contentEquals("MAKE")) {
						CParamentros += 1;

						if (Entrada >= withs.size()) {
							mRunTime.getErros().add("FUNCTION " + AST_FUNCTION.getValor() + " faltam argumentos ");
							return false;
						}

						mInternal.Make(ASTC.getValor());
						mInternal.Apply(ASTC.getValor(), Float.parseFloat(withs.get(Entrada)));

						// System.out.println("Parametro " + ASTC.getValor() + " com "
						// +withs.get(Entrada) );
						Entrada += 1;

					}

				}

				if (CParamentros != withs.size()) {
					mRunTime.getErros().add("FUNCTION " + AST_FUNCTION.getValor() + " problemas com argumentos ");
					return false;
				}

				for (AST ASTC : AST_Return.getASTS()) {
					if (ASTC.getNome().contentEquals("TAGS")) {
						mInternal.run(ASTC);
					}

				}

				for (AST ASTC : AST_Return.getASTS()) {
					if (mInternal.run(ASTC) == false) {
						ret = false;
						break;
					}
				}

				// System.out.println(" Run " + Function_DO + "-->" +
				// mInternal.getRETORNAVEL());

				eInternal.setVALOR(mInternal.getRETORNAVEL());

				enc = true;
				break;
			}

		}

		if (enc == false) {
			mRunTime.getErros().add("FUNCTION não encontrada : " + Function_DO);
			ret = false;
		}

		return ret;
	}

	public boolean RunnerTo(AST ASTRaiz, String Function_DO, ArrayList<String> withs, Escopo eInternal,
			String eVariavel) {

		if (mRunTime.getErros().size() > 0) {
			return false;
		}

		boolean enc = false;
		boolean ret = true;

		for (AST AST_FUNCTION : mFunction_Set) {

			if (AST_FUNCTION.getValor().contentEquals(Function_DO)) {

				Escopo mInternal = new Escopo(mRunTime,null);
				// mInternal.setEscopoAnterior(OlimpusInternal.getMemoria());

				for (AST FS : mFunction_Set) {
					mInternal.Function_Apply(FS);
				}
				for (AST FS : mCall_Set) {
					mInternal.Call_Apply(FS);
				}

				for (AST FS : mTag_Set) {
					mInternal.Tags_Apply(FS);
				}

				mInternal.AplicarTags();

				AST AST_Param = null;
				AST AST_Return = null;

				for (AST ASTC : AST_FUNCTION.getASTS()) {

					if (ASTC.getValor().contentEquals("param")) {
						AST_Param = ASTC;
					}
					if (ASTC.getValor().contentEquals("return")) {
						AST_Return = ASTC;
					}

				}

				int Entrada = 0;
				int CParamentros = 0;

				for (AST ASTC : AST_Param.getASTS()) {

					if (ASTC.getNome().contentEquals("MAKE")) {
						CParamentros += 1;

						if (Entrada >= withs.size()) {
							mRunTime.getErros().add("FUNCTION " + AST_FUNCTION.getValor() + " faltam argumentos ");
							return false;
						}

						mInternal.Make(ASTC.getValor());
						mInternal.Apply(ASTC.getValor(), Float.parseFloat(withs.get(Entrada)));

						// System.out.println("Parametro " + ASTC.getValor() + " com "
						// +withs.get(Entrada) );
						Entrada += 1;

					}

				}

				if (CParamentros != withs.size()) {
					mRunTime.getErros().add("FUNCTION " + AST_FUNCTION.getValor() + " problemas com argumentos ");
					return false;
				}

				for (AST ASTC : AST_Return.getASTS()) {
					if (mInternal.run(ASTC) == false) {
						ret = false;
						break;
					}
				}

				// System.out.println(" Run " + Function_DO + "-->" +
				// mInternal.getRETORNAVEL());

				// eInternal.setVALOR(mInternal.getRETORNAVEL());
				eInternal.Apply(eVariavel, mInternal.getRETORNAVEL());

				enc = true;
				break;
			}

		}

		if (enc == false) {
			mRunTime.getErros().add("FUNCTION não encontrada : " + Function_DO);
			ret = false;
		}

		return ret;
	}

	public boolean Caller(AST ASTRaiz) {

		if (mRunTime.getErros().size() > 0) {
			return false;
		}

		boolean enc = false;
		boolean ret = true;

		for (AST AST_ACTION : mCall_Set) {

			if (AST_ACTION.getValor().contentEquals(ASTRaiz.getValor())) {

				Escopo mInternal = new Escopo(mRunTime,null);

				for (AST FS : mFunction_Set) {
					mInternal.Function_Apply(FS);
				}
				for (AST FS : mCall_Set) {
					mInternal.Call_Apply(FS);
				}

				for (AST FS : mTag_Set) {
					mInternal.Tags_Apply(FS);
				}

				mInternal.AplicarTags();

				for (AST ASTC : AST_ACTION.getASTS()) {
					ret = mInternal.run(ASTC);

				}

				// mInternal.Functions_Listar();

				enc = true;
				break;
			}

		}

		if (enc == false) {
			mRunTime.getErros().add("ACTION não encontrada " + ASTRaiz.getValor());
			ret = false;
		}

		return ret;
	}

	public float getRETORNAVEL() {
		return RETORNAVEL;
	}

	public void setVALOR(float eVALOR) {
		VALOR = eVALOR;
	}
	
	public float getValor() {
		return VALOR;
	}

	public int getTabulacao() {
		return TABULACAO;
	}
	
	public void setCancel(boolean eCancel) {
		mCancel = eCancel;
	}

	public boolean getCancel() {
		return mCancel;
	}

	public void setContinue(boolean eContinue) {
		mContinue = eContinue;
	}

	public boolean getContinue() {
		return mContinue;
	}

	

	
	
	public class ItemCash {

		private String mNome;
		private float mValor;
		private int mTipo;

		public ItemCash(String eNome) {
			this.mNome = eNome;
			mValor = 0;
			mTipo = 0;
		}

		public void setTipo(int eTipo) {
			mTipo = eTipo;
		}

		public int getTipo() {
			return mTipo;
		}

		public void setValor(float eValor) {
			mValor = eValor;
		}

		public float getValor() {
			return mValor;
		}

		public String getNome() {
			return mNome;
		}

	}

}
