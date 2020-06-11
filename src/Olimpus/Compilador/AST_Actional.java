package Olimpus.Compilador;

import Olimpus.Lexer.Token;
import Olimpus.Lexer.TokenTipo;

public class AST_Actional {

    private Compiler mCompiler;

    public AST_Actional(Compiler eCompiler){
        mCompiler=eCompiler;
    }

    public void AST_ACTION_GET(AST ASTPai) {

        Token TokenC = mCompiler.getTokenCorrente();

        AST AST_Corrente = new AST();
        AST_Corrente.setNome("ACTION_GET");

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
            mCompiler.getErros().add("Era esperado o nome de uma ACTION  !");
        }

        ASTPai.getASTS().add(AST_Corrente);

    }

    public void AST_ACTION_SET(AST ASTPai) {

        Token TokenC = mCompiler.getTokenCorrente();

        AST AST_Corrente = new AST();
        AST_Corrente.setNome("ACTION_SET");

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
            mCompiler.getErros().add("Era esperado um nome para uma ACTION !");
        }

        ASTPai.getASTS().add(AST_Corrente);

    }

}
