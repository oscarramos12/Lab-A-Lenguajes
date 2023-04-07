import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;



public class graficarTree {

    public static void graficar(treeNode root) {

        // Create a DelegateTree object
        DelegateTree<String, Integer> tree = new DelegateTree<>();
        ArrayList<treeNode> esperando = new ArrayList<treeNode>();
        ArrayList<String> esperandoIDs = new ArrayList<String>();
        String procesandoID;
        int ID = 0;
        treeNode procesando;
        esperando.add(root);
        esperandoIDs.add(root.getValue() + "-" + Integer.toString(ID));
        tree.setRoot(root.getValue() + "-" + Integer.toString(ID));
        System.out.println("ROOT: " + root.getValue() + "-" + Integer.toString(ID));
        ID++;
        while(!esperando.isEmpty()){
            procesando = esperando.get(0);
            esperando.remove(0);
            procesandoID = esperandoIDs.get(0);
            esperandoIDs.remove(0);
            for(int i = 0; i < procesando.getHojas().size(); i++){
                tree.addChild(ID, procesandoID, procesando.getHojas().get(i).getValue() + "-"  + Integer.toString(ID));
                esperando.add(procesando.getHojas().get(i));
                esperandoIDs.add(procesando.getHojas().get(i).getValue() + "-"  + Integer.toString(ID));
                ID ++;
            }
        }


        TreeLayout<String, Integer> layout = new TreeLayout<>(tree);
        BasicVisualizationServer<String, Integer> server = new BasicVisualizationServer<>(layout);
        server.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());

        JFrame frame = new JFrame("Tree Graph Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1920, 1080));
        frame.getContentPane().add(server);
        frame.pack();
        frame.setVisible(true);
    }
}
