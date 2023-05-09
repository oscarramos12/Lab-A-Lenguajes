import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class runExpression {
    public static void readFile(String dir, Vertex root){
        try (BufferedReader br = new BufferedReader(new FileReader(dir))) {
            String line;
            while ((line = br.readLine()) != null) {
               simular(root,line);
            }
         } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
         }
    }

    public static void simular(Vertex root, String line){
       /* ArrayList<Edges> rootEdges = root.getNextEdges();
        ArrayList<Edges> esperando = new ArrayList<Edges>(rootEdges);
        HashMap<String,String> finalStates = new HashMap<String,String>();
        while(!esperando.isEmpty()){
            Edges currentEdge = esperando.remove(0);
        }*/
    }

}
