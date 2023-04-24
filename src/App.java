import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        //(a|b)*(b|a)*abb
        //((ε|a)b*)*
        //(.|;)*-/.(.|;)*
        //(x|t)+((a|m)?)+
        //(\"(.(;(.;(.|;)+)*)*)*)
        //null ws @10000000 ID @|0 1 |2 |3 |4 |5 |6 |7 |8 |9 |0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@. 0 1 |2 |3 |4 |5 |6 |7 |8 |9 |@0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@ε |@E + - |ε |0 1 |2 |3 |4 |5 |6 |7 |8 |9 |@0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@@ε |@NUMBER @|10000001 SEMICOLON @|10000002 ASSIGNOP @|10000003 LT @|10000004 EQ @|10000005 PLUS @|10000006 MINUS @|10000007 TIMES @|10000008 DIV @|10000009 LPAREN @|10000010 RPAREN @|
        /*String input = "((a)b)";
        Character concat = '@';
        ArrayList<Vertex> endsAFN = AFN.doAFN(input,concat);
        Vertex inicioAFD = AFD.doAFD(endsAFN.get(0), concat, input, endsAFN);       
        graficaAFN.graficar(endsAFN.get(0), "AFN");
        graficaAFN.graficar(inicioAFD, "AFD");*/
        //AFN.doAFNlex(arbolLex.getLexExp(), '@');
        ArrayList<String> readDoc = arbolLex.getLexExp();
        ArrayList<Vertex> endsAFN = AFN.doAFNlex(readDoc, '@', 2, 0);
        Character concat = '@';
        String input = AFN.getExpression(readDoc, 2, concat, 0);
        Vertex inicioAFD = AFD.doAFD(endsAFN.get(0), concat, input, endsAFN);
        //graficaAFN.graficar(endsAFN.get(0), "AFN");
        System.out.println("done");
    }

}