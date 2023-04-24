import java.util.Stack;
import java.lang.Character;
import java.util.List;
import java.util.Arrays;



public class InFixPostFix {
    public static boolean letterOrDigit(char c)
    {
        if (Character.isLetterOrDigit(c))
            return true;
        else
            return false;
    }
 
    public static int getPrecedence(char ch, Character concat)
    {
        if(ch == '(')
            return 1;
        else if(ch == '|')
            return 2;
        else if(ch == concat)
            return 3;
        else if (ch == '+' || ch == '?' || ch =='*')
            return 4;
        else if (ch == '^')
            return 5;
        else
            return 6;
    }
    
   
    public static String newFormat(String expresion, Character concat) {
        List<Character> todo = Arrays.asList('+','*','?','|','^');
        String formateado = new String();
        

        for (int i = 0; i < expresion.length();i++) {
            Character char1 = expresion.charAt(i);

            if (i + 1 < expresion.length()) {
                Character char2 = expresion.charAt(i + 1);

                formateado += Character.toString(char1);

                if ((!char1.equals('(') && !char2.equals(')') && !(char1 == '|' || char1 == '^') && !todo.contains(char2))) {
                    formateado += concat;
                }
            }
        }
        formateado += expresion.charAt(expresion.length() - 1);
        System.out.println("Formateado:"+formateado);
        return formateado;
    }

    public static String cambioMas(String expresion){
        for (int i = 0; i < expresion.length(); i++) {

            Character char1 = expresion.charAt(i);

            if (char1.equals('+')) {

                if (Character.toString(expresion.charAt(i-1)).equals(")")) {

                    int parentesis = 0;

                    for (int x = (i-1); x >= 0; x--) {
                        if ((x != (i-1)) && (Character.toString(expresion.charAt(x)).equals(")"))) {

                            parentesis++;

                        }

                        if ((Character.toString(expresion.charAt(x)).equals("("))) {
                            if (parentesis != 0) {
                                parentesis--;
                            } 
                            else {

                                String repetir = (String) expresion.subSequence(x, i);
                                expresion = expresion.substring(0, x) + repetir + repetir + "*" + expresion.substring(i+1);

                            }
                        }
                    }
                } 
                else {

                    String repetir = Character.toString(expresion.charAt(i-1));
                    expresion = expresion.substring(0, i-1) + repetir + repetir + "*" +expresion.substring(i+1);

                }
            }
        }
        return expresion;
    }

    public static String cambioInterrogacion(String expresion){
        for (int i = 0; i < expresion.length(); i++) {

            Character char1 = expresion.charAt(i);
            
            if (char1.equals('?')) {

                if (Character.toString(expresion.charAt(i-1)).equals(")")) {

                    for (int x = (i-1); x >= 0; x--) {

                        if ((Character.toString(expresion.charAt(x)).equals("("))) {
                            if (x != 0) {
                                
                                String cambioExpresion = (String) expresion.subSequence(x, i);
                                expresion = expresion.substring(0, x) + "(" + cambioExpresion + "|ε)" + expresion.substring(i+1);
                                break;

                            }
                        }
                    }
                    
                } 
                else {                    
                    expresion = expresion.substring(0, i-1) + "(" + Character.toString(expresion.charAt(i-1)) + "|ε)" + expresion.substring(i+1);
                }
            }
        }
        return expresion;
    }
    //creo que el error es que tengo letterordigit cuando no importa solo con que no sea un operador
    public static String toPostFix(String expression, Character concat)
    {
        Stack<Character> stack = new Stack<>();
 
        String output = new String("");
        expression = newFormat(expression,concat);
        expression = cambioInterrogacion(expression);
        expression = cambioMas(expression);

        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);
 

            if (letterOrDigit(c))
                output += c;

            else if (c == '(')
                stack.push(c);
 
            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(')
                    output += stack.pop();
 
                stack.pop();
            }
 
            else {
                while (!stack.isEmpty() && getPrecedence(c, concat) <= getPrecedence(stack.peek(), concat)) {
                    output += stack.pop();
                }
                stack.push(c);
            }
        }
 
        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "This expression is invalid";
            output += stack.pop();
        }
        return output;
    }
 
    /*public static void main(String[] args)
    {
        //(x|t)+((a|m)?)+
        //((e.((.|e)|ε).(f.(((g|-)|ε).e)|ε)).x)
        String expression = "(x|t)+((a|m)?)+";
        System.out.println(toPostFix(expression, '%'));
    }*/
}
