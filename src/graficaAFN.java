import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Comparator;

public class graficaAFN {


    public static void graficar(Vertex nodoInicial, Vertex nodoFinal) {

        Graph<Integer, String> graph = new DirectedSparseGraph<Integer, String>();

        graph.addVertex(nodoInicial.getID());
        ArrayList<Vertex> esperando = new ArrayList<Vertex>();
        ArrayList<String> hasEdge = new ArrayList<String>();
        ArrayList<Integer> hasVertex = new ArrayList<Integer>();
        int epsilonCounter = 0;

        ArrayList<Edges> getNextVert = nodoInicial.getNextEdges();
        for (int i = 0; i < getNextVert.size(); i++){
            graph.addVertex(getNextVert.get(i).getDestVert().getID());
            graph.addEdge(Character.toString(getNextVert.get(i).getID()) + "-" + epsilonCounter, nodoInicial.getID(), getNextVert.get(i).getDestVert().getID());
            esperando.add(getNextVert.get(i).getDestVert());
            epsilonCounter++;
        }
        
        while(!esperando.isEmpty()){
            Vertex newVert = esperando.remove(0);
            ArrayList<Edges> getNextVerts = newVert.getNextEdges();
            for (int i = 0; i < getNextVerts.size(); i++){
                if(!hasVertex.contains(getNextVerts.get(i).getDestVert().getID())){
                    graph.addVertex(getNextVerts.get(i).getDestVert().getID());
                    hasVertex.add(getNextVerts.get(i).getDestVert().getID());
                }
                if(!hasEdge.contains(Integer.toString(getNextVerts.get(i).getInitVert().getID()) + "-" + Integer.toString(getNextVerts.get(i).getDestVert().getID()))){
                    graph.addEdge(Character.toString(getNextVerts.get(i).getID()) + "-" + epsilonCounter, newVert.getID(), getNextVerts.get(i).getDestVert().getID());
                    hasEdge.add(Integer.toString(getNextVerts.get(i).getInitVert().getID()) + "-" + Integer.toString(getNextVerts.get(i).getDestVert().getID()));
                }
                if(!getNextVerts.get(i).getDestVert().getVisited()){
                    esperando.add(getNextVerts.get(i).getDestVert());
                }
                epsilonCounter++;
                newVert.setVisited(true);
            }
        }
        
        Layout<Integer, String> layout = new CircleLayout<Integer, String>(graph);
        layout.setSize(new Dimension(750, 750));
        
        VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(800, 800));
        
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
                
        JFrame frame = new JFrame("One Directional Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}

