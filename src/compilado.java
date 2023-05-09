import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
public class compilado {
public static void main(String[] args){
ArrayList<String> expresiones = new ArrayList<String>();
expresiones.add("((A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z(A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|Z|a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z|(_)*|0|1|2|3|4|5|6|7|8|9)*)ID)");
expresiones.add("(((0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*((.(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)|ε)(E(((plusSymbolSpecial|-)|ε)(0|1|2|3|4|5|6|7|8|9)(0|1|2|3|4|5|6|7|8|9)*)|ε))NUMBER)");
expresiones.add("((;)SEMICOLON)");
expresiones.add("((:=)ASSIGNOP)");
expresiones.add("((<)LT)");
expresiones.add("((=)EQ)");
expresiones.add("((plusSymbol)PLUS)");
expresiones.add("((-)MINUS)");
expresiones.add("((multiSymbol)TIMES)");
expresiones.add("((/)DIV)");
expresiones.add("((lparenSymbol)LPAREN)");
expresiones.add("((rparenSymbol)RPAREN)");
ArrayList<Vertex> endsAFN = AFN.doAFNlex(expresiones, '@', 0, 0);
String input = AFN.getExpression(expresiones, 0, '@', 0);
Vertex rootAFD = AFD.doAFD(endsAFN.get(0), '@', input, endsAFN);
LinkedHashMap<String, ArrayList<String>> grammar = lr.getGrammar("slr-1.yalp", rootAFD);
HashMap<String, ArrayList<String>> first = lr.computeFirst(grammar);
lr.computeFollow(grammar, first);
lr.computeClosure(grammar);
}
}