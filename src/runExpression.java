import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


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
        Vertex currentVert = root;
        for (int i  = 0; i < line.length(); i++){
            ArrayList<Edges> edgesList = currentVert.getNextEdges();
            for(int x = 0; x < edgesList.size(); x++){
                if(edgesList.get(x).getID().equals(Character.toString(line.charAt(i)))){
                    currentVert = edgesList.get(x).getDestVert();
                    ArrayList<Edges> nextEdgesList = currentVert.getNextEdges();
                    System.out.println(line.charAt(i) + "," + nextEdgesList.get(0).getID());
                    break;
                }
            }
            currentVert = root;
        }
    }

}
