import java.awt.Dimension;
import javax.swing.JFrame;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;

public class graficarTree {

    public static void main(String[] args) {

        // Create a DelegateTree object
        DelegateTree<String, Integer> tree = new DelegateTree<>();

        // Add nodes to the tree
        tree.addVertex("deezRoot");
        tree.addChild(1, "deezRoot", "ddeez", EdgeType.DIRECTED);
        tree.addChild(2, "deezRoot", "nutz" , EdgeType.DIRECTED);

        tree.addVertex("NuRoot");
        tree.addChild(1,"NuRoot", "deezRoot");

        // Add edges to the tree
        /*tree.addEdge(1, "root", "child1", EdgeType.DIRECTED);
        tree.addEdge(2, "root", "child2", EdgeType.DIRECTED);*/

        // Create a TreeLayout object to layout the nodes in the tree
        TreeLayout<String, Integer> layout = new TreeLayout<>(tree);

        // Create a visualization server
        BasicVisualizationServer<String, Integer> server = new BasicVisualizationServer<>(layout);
        server.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());

        // Create a window and add the server to it
        JFrame frame = new JFrame("Tree Graph Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.getContentPane().add(server);
        frame.pack();
        frame.setVisible(true);
    }
}
