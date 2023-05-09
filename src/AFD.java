import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AFD {
    public static Vertex doAFD(Vertex inicio, Character concat, String input, ArrayList<Vertex> fin){
        List<Character> operadores = Arrays.asList('|', '?', '+', '*', '^',concat,'(',')','ε', ' ');
        ArrayList<String> simbols = new ArrayList<String>();

        HashMap<String, String> specialCase = new HashMap<String, String>();
        specialCase.put("plusSymbol", "+");
        specialCase.put("questionSymbol", "?");
        specialCase.put("lparenSymbol", "(");
        specialCase.put("rparenSymbol", ")");
        specialCase.put("orSymbol", "|");
        specialCase.put("multiSymbol", "*");
        specialCase.put("elevadoSymbol", "^");

        for (int i = 0; i < input.length(); i++){

            Character check = input.charAt(i);
                String word = new String(Character.toString(input.charAt(i)));
                if(!operadores.contains(check)){
                    word = "";
                    for (int getFull = i; getFull < input.length(); getFull++){
                        if(!operadores.contains(input.charAt(getFull))){
                            word += input.charAt(getFull);
                        }
                        else if(!simbols.contains(word) && !word.equals("")){
                            //System.out.println(word);
                            if(specialCase.containsKey(word)){
                                simbols.add(specialCase.get(word));
                                i = i + word.length();
                                word = "";
                            }
                            else{
                                simbols.add(word);
                                i = i + word.length();
                                word = "";
                            }

                            
                        }
                        else{
                            break;
                        }

                    }
                }
        }
        simbols.add("ε");
        System.out.println(simbols);
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
                        if(simbols.get(i-1).equals(vertEdge.getID()) && simbols.get(i-1).equals("ε")){
                            String connect = row.get(i);
                            if(connect.length() == 0 && !(connect.contains(Integer.toString(vertexRow.getID())))){
                                connect += vertexRow.getID() + "|";
                            }
                            if(!connect.contains( "|" + vertEdge.getDestVert().getID() + "|")){
                                connect += vertEdge.getDestVert().getID() + "|";
                            }
                            ArrayList<Edges> esperandoTrans = new ArrayList<Edges>(vertEdge.getDestVert().getNextEdges());
                            while(!esperandoTrans.isEmpty()){
                                if(esperandoTrans.get(0).getID() == "ε"){
                                    Vertex nextTrans = esperandoTrans.get(0).getDestVert();
                                    String[] inList = connect.split("\\|");
                                    List<String> check = Arrays.asList(inList);
                                    //(!connect.contains("|" + nextTrans.getID() + "|")) && (!connect.contains("|" + nextTrans.getID()))
                                    if(!check.contains(Integer.toString(nextTrans.getID()))){
                                        connect += nextTrans.getID() + "|";
                                    }
                                    ArrayList<Edges> nextTransEdges = new ArrayList<Edges>(nextTrans.getNextEdges());
                                    for(int j  = 0; j < nextTransEdges.size(); j++){
                                        if(nextTransEdges.get(j).getID() == "ε" && (!connect.contains("|" + Integer.toString(nextTransEdges.get(j).getDestVert().getID()) + "|"))){
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
                //System.out.println(row.toString());
                matriz.add(row);
            }
        }
     
        //ENDS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        ArrayList<Integer> allFinIDs = new ArrayList<Integer>();
        for (int getIDs = 1; getIDs < fin.size(); getIDs++){
            allFinIDs.add(fin.get(getIDs).getID());
        }
        System.out.println(allFinIDs);

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
            //System.out.println(newTableRow);
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
        ArrayList<ArrayList<Integer>> matriz2Int = convertToInt(matriz2, allFinIDs);
        //System.out.println(matriz2Int);
       // Vertex inicioAFD = tableToNodes(matriz2Int, simbols);
        //System.out.println("matriz2Int");
        //minimizar(matriz2Int, simbols);
        Vertex minimizado = minToNodes(minimizar(matriz2Int, simbols), simbols);
        //graficaAFD.graficar(minimizado);
        //runExpression.readFile("C:\\Users\\Oscar\\Desktop\\Lab A\\slr-1.2.yal.run", minimizado);
        return minimizado;
    }
    private static ArrayList<ArrayList<Integer>> convertToInt(ArrayList<ArrayList<String>> matriz,  ArrayList<Integer> allFinIDs){
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

                            for (int compare = 0; compare < conjuntoInt.size(); compare++){
                                if(allFinIDs.contains(conjuntoInt.get(compare))){
                                    aceptacion.add(ID);
                                }
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
    private static Vertex tableToNodes(ArrayList<ArrayList<Integer>> matrizFinal, ArrayList<String> simbols){
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

    private static Vertex minToNodes(HashMap<Integer, List<Integer>> matrizFinal, ArrayList<String> simbols){

        HashMap<String, String> specialCase = new HashMap<String, String>();
        specialCase.put("plusSymbolSpecial", "+");
        specialCase.put("questionSymbolSpecial", "?");
        specialCase.put("lparenSymbolSpecial", "(");
        specialCase.put("rparenSymbolSpecial", ")");
        specialCase.put("orSymbolSpecial", "|");
        specialCase.put("multiSymbolSpecial", "*");
        specialCase.put("elevadoSymbolSpecial", "^");
        specialCase.put("empty", " ");

        List<Integer> aceptacion = matrizFinal.get(-1);
        matrizFinal.remove(-1);

        System.out.println(aceptacion);

        HashMap<Integer,Vertex> vertexMap = new HashMap<Integer,Vertex>();
        Vertex root = new Vertex();
        for (Map.Entry<Integer, List<Integer>> entry : matrizFinal.entrySet()) {
            Integer key = entry.getKey();
            List<Integer> value = entry.getValue();
            if(key == 0){
                root.setID(0);
                vertexMap.put(key, root);
            }
            if(vertexMap.containsKey(key)){
                Vertex currentVert = vertexMap.get(key);
                currentVert.setID(key);
                ArrayList<Edges> allEdges = new ArrayList<Edges>();
                for (int goRow = 0 ; goRow < value.size(); goRow++){
                    if(vertexMap.containsKey(value.get(goRow))){
                        Edges connect = new Edges();
                        if(specialCase.containsKey(simbols.get(goRow))){
                            connect.setID(specialCase.get(simbols.get(goRow)));
                        }
                        else{
                            connect.setID(simbols.get(goRow));
                        }
                        connect.setInitVert(currentVert);
                        connect.setDestVert(vertexMap.get(value.get(goRow)));
                        allEdges.add(connect);
                    }
                    else if(value.get(goRow)!=-1){
                        Edges connect = new Edges();
                        if(specialCase.containsKey(simbols.get(goRow))){
                            connect.setID(specialCase.get(simbols.get(goRow)));
                        }
                        else{
                            connect.setID(simbols.get(goRow));
                        }
                        connect.setInitVert(currentVert);
                        Vertex createVertex = new Vertex();
                        createVertex.setID(value.get(goRow));
                        if(aceptacion.contains(value.get(goRow))){
                            createVertex.setIsEnd(true);
                        }
                        vertexMap.put(value.get(goRow), createVertex);
                        connect.setDestVert(createVertex);
                        allEdges.add(connect);
                    }
                }
                currentVert.setNextEdge(allEdges);
            }
        }
        return root;

    }

    private static HashMap<Integer, List<Integer>> minimizar (ArrayList<ArrayList<Integer>> matriz, ArrayList<String> simbols){
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
                //System.out.println(minMatRow);
                minMatrix.add(minMatRow);
            }
            ArrayList<ArrayList<Integer>> newPi = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> newConjunto = new ArrayList<ArrayList<Integer>>();
            HashMap<ArrayList<Integer>, Integer> reference = new HashMap<ArrayList<Integer>, Integer>();
            int ID = 0;
            for (int i = 0; i < minMatrix.size(); i++){
                ArrayList<Integer> minMatRow = minMatrix.get(i);
                ArrayList<Integer> piSub = new ArrayList<Integer>();
                //System.out.println("MINMATRIX :" + minMatrix);
                //System.out.println("minMatRow :" + minMatRow);
                //System.out.println("globalAcept :" + globalAcept);
                //System.out.println("newConjunto :" + newConjunto);
                if(!newConjunto.contains(minMatRow) && !globalAcept.contains(i)){   
                    reference.put(minMatRow, ID);
                    ID++;
                    piSub.add(i);
                    newPi.add(piSub);
                    newConjunto.add(minMatRow);
                }
                else if(globalAcept.contains(i)){
                    piSub.add(i);
                    newPi.add(piSub);
                    ID++;
                }
                else{
                    if(reference.containsKey(minMatRow)){
                        newPi.get(reference.get(minMatRow)).add(i);
                    }
                }
                //System.out.println("piSub :" + piSub);
                //System.out.println("newPi :" + newPi);

            }
            System.out.println("Separacion Conjunto PI");
            System.out.println("PI: " + newPi);
            //System.out.println(newPi);
            if(newPi.equals(pi)){
                minimize = false;
                System.out.println("PI DONE");
                System.out.println(newPi);
                System.out.println("Min Matriz");
                returnMatrix = minMatrix;
                for (int i = 0; i < returnMatrix.size(); i++){
                    returnMatrix.get(i).add(0,i);
                }

            }
            pi.clear();
            pi = newPi;
        }
        //System.out.println(matriz);
        //System.out.println(piRow);
        ArrayList<List<Integer>> noDups = new ArrayList<List<Integer>>();
        HashMap<Integer, List<Integer>> noDupsAndIDs = new HashMap<Integer, List<Integer>>();
        int IDcont = 0;
        ArrayList<Integer> newAceptacion = new ArrayList<Integer>();
        for (int i = 0; i < returnMatrix.size(); i++){
            ArrayList<Integer> checkDupRow = returnMatrix.get(i);
            int checkID = checkDupRow.get(0);
            List<Integer> noIndex = checkDupRow.subList(1, checkDupRow.size()); 
            if(!noDups.contains(noIndex) || aceptacion.contains(checkID)){
                noDupsAndIDs.put(IDcont, noIndex);
                noDups.add(noIndex);
                if(aceptacion.contains(checkID)){
                    newAceptacion.add(IDcont);
                }
                IDcont++;
            }

        }
        returnMatrix.add(aceptacion);
        noDupsAndIDs.put(-1, newAceptacion);
        //System.out.println("OGMATRIX: " + returnMatrix);
        //System.out.println("NoDUps: " + noDups);
        return noDupsAndIDs;
    }
    
}
