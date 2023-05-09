import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class createFile {

    public static void doFile(ArrayList<String> expresiones){
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Oscar\\Desktop\\Lab A\\src\\compilado.java", StandardCharsets.UTF_8);
            writer.write("import java.util.ArrayList;\n");
            writer.write("import java.util.HashMap;\n");
            writer.write("import java.util.LinkedHashMap;\n");
            writer.write("public class compilado {\n");
            writer.write("public static void main(String[] args){\n");
            writer.write("ArrayList<String> expresiones = new ArrayList<String>();\n");
            for (int i  = 0; i < expresiones.size(); i++){
                writer.write("expresiones.add(\"" + expresiones.get(i) + "\");\n");
            }
            writer.write("ArrayList<Vertex> endsAFN = AFN.doAFNlex(expresiones, '@', 0, 0);\n");
            writer.write("String input = AFN.getExpression(expresiones, 0, '@', 0);\n");
            writer.write("Vertex rootAFD = AFD.doAFD(endsAFN.get(0), '@', input, endsAFN);\n");
            writer.write("LinkedHashMap<String, ArrayList<String>> grammar = lr.getGrammar(\"slr-1.yalp\", rootAFD);\n");
            writer.write("HashMap<String, ArrayList<String>> first = lr.computeFirst(grammar);\n");
            writer.write("lr.computeFollow(grammar, first);\n");
            writer.write("lr.computeClosure(grammar);\n");
            writer.write("}\n");
            writer.write("}");
            writer.close();
            System.out.println("File created successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }
    
}   
