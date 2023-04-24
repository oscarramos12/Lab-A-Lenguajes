import java.util.ArrayList;
import java.util.Stack;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AFN {

    public static ArrayList<Vertex> doAFN(String input, Character concat){

        String postfix = InFixPostFix.toPostFix(input,concat);
        List<Character> operadores = Arrays.asList('|', '?', '+', '*', '^',concat);

        Stack<Vertex> inicio = new Stack<Vertex>();
        Stack<Vertex> fin = new Stack<Vertex>();
        ArrayList<Vertex> ends = new ArrayList<Vertex>();
        int vertexCount = 0;

        System.out.println(postfix);
            
        for(int i = 0; i < postfix.length(); i++){
            Character char1 = postfix.charAt(i);
            if(!operadores.contains(char1)){
                Vertex startVertex = new Vertex();
                Vertex endVertex = new Vertex();

                Edges link = new Edges();

                ArrayList<Edges> prevEdges1 = new ArrayList<Edges>();
                ArrayList<Edges> nextEdges1 = new ArrayList<Edges>();
                ArrayList<Edges> prevEdges2 = new ArrayList<Edges>();
                ArrayList<Edges> nextEdges2 = new ArrayList<Edges>();
                link.setAll(startVertex, endVertex, Character.toString(postfix.charAt(i)));
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
                topFin.setNextEdge(nextListTop);
                botFin.setNextEdge(nextListBot);

                inicio.push(newInicio);
                fin.push(newFin);
            }
            else if(char1.equals(concat)){
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

                Vertex newInicio = new Vertex();
                Vertex newFin = new Vertex();

                Edges newInicioToOld = createEpsilon(newInicio, oldInicio);
                Edges newInicioToNewFin = createEpsilon(newInicio, newFin);

                ArrayList<Edges> edgesNewInicio = new ArrayList<Edges>();
                edgesNewInicio.add(newInicioToOld);
                edgesNewInicio.add(newInicioToNewFin);

                newInicio.setAll(null, edgesNewInicio, vertexCount);
                vertexCount++;
                newFin.setID(vertexCount);
                vertexCount++;

                Edges backToOldInicio = createEpsilon(oldFin, oldInicio);
                Edges oldFinToNew = createEpsilon(oldFin, newFin);

                ArrayList<Edges> oldFinEdges = new ArrayList<Edges>();
                oldFinEdges = oldFin.getNextEdges();
                oldFinEdges.add(backToOldInicio);
                oldFinEdges.add(oldFinToNew);

                oldFin.setNextEdge(oldFinEdges);

                fin.push(newFin);
                inicio.push(newInicio);

            }
        }
        //inicio.peek().setID(-99);
        //fin.peek().setID(99);
        ends.add(inicio.peek());
        ends.add(fin.peek());
        return ends;
    }
    public static Edges createEpsilon(Vertex startVertex, Vertex endVertex){
        Edges epsilon = new Edges();
        epsilon.setAll(startVertex, endVertex, "ε");
        return epsilon;
    }

    public static String getExpression(ArrayList<String> input, int start, Character concat, int arrayEnd){
        String resultado = "";
        for(int i = start; i < input.size() - arrayEnd; i++){
            if(i == input.size() - 1){
                resultado += input.get(i);
            }
            else{
                resultado += input.get(i) + "|";
            }
        }
        resultado = arbolLex.newFormatTree(resultado, concat);
        resultado = arbolLex.NewcambioInterrogacion(resultado);
        resultado = arbolLex.NewcambioMas(resultado);
        resultado = arbolLex.newToPostFix(resultado, concat);
        return resultado;
    }

    public static ArrayList<Vertex> doAFNlex(ArrayList<String> input, Character concat, int start, int arrayEnd){

        List<Character> operadores = Arrays.asList('|', '?', '+', '*', '^',concat, ' ');

        Stack<Vertex> inicio = new Stack<Vertex>();
        Stack<Vertex> fin = new Stack<Vertex>();
        ArrayList<Vertex> ends = new ArrayList<Vertex>();
        int vertexCount = 0;
        Vertex mainRoot = new Vertex();
        mainRoot.setIsInit(true);
        mainRoot.setID(9999999);
        ArrayList<Edges> mainRootEdges = new ArrayList<Edges>();
        ends.add(mainRoot);

        HashMap<String, String> specialCase = new HashMap<String, String>();
        specialCase.put("plusSymbol", "+");
        specialCase.put("questionSymbol", "?");
        specialCase.put("lparenSymbol", "(");
        specialCase.put("rparenSymbol", ")");
        specialCase.put("orSymbol", "|");
        specialCase.put("multiSymbol", "*");
        specialCase.put("elevadoSymbol", "^");

        for (int array = start; array < input.size() - arrayEnd; array++){
            
            String postfix = input.get(array);

            postfix = arbolLex.newFormatTree(postfix, concat);
            postfix = arbolLex.NewcambioInterrogacion(postfix);
            postfix = arbolLex.NewcambioMas(postfix);
            postfix = arbolLex.newToPostFix(postfix, concat);

            for(int i = 0; i < postfix.length(); i++){
                Character char1 = postfix.charAt(i);
                String word = new String(Character.toString(postfix.charAt(i)));
                if(!operadores.contains(char1)){
                    for (int getFull = i+1; getFull < postfix.length(); getFull++){
                        if(!operadores.contains(postfix.charAt(getFull))){
                            word += postfix.charAt(getFull);
                        }
                        else{
                            i = i + word.length();
                            break;
                        }

                    }
                }

                if(word.length() > 1 || !operadores.contains(word.charAt(0))){

                    for (Map.Entry<String, String> entry : specialCase.entrySet()) {
                        if(word.equals(entry.getKey())){
                            word = entry.getValue();
                            break;
                        }
                    }

                    Vertex startVertex = new Vertex();
                    Vertex endVertex = new Vertex();
    
                    Edges link = new Edges();   
    
                    ArrayList<Edges> prevEdges1 = new ArrayList<Edges>();
                    ArrayList<Edges> nextEdges1 = new ArrayList<Edges>();
                    ArrayList<Edges> prevEdges2 = new ArrayList<Edges>();
                    ArrayList<Edges> nextEdges2 = new ArrayList<Edges>();
                    link.setAll(startVertex, endVertex, word);
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
                    topFin.setNextEdge(nextListTop);
                    botFin.setNextEdge(nextListBot);
    
                    inicio.push(newInicio);
                    fin.push(newFin);
                }
                else if(char1.equals(concat)){
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
    
                    Vertex newInicio = new Vertex();
                    Vertex newFin = new Vertex();
    
                    Edges newInicioToOld = createEpsilon(newInicio, oldInicio);
                    Edges newInicioToNewFin = createEpsilon(newInicio, newFin);
    
                    ArrayList<Edges> edgesNewInicio = new ArrayList<Edges>();
                    edgesNewInicio.add(newInicioToOld);
                    edgesNewInicio.add(newInicioToNewFin);
    
                    newInicio.setAll(null, edgesNewInicio, vertexCount);
                    vertexCount++;
                    newFin.setID(vertexCount);
                    vertexCount++;
    
                    Edges backToOldInicio = createEpsilon(oldFin, oldInicio);
                    Edges oldFinToNew = createEpsilon(oldFin, newFin);
    
                    ArrayList<Edges> oldFinEdges = new ArrayList<Edges>();
                    oldFinEdges = oldFin.getNextEdges();
                    oldFinEdges.add(backToOldInicio);
                    oldFinEdges.add(oldFinToNew);
    
                    oldFin.setNextEdge(oldFinEdges);
    
                    fin.push(newFin);
                    inicio.push(newInicio);
    
                }
            }

            Edges epToMain = createEpsilon(mainRoot, inicio.pop());
            mainRootEdges.add(epToMain);
            ends.add(fin.pop());
            inicio.clear();
            fin.clear();
            
        } 
        mainRoot.setNextEdge(mainRootEdges);
        return ends;
    }

}
