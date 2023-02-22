import java.util.Stack;
import java.lang.Character;
import java.util.List;
import java.util.Arrays;



public class InFixPostFix {
    private static boolean letterOrDigit(char c)
    {
        if (Character.isLetterOrDigit(c))
            return true;
        else
            return false;
    }
 
    static int getPrecedence(char ch)
    {
        if(ch == '(')
            return 1;
        else if(ch == '|')
            return 2;
        else if(ch == '.')
            return 3;
        else if (ch == '+' || ch == '?' || ch =='*')
            return 4;
        else if (ch == '^')
            return 5;
        else
            return 6;
    }
    
   
    public static String newFormat(String expresion) {
        List<Character> todo = Arrays.asList('+','*','?','|','^');
        String formateado = new String();
        

        for (int i = 0; i < expresion.length();i++) {
            Character char1 = expresion.charAt(i);

            if (i + 1 < expresion.length()) {
                Character char2 = expresion.charAt(i + 1);

                formateado += char1;

                if ((!char1.equals('(') && !char2.equals(')') && !(char1 == '|' || char1 == '^') && !todo.contains(char2))) {
                    formateado += '.';
                }
            }
        }
        formateado += expresion.charAt(expresion.length() - 1);
        System.out.println("Formateado:"+formateado);
        return formateado;
    }

    public static String toPostFix(String expression)
    {
        Stack<Character> stack = new Stack<>();
 
        String output = new String("");
        expression = newFormat(expression);

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
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
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
        String expression = "(b|b)*abb(a|b)*";
        System.out.println(toPostFix(expression));
    }*/
}
