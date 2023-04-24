import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import java.util.ArrayList;

public class graficaAFN {


    public static void graficar(Vertex nodoInicial, String graphName) {

        Graph<Integer, String> graph = new DirectedSparseGraph<Integer, String>();

        graph.addVertex(nodoInicial.getID());
        ArrayList<Vertex> esperando = new ArrayList<Vertex>();
        ArrayList<String> hasEdge = new ArrayList<String>();
        ArrayList<Integer> hasVertex = new ArrayList<Integer>();
        int epsilonCounter = 0;

        /*ArrayList<Edges> getNextVert = nodoInicial.getNextEdges();
        for (int i = 0; i < getNextVert.size(); i++){
            graph.addVertex(getNextVert.get(i).getDestVert().getID());
            graph.addEdge(Character.toString(getNextVert.get(i).getID()) + "-" + epsilonCounter, nodoInicial.getID(), getNextVert.get(i).getDestVert().getID());
            esperando.add(getNextVert.get(i).getDestVert());
            epsilonCounter++;
        }*/

        esperando.add(nodoInicial);
        
        while(!esperando.isEmpty()){
            Vertex newVert = esperando.remove(0);
            ArrayList<Edges> getNextVerts = newVert.getNextEdges();
            for (int i = 0; i < getNextVerts.size(); i++){
                if(!hasVertex.contains(getNextVerts.get(i).getDestVert().getID())){
                    graph.addVertex(getNextVerts.get(i).getDestVert().getID());
                    hasVertex.add(getNextVerts.get(i).getDestVert().getID());
                }
                if(!hasEdge.contains(Integer.toString(getNextVerts.get(i).getInitVert().getID()) + "-" + Integer.toString(getNextVerts.get(i).getDestVert().getID()))){
                    graph.addEdge(getNextVerts.get(i).getID() + "-" + epsilonCounter, newVert.getID(), getNextVerts.get(i).getDestVert().getID());
                    hasEdge.add(Integer.toString(getNextVerts.get(i).getInitVert().getID()) + "-" + Integer.toString(getNextVerts.get(i).getDestVert().getID()));
                }
                if(!getNextVerts.get(i).getDestVert().getVisitedGraph()){
                    esperando.add(getNextVerts.get(i).getDestVert());
                }
                epsilonCounter++;
                newVert.setVisitedGraph(true);
            }
        }

        
        Layout<Integer, String> layout = new CircleLayout<Integer, String>(graph);
        layout.setSize(new Dimension(2460, 1340));
        
        VisualizationViewer<Integer, String> vv = new VisualizationViewer<Integer, String>(layout);
        vv.setPreferredSize(new Dimension(2560, 1440));
        
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<Integer>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
                
        JFrame frame = new JFrame(graphName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}

