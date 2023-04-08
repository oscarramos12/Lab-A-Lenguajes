import java.awt.BorderLayout;
import javax.swing.JFrame;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.util.ArrayList;
/*import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import com.mxgraph.util.mxCellRenderer;
import java.awt.Color;*/


public class pruebas {

    public static void graficardd(treeNode root) {
        JFrame frame = new JFrame();
        frame.setTitle("Arbol");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        // Add nodes to the graph

        ArrayList<treeNode> esperando = new ArrayList<treeNode>();
        ArrayList<Object> esperandoObj = new ArrayList<Object>();
        treeNode procesando;
        int level = 2;
        Object parentNode = new Object();
        esperando.add(root);
        esperandoObj.add(graph.insertVertex(parent, null, root.getValue(), 20 * 80, 20, 60, 30));
        while(!esperando.isEmpty()){
            procesando = esperando.get(0);
            parentNode = esperandoObj.get(0);
            esperando.remove(0);
            esperandoObj.remove(0);
            for(int i = 0; i < procesando.getHojas().size(); i++){
                Object child = graph.insertVertex(parent, null, procesando.getHojas().get(i).getValue(), 20 + ((i)*5) * 80, 40*level+(i+2), 60, 30);
                graph.insertEdge(parent, null, "", parentNode, child);
                esperando.add(procesando.getHojas().get(i));
                esperandoObj.add(child);
            }
            level++;
        }


        /*Object[] vertex = new Object[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            vertex[i] = graph.insertVertex(parent, null, nodes.get(i), 20 + i * 80, 20*i, 60, 30);
        }

        // Add edges to the graph
        graph.insertEdge(parent, null, "", vertex[0], vertex[1]);
        graph.insertEdge(parent, null, "", vertex[0], vertex[2]);
        graph.insertEdge(parent, null, "", vertex[1], vertex[3]);
        graph.insertEdge(parent, null, "", vertex[2], vertex[3]);*/

        //BufferedImage image = mxCellRenderer.createBufferedImage(graph, null, 1, Color.WHITE, true, null);

        // Write the image to a file
        /*try {
            ImageIO.write(image, "png", new File("jgraph.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        frame.getContentPane().add(graphComponent, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
