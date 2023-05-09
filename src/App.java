import java.util.ArrayList;

public class App {

    public static void main(String[] args) throws Exception {

        ArrayList<String> readDoc = arbolLex.getLexExp("slr-4.yal");
        createFile.doFile(readDoc);

        /*ArrayList<Vertex> endsAFN = AFN.doAFNlex(readDoc, '@', start, end);
        Character concat = '@';

        String input = AFN.getExpression(readDoc, start, concat, end);
        AFD.doAFD(endsAFN.get(0), concat, input, endsAFN);
        
        System.out.println("done");*/
    }

}

/*para el archivo que tiene que generar su pongo que escaneo el .yal para obtener conjuntos y ponerlos como lex.add("IDS", "abcd...")
 * luego con esos conjuntos es lo que mando a hacer el afd
 * 
 * 0 1 |2 |3 |4 |5 |6 |7 |8 |9 |0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@. 0 1 |2 |3 |4 |5 |6 |7 |8 |9 |@0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@|ε |@E plusSymbolSpecial - ||ε |0 1 |2 |3 |4 |5 |6 |7 |8 |9 |@0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@|ε |@@NUMBER @
 * 0 1 |2 |3 |4 |5 |6 |7 |8 |9 |0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@. 0 1 |2 |3 |4 |5 |6 |7 |8 |9 |@0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@ε |@E plusSymbolSpecial - |ε |0 1 |2 |3 |4 |5 |6 |7 |8 |9 |@0 1 |2 |3 |4 |5 |6 |7 |8 |9 |*@@ε |@NUMBER @
 * 
 * [((A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z(A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|(_)*|0|1|2|3|4|5|6|7|8|9)*)ID), (((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*((.(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)|ε)(E(((plusSymbolSpecial|-)|ε)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)|ε))NUMBER), ((;)SEMICOLON), ((:=)ASSIGNOP), ((<)LT), ((=)EQ), ((plusSymbol)PLUS), ((-)MINUS), ((multiSymbol)TIMES), ((/)DIV), ((lparenSymbol)LPAREN), ((rparenSymbol)RPAREN)]
 * [((A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z(A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|(_)*|0|1|2|3|4|5|6|7|8|9)*)ID), (((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*((.(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)|?)(E(((plusSymbolSpecial|-)|?)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)|?))NUMBER), ((;)SEMICOLON), ((:=)ASSIGNOP), ((<)LT), ((=)EQ), ((plusSymbol)PLUS), ((-)MINUS), ((multiSymbol)TIMES), ((/)DIV), ((lparenSymbol)LPAREN), ((rparenSymbol)RPAREN)]
 */