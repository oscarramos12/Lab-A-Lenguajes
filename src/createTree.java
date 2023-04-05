import java.util.HashSet;
import java.util.Set;
import java.util.Stack;


public class createTree {
    treeNode root;
    Set<Character> operators;

    public createTree(String postfix, Character concat) {
        operators = new HashSet<>();
        operators.add('+');
        operators.add(concat);
        operators.add('*');
        operators.add('|');
        operators.add('^');
        buildTree(postfix);
    }

    private void buildTree(String postfix) {
        Stack<treeNode> stack = new Stack<>();
        Character c;
        String word;
        for (int i = 0; i < postfix.length()-1; i++) {
            c = postfix.charAt(i);
            //if(c == '9'){
                System.out.println(c);
            //}
            
            if(c != ' '){
                if (operators.contains(c)) {
                    if(c != '*'){
                        treeNode right = stack.pop();
                        treeNode left = stack.pop();
                        treeNode node = new treeNode(Character.toString(c));
                        node.setHoja(left);
                        node.setHoja(right);
                        stack.push(node);
                    }
                    else{
                        treeNode single = stack.pop();
                        treeNode node = new treeNode(Character.toString(c));
                        node.setHoja(single);
                        stack.push(node);
                    }
                    
                } else {
                    word = "";
                    for (int getWord = i; getWord < postfix.length(); getWord++){
                        if(!operators.contains(postfix.charAt(getWord)) && postfix.charAt(getWord) != ' '){
                            word += postfix.charAt(getWord);
                        }
                        else if(postfix.charAt(getWord) == ' ' || operators.contains(postfix.charAt(getWord))){
                            i = word.length() + i - 1;
                            break;
                        }
                    }
                    if(word.equals("empty")){
                        treeNode node = new treeNode(" ");
                        stack.push(node);
                    }
                    else{
                        treeNode node = new treeNode(word);
                        stack.push(node);
                    }
                    
                }
            }
        }

        root = stack.pop();
    }
}
