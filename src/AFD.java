import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


//hacer atributo en vertex de inicio y fin y tener una lista con ID de vertices de inicio y fin

public class AFD {
    public static Vertex doAFD(Vertex inicio, Character concat, String input, Vertex fin){
        List<Character> operadores = Arrays.asList('|', '?', '+', '*', '^',concat,'(',')','ε');
        ArrayList<Character> simbols = new ArrayList<Character>();
        Character check;
        for (int i = 0; i < input.length(); i++){
            check = input.charAt(i);
            if(!operadores.contains(check) && !simbols.contains(check) && (!check.equals(' '))){
                simbols.add(check);
            }
        }
        simbols.add('ε');
        System.out.println(simbols.toString());
        ArrayList<ArrayList<String>> matriz = new ArrayList<ArrayList<String>>();

        ArrayList<Vertex> esperando = new ArrayList<Vertex>();
        esperando.add(inicio);
        while(!esperando.isEmpty()){
            Vertex vertexRow = esperando.remove(0);
            if(!vertexRow.visitedAFD){
                vertexRow.setVisitedAFD(true);
                ArrayList<String> row = new ArrayList<String>();
                row.add(Integer.toString(vertexRow.getID()));
                for (int i = 0; i < simbols.size(); i++){
                    row.add("");
                }
                ArrayList<Edges> edgeList = vertexRow.getNextEdges();
                for (int i = 0; i < edgeList.size(); i++){
                    Edges vertEdge = edgeList.get(i);
                    if(!vertEdge.getDestVert().getVisitedAFD()){
                        esperando.add(vertEdge.getDestVert());
                    }
                }
                for (int i = 1; i < row.size(); i++){
                    for (int x = 0; x < edgeList.size(); x++){
                        Edges vertEdge = edgeList.get(x);
                        if(simbols.get(i-1).equals(vertEdge.getID()) && simbols.get(i-1).equals('ε')){
                            String connect = row.get(i);
                            if(connect.length() == 0 && !(connect.contains(Integer.toString(vertexRow.getID())))){
                                connect += vertexRow.getID() + "|";
                            }
                            if(!connect.contains( "|" + vertEdge.getDestVert().getID() + "|")){
                                connect += vertEdge.getDestVert().getID() + "|";
                            }
                            ArrayList<Edges> esperandoTrans = new ArrayList<Edges>(vertEdge.getDestVert().getNextEdges());
                            while(!esperandoTrans.isEmpty()){
                                if(esperandoTrans.get(0).getID() == 'ε'){
                                    Vertex nextTrans = esperandoTrans.get(0).getDestVert();
                                    if((!connect.contains("|" + nextTrans.getID() + "|")) && (!connect.contains("|" + nextTrans.getID()))){
                                        connect += nextTrans.getID() + "|";
                                    }
                                    ArrayList<Edges> nextTransEdges = new ArrayList<Edges>(nextTrans.getNextEdges());
                                    for(int j  = 0; j < nextTransEdges.size(); j++){
                                        if(nextTransEdges.get(j).getID() == 'ε' && (!connect.contains("|" + Integer.toString(nextTransEdges.get(j).getDestVert().getID()) + "|"))){
                                            esperandoTrans.add(nextTransEdges.get(j));
                                        }
                                    }
                                }
                            esperandoTrans.remove(0);
                        }
                        row.set(i, connect);
                    }
                        else if(simbols.get(i-1).equals(vertEdge.getID())){
                            String connect = row.get(i);
                            if(!connect.contains( "|" + vertEdge.getDestVert().getID() + "|")){
                                connect += vertEdge.getDestVert().getID() + "|";
                            }
                            row.set(i, connect);
                        }
                    }
                }
                if(row.get(row.size()-1).equals("")){
                    String self = row.get(row.size()-1);
                    self = Integer.toString(vertexRow.getID()) + "|";
                    row.set(row.size()-1, self);
                }
                System.out.println(row.toString());
                matriz.add(row);
            }
        }
     
        
        int finID = fin.getID();

        ArrayList<String> first = matriz.get(0);
        ArrayList<ArrayList<String>> matriz2 = new ArrayList<ArrayList<String>>();
        ArrayList<String> esperando2 = new ArrayList<String>();
        System.out.println("matriz2");

        esperando2.add(first.get(first.size()-1));
        while(!esperando2.isEmpty()){
            String conjunto = esperando2.get(0);
            esperando2.remove(0);
            String[] values = conjunto.split("\\|");
            ArrayList<String> newTableRow = new ArrayList<String>();
            newTableRow.add(conjunto);
            for (int tableSize = 1; tableSize < simbols.size(); tableSize++){
                newTableRow.add("");
            }
            for (int rowPos = 0; rowPos < values.length ;rowPos++){
                for(int matrizSearch = 0; matrizSearch < matriz.size(); matrizSearch++){
                    ArrayList<String> searchRow = matriz.get(matrizSearch);
                    if(searchRow.get(0).equals(values[rowPos])){
                        for(int searchRowCont = 1; searchRowCont < searchRow.size()-1; searchRowCont++){
                            if(!searchRow.get(searchRowCont).equals("")){
                                String[] refList = searchRow.get(searchRowCont).split("\\|");
                                for (int refSearch = 0; refSearch < refList.length; refSearch++){
                                    for (int matrixSearch = 1; matrixSearch < matriz.size(); matrixSearch++){
                                        ArrayList<String> matrixRow = matriz.get(matrixSearch);
                                        if(matrixRow.get(0).equals(refList[refSearch])){
                                            if(newTableRow.get(searchRowCont).equals("")){
                                                newTableRow.set(searchRowCont, matrixRow.get(matrixRow.size()-1));
                                            }
                                            else{
                                                String[] newTRowDups = newTableRow.get(searchRowCont).split("\\|");
                                                String[] newInsertDups =  matrixRow.get(matrixRow.size()-1).split("\\|");
                                                String add = newTableRow.get(searchRowCont);
                                                List<String> TRowDups = Arrays.asList(newTRowDups);
                                                for(int dups2 = 0; dups2 < newInsertDups.length; dups2 ++){
                                                    if(!TRowDups.contains(newInsertDups[dups2])){
                                                        add += newInsertDups[dups2] + "|";
                                                        TRowDups = Arrays.asList(add.split("\\|"));
                                                    }
                                                }
                                                newTableRow.set(searchRowCont, add);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                       break;
                    }
                }
            }
            System.out.println(newTableRow);
            if(matriz2.size() == 0){
                for(int i = 1; i < newTableRow.size(); i++){
                    if(!newTableRow.get(i).equals("")){
                        esperando2.add(newTableRow.get(i));
                    }
                }
                
                matriz2.add(newTableRow);
            }
            else{
                matriz2.add(newTableRow);
                ArrayList<ArrayList<Integer>> allOld = new ArrayList<ArrayList<Integer>>();
                for(int i = 0; i < matriz2.size(); i++){
                    ArrayList<Integer> checkOld = new ArrayList<Integer>();
                    String[] convert = matriz2.get(i).get(0).split("\\|");
                    for (int y = 0; y < convert.length; y ++){
                        checkOld.add(Integer.parseInt(convert[y]));
                    }
                    Collections.sort(checkOld);
                    allOld.add(checkOld);                   

                }
                //System.out.println("OLD:" + allOld);
                for (int j = 1; j < newTableRow.size(); j++){
                    ArrayList<Integer> checkNew = new ArrayList<Integer>();
                    String[] convertNew = newTableRow.get(j).split("\\|");
                    for(int k = 0; k < convertNew.length; k ++){
                        if(!convertNew[k].equals("")){
                            checkNew.add(Integer.parseInt(convertNew[k]));
                        }
                    }
                    Collections.sort(checkNew);
                    if(!allOld.contains(checkNew) && !checkNew.isEmpty() && !esperando2.contains(newTableRow.get(j))){
                        esperando2.add(newTableRow.get(j));
                    }
                }
                //System.out.println("OLD: " + allOld); 
            }
        }
        //System.out.println("MAT: " + matriz2);
        ArrayList<ArrayList<Integer>> matriz2Int = convertToInt(matriz2, finID);
        System.out.println(matriz2Int);
        Vertex inicioAFD = tableToNodes(matriz2Int, simbols);
        System.out.println("matriz2Int");
        //minimizar(matriz2Int, simbols);
        Vertex minimizado = minToNodes(minimizar(matriz2Int, simbols), simbols);
        graficaAFN.graficar(minimizado, "AFD Minimizado");
        return inicioAFD;
    }
    private static ArrayList<ArrayList<Integer>> convertToInt(ArrayList<ArrayList<String>> matriz, int finID){
        ArrayList<ArrayList<Integer>> matrizFinal = new ArrayList<ArrayList<Integer>>();
        HashMap<ArrayList<Integer>, Integer> referencias = new HashMap<ArrayList<Integer>, Integer>();
        ArrayList<Integer> aceptacion = new ArrayList<Integer>();

        int ID = 0;
                for (int x = 0; x < matriz.size(); x++){
                    ArrayList<String> row = matriz.get(x);
                    ArrayList<Integer> intRow = new ArrayList<Integer>();
                    for (int n = 0; n < row.size(); n++){
                        ArrayList<Integer> conjuntoInt = new ArrayList<Integer>();
                        String[] conjunto = row.get(n).split("\\|");
                        for(int d = 0; d< conjunto.length; d++){
                            if(!conjunto[d].equals("")){
                                conjuntoInt.add(Integer.parseInt(conjunto[d]));
                            }
                        }
                        Collections.sort(conjuntoInt);
                        if(!referencias.containsKey(conjuntoInt) && (conjuntoInt.size() > 0)){
                            if(conjuntoInt.contains(finID)){
                                aceptacion.add(ID);
                            }
                            referencias.put(conjuntoInt, ID);
                            intRow.add(ID);
                            ID++;
                        }
                        else{
                            intRow.add(referencias.get(conjuntoInt));
                        }
                        
                    }
                    matrizFinal.add(intRow);
                }
                matrizFinal.add(aceptacion);
                return matrizFinal;
    }
    private static Vertex tableToNodes(ArrayList<ArrayList<Integer>> matrizFinal, ArrayList<Character> simbols){
        HashMap<Integer,Vertex> vertexMap = new HashMap<Integer,Vertex>();
        Vertex root = new Vertex();
        for (int i = 0; i < matrizFinal.size() - 1; i++){
            ArrayList<Integer> row = matrizFinal.get(i);
            Vertex vertInitRow = new Vertex();
            if(vertexMap.containsKey(row.get(0))){
                vertInitRow = vertexMap.get(row.get(0));
            }
            vertInitRow.setID(row.get(0));
            if(i == 0){
                root = vertInitRow;
            }
            ArrayList<Edges> edgeList = new ArrayList<Edges>();
            for(int j = 1; j < row.size(); j++){             
                Edges connect = new Edges();
                connect.setID(simbols.get(j-1));
                connect.setInitVert(vertInitRow);
                if(!vertexMap.containsKey(row.get(j)) && row.get(j) != null && row.get(j) != -1){
                    Vertex destVert = new Vertex();
                    destVert.setID(row.get(j));
                    connect.setDestVert(destVert);
                    edgeList.add(connect);
                    vertexMap.put(row.get(j), destVert);
                }
                else if(vertexMap.containsKey(row.get(j)) && row.get(j) != null && row.get(j) != -1){
                    connect.setDestVert(vertexMap.get(row.get(j)));
                    edgeList.add(connect);
                }
                if(matrizFinal.get(matrizFinal.size()-1).contains(row.get(j))){
                    vertInitRow.setIsEnd(true);
                }
                vertInitRow.setNextEdge(edgeList);
            }
        }
        return root;

    }

    private static Vertex minToNodes(ArrayList<ArrayList<Integer>> matrizFinal, ArrayList<Character> simbols){
        HashMap<Integer,Vertex> vertexMap = new HashMap<Integer,Vertex>();
        Vertex root = new Vertex();
        for (int i = 0; i < matrizFinal.size()-1; i++){
            ArrayList<Integer> row = matrizFinal.get(i);
            Vertex vertInitRow = new Vertex();
            if(vertexMap.containsKey(row.get(0))){
                vertInitRow = vertexMap.get(row.get(0));
            }
            vertInitRow.setID(row.get(0));
            if(i == 0){
                root = vertInitRow;
            }
            ArrayList<Edges> edgeList = new ArrayList<Edges>();
            for(int j = 1; j < row.size(); j++){             
                Edges connect = new Edges();
                connect.setID(simbols.get(j-1));
                connect.setInitVert(vertInitRow);
                if(!vertexMap.containsKey(row.get(j))  && row.get(j) != -1){
                    Vertex destVert = new Vertex();
                    destVert.setID(row.get(j));
                    connect.setDestVert(destVert);
                    edgeList.add(connect);
                    vertexMap.put(row.get(j), destVert);
                }
                else if(vertexMap.containsKey(row.get(j)) && row.get(j) != -1){
                    connect.setDestVert(vertexMap.get(row.get(j)));
                    edgeList.add(connect);
                }
                if(matrizFinal.get(matrizFinal.size()-1).contains(row.get(j))){
                    vertInitRow.setIsEnd(true);
                }
                vertInitRow.setNextEdge(edgeList);
            }
        }
        return root;

    }

    private static ArrayList<ArrayList<Integer>> minimizar (ArrayList<ArrayList<Integer>> matriz, ArrayList<Character> simbols){
        ArrayList<Integer> aceptacion = matriz.get(matriz.size()-1);
        ArrayList<ArrayList<Integer>> pi = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> returnMatrix = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> piRow = new ArrayList<Integer>();
        for (int i = 0; i < matriz.size()-1; i++){
            ArrayList<Integer> row = matriz.get(i);
            if(!aceptacion.contains(row.get(0))){
                piRow.add(row.get(0));
            }
        }
        pi.add(piRow);
        ArrayList<Integer> globalAcept = new ArrayList<Integer>();
        for (int i = 0; i < aceptacion.size(); i++){
            ArrayList<Integer> acpetIndiv = new ArrayList<Integer>();
            acpetIndiv.add(aceptacion.get(i));
            pi.add(acpetIndiv);
            globalAcept.add(aceptacion.get(i));
        }
        boolean minimize = true;
        System.out.println("FISRT PI: " + pi);
        while(minimize){
            ArrayList<ArrayList<Integer>> minMatrix = new ArrayList<ArrayList<Integer>>();
            for(int i = 0; i < matriz.size() - 1; i++){
                ArrayList<Integer> minMatRow = new ArrayList<Integer>();
                ArrayList<Integer> matrizOGRow = matriz.get(i);
                for(int j = 1; j < simbols.size(); j++){
                    if(matrizOGRow.get(j) != null){
                        for (int k = 0; k < pi.size(); k++){
                            if(pi.get(k).contains(matrizOGRow.get(j))){
                                minMatRow.add(k);
                            }
                        }
                    }
                    else{
                        minMatRow.add(-1);
                    }   
                }
                System.out.println(minMatRow);
                minMatrix.add(minMatRow);
            }
            ArrayList<ArrayList<Integer>> newPi = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> newConjunto = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < minMatrix.size(); i++){
                ArrayList<Integer> minMatRow = minMatrix.get(i);
                ArrayList<Integer> piSub = new ArrayList<Integer>();
                /*System.out.println(minMatrix);
                System.out.println(minMatRow);*/
                if(!newConjunto.contains(minMatRow) && !globalAcept.contains(i)){   
                    piSub.add(i);
                    newPi.add(piSub);
                    newConjunto.add(minMatRow);
                }
                else if(globalAcept.contains(i)){
                    piSub.add(i);
                    newPi.add(piSub);
                }
                else{
                    for (int j = 0; j < newConjunto.size(); j++){
                        if(minMatRow.equals(newConjunto.get(j))){
                            newPi.get(j).add(i);
                        }
                    }
                }

            }
            System.out.println("Separacion Conjunto PI");
            //System.out.println(pi);
            //System.out.println(newPi);
            if(newPi.equals(pi)){
                minimize = false;
                System.out.println("PI DONE");
                System.out.println(newPi);
                System.out.println("Min Matriz");
                returnMatrix = minMatrix;
                for (int i = 0; i < newPi.size(); i++){
                    if(newPi.get(i).size() > 1){
                        for (int j = 1; j < newPi.get(i).size(); j++){
                            int rem = newPi.get(i).get(j);
                            if(returnMatrix.size() < rem){
                                returnMatrix.remove(rem);
                            }
                            for(int x = 0; x < aceptacion.size(); x++){
                                aceptacion.set(x, aceptacion.get(x)-1);
                            }
                        }
                    }
                }
                for (int i = 0; i < returnMatrix.size(); i++){
                    returnMatrix.get(i).add(0,i);
                }

            }
            pi.clear();
            pi = newPi;
        }
        //System.out.println(matriz);
        //System.out.println(piRow);
        System.out.println(returnMatrix);
        returnMatrix.add(aceptacion);
        return returnMatrix;
    }
    
}
