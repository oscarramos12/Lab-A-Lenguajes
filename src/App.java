import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {
        //(a|b)*(b|a)*abb
        //((ε|a)b*)*
        //(.|;)*-/.(.|;)*
        //(x|t)+((a|m)?)+
        //("(.(;(.;(.|;)+)*)*)*)
        String input = "(a|b)*a(a|b)(a|b)";
        Character concat = '@';
        ArrayList<Vertex> endsAFN = AFN.doAFN(input,concat);
        Vertex inicioAFD = AFD.doAFD(endsAFN.get(0), concat, input, endsAFN.get(1));       
        graficaAFN.graficar(endsAFN.get(0), "AFN");
        graficaAFN.graficar(inicioAFD, "AFD");
        System.out.println("done");
    }

}
