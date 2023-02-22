import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.zip.CRC32;


public class App {

    public static void main(String[] args) throws Exception {
        String input = "(a|b)*abb";
        String postfix = InFixPostFix.toPostFix(input);

        Stack<Vertex> inicio = new Stack<Vertex>();
        Stack<Vertex> fin = new Stack<Vertex>();

        List<Character> todo = Arrays.asList('+','*','?','|','^');

        int vertexCount = 0;

        System.out.println(postfix);
            
        for(int i = 0; i < postfix.length(); i++){
            Character char1 = postfix.charAt(i);
            if(Character.isLetter(char1)){
                Vertex startVertex = new Vertex();
                Vertex endVertex = new Vertex();

                Edges link = new Edges();

                ArrayList<Edges> prevEdges1 = new ArrayList<Edges>();
                ArrayList<Edges> nextEdges1 = new ArrayList<Edges>();
                ArrayList<Edges> prevEdges2 = new ArrayList<Edges>();
                ArrayList<Edges> nextEdges2 = new ArrayList<Edges>();
                link.setAll(startVertex, endVertex, postfix.charAt(i));
                nextEdges1.add(link);

                startVertex.setAll(prevEdges1, nextEdges1, vertexCount);

                vertexCount++;
                prevEdges2.add(link);

                endVertex.setAll(prevEdges2, nextEdges2, vertexCount);
                vertexCount++;

                inicio.push(startVertex);
                fin.push(endVertex);
            }
            else if(char1.equals('|')){
                Vertex topInicio = inicio.pop();
                Vertex botInicio = inicio.pop();

                Vertex topFin = fin.pop();
                Vertex botFin = fin.pop();

                Vertex newInicio = new Vertex();
                Vertex newFin = new Vertex();

                Edges topEpsilon1 = new Edges();
                Edges botEpsilon1 = new Edges();

                //asignar valores al nuevo vert de inicio
                topEpsilon1 = createEpsilon(newInicio, topInicio);
                botEpsilon1 = createEpsilon(newInicio, botInicio);
                ArrayList<Edges> prevListNewInicio1 = new ArrayList<Edges>();
                ArrayList<Edges> nextListNewInicio1 = new ArrayList<Edges>();
                nextListNewInicio1.add(topEpsilon1);
                nextListNewInicio1.add(botEpsilon1);
                newInicio.setAll(prevListNewInicio1, nextListNewInicio1, vertexCount);
                vertexCount++;

                //asignar valores de nuevo vert de inicio a vert iniciales pasados
                ArrayList<Edges> prevListTop = new ArrayList<Edges>();
                ArrayList<Edges> prevListBot = new ArrayList<Edges>();
                prevListTop.add(topEpsilon1);
                prevListBot.add(botEpsilon1);
                topInicio.setPrevEdge(prevListTop);
                botInicio.setPrevEdge(prevListBot);

                Edges topEpsilon2 = new Edges();
                Edges botEpsilon2 = new Edges();

                //asignar valores al nuevo vert de fin
                topEpsilon2 = createEpsilon(topFin,newFin);
                botEpsilon2 = createEpsilon(botFin, newFin);
                ArrayList<Edges> prevListNewInicio2 = new ArrayList<Edges>();
                ArrayList<Edges> nextListNewInicio2 = new ArrayList<Edges>();
                prevListNewInicio2.add(topEpsilon2);
                prevListNewInicio2.add(botEpsilon2);
                newFin.setAll(prevListNewInicio2, nextListNewInicio2, vertexCount);
                vertexCount++;

                ArrayList<Edges> nextListTop = new ArrayList<Edges>();
                ArrayList<Edges> nextListBot = new ArrayList<Edges>();
                nextListTop.add(topEpsilon2);
                nextListBot.add(botEpsilon2);
                topFin.setPrevEdge(nextListTop);
                botFin.setPrevEdge(nextListBot);

                inicio.push(newInicio);
                fin.push(newFin);
            }
            else if(char1.equals('.')){
                Vertex newFin = fin.pop();
                Vertex connect = fin.pop();
                Vertex getEdge = inicio.pop();

                ArrayList<Edges> edgeConnect = getEdge.getNextEdges();
                for(int x = 0; x < edgeConnect.size(); x++){
                    Edges changeInit = edgeConnect.get(x);
                    changeInit.setInitVert(connect);
                }
                connect.setNextEdge(edgeConnect);
                fin.push(newFin);
            }
            else if(char1.equals('*')){
                Vertex oldInicio = inicio.pop();
                Vertex oldFin = fin.pop();
                Edges newEdgeBackFin = new Edges();
                newEdgeBackFin = createEpsilon(oldFin, oldInicio);

                ArrayList<Edges> newEdgesFin = new ArrayList<Edges>();
                newEdgesFin = oldFin.getNextEdges();
                newEdgesFin.add(newEdgeBackFin);

                Vertex newInicio = new Vertex();
                Vertex newFin = new Vertex();



            }
        }
    }

    public static Edges createEpsilon(Vertex startVertex, Vertex endVertex){
        Edges epsilon = new Edges();
        epsilon.setAll(startVertex, endVertex, 'ε');
        return epsilon;
    }

}