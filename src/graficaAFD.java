import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.util.HashMap;



public class graficaAFD {


    public static void graficar(Vertex nodoInicial) {

        mxGraph graph = new mxGraph();
    
        Object parent = graph.getDefaultParent();
    
        graph.getModel().beginUpdate();
    
        try {

        ArrayList<Vertex> esperando = new ArrayList<Vertex>();
        HashMap<Integer, Object> onGraph = new HashMap<Integer, Object>();
        esperando.add(nodoInicial);
        Object root = graph.insertVertex(parent, null, nodoInicial.getID(), 20, 20, 80, 30);
        onGraph.put(nodoInicial.getID(), root);

        int deep = 60;

        while(!esperando.isEmpty()){
            Vertex newVert = new Vertex();

            newVert = esperando.remove(0);
            ArrayList<Edges> getNextVerts = newVert.getNextEdges();
            Object OGvertex = new Object();
            if(!onGraph.containsKey(newVert.getID())){
                OGvertex = graph.insertVertex(parent, null, newVert.getID(), 20, deep, 80, 30);
            }
            else{
                OGvertex = onGraph.get(newVert.getID());
            }
            int side = 100;
            for (int i = 0; i < getNextVerts.size(); i++){
                if(!onGraph.containsKey(getNextVerts.get(i).getDestVert().getID())){
                    Object destVertex = graph.insertVertex(parent, null, getNextVerts.get(i).getDestVert().getID(), side, deep, 80, 30);
                    graph.insertEdge(parent, null, getNextVerts.get(i).getID() , OGvertex, destVertex);
                    onGraph.put(getNextVerts.get(i).getDestVert().getID(), destVertex);
                    esperando.add(getNextVerts.get(i).getDestVert());
                }
                else if(onGraph.containsKey(getNextVerts.get(i).getDestVert().getID())){
                    Object destVert = onGraph.get(getNextVerts.get(i).getDestVert().getID());
                    graph.insertEdge(parent, null,getNextVerts.get(i).getID(), OGvertex, destVert);

                }

                newVert.setVisitedGraph(true);
                side += 100;
            }
            deep += 60;
        }
        } finally {
            graph.getModel().endUpdate();
        }
    
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        mxHierarchicalLayout layout = new mxHierarchicalLayout(graph);
        layout.execute(graph.getDefaultParent());
    
        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

