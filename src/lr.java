import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;



public class lr {

    public static LinkedHashMap<String, ArrayList<String>> getGrammar(String filename, Vertex rootAFD){ 
        LinkedHashMap<String, ArrayList<String>> grammar = new LinkedHashMap<String, ArrayList<String>>();
        HashMap<String, String> specialCases = new HashMap<String, String>();
        specialCases.put("expression", "E");
        specialCases.put("term", "T");
        specialCases.put("factor", "F");
        specialCases.put("ASSIGNOP", ":=");
        specialCases.put("LT", "<");
        specialCases.put("EQ", "=");
        specialCases.put("PLUS", "+");
        specialCases.put("MINUS", "-");
        specialCases.put("TIMES", "*");
        specialCases.put("DIV", "/");
        specialCases.put("LPAREN", "(");
        specialCases.put("RPAREN", ")");
        specialCases.put("SEMICOLON", ";");
        String grammarKey;
        try {
            FileInputStream inputStream = new FileInputStream(filename);
            InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if(line.contains(":") && !line.contains("/*") && !line.contains("*/")){
                    grammarKey = "";
                    for (int getKey = 0; getKey < line.length(); getKey++){
                        if(line.charAt(getKey) != ':'){
                            grammarKey += line.charAt(getKey);
                        }
                        else if(line.charAt(getKey) == ':'){
                            break;
                        }
                    }
                    if(specialCases.containsKey(grammarKey)){
                        grammarKey = specialCases.get(grammarKey);
                    }
                    line = bufferedReader.readLine();
                    while(!line.contains(";")){
                        line = line.replace("|", "");
                        String lineContent = "";
                        for (int i = 0; i < line.length(); i++){
                            if(line.charAt(i) != ' '){
                                String word = "";
                                for (int getWord = i; getWord < line.length(); getWord++){
                                    //System.out.println(line.charAt(getWord));
                                    if(line.charAt(getWord) != ' '){
                                        word += line.charAt(getWord);
                                        if(getWord == line.length()-1 && word.length() > 0){
                                            if(specialCases.containsKey(word)){
                                                lineContent += specialCases.get(word);
                                            }
                                            else{
                                                lineContent += word;
                                            }
                                            i = line.length();
                                            if(grammar.containsKey(grammarKey)){
                                                ArrayList<String> getGrammarValue = grammar.get(grammarKey);
                                                getGrammarValue.add(lineContent);
                                                grammar.put(grammarKey,getGrammarValue);
                                                line = bufferedReader.readLine();
                                                line = line.replace("|", "");
                                                lineContent = "";
                                                getWord = 0;
                                                i = 0;
                                                if(line.contains(":")){
                                                    System.err.println("ERROR NO SE ENCONTRO ;");
                                                    break;
                                                }
                                            }
                                            else{
                                                ArrayList<String> grammarValue = new ArrayList<String>();
                                                grammarValue.add(lineContent);
                                                grammar.put(grammarKey, grammarValue);
                                                line = bufferedReader.readLine();
                                                line = line.replace("|", "");
                                                lineContent = "";
                                                getWord = 0;
                                                i = 0;
                                                if(line.contains(":")){
                                                    System.err.println("ERROR NO SE ENCONTRO ;");
                                                    break;
                                                }
                                            }

                                            break;
                                        }
                                    }

                                    else if(word.length() > 0 && specialCases.containsKey(word)){
                                        lineContent += specialCases.get(word) + " ";
                                        i += word.length();
                                        break;
                                    }
                                    else if(word.length() > 0){
                                        lineContent += word + " ";
                                        i += word.length();
                                        break;
                                    }
                                }
                            }
                        }

                    }
                }
            }

            System.out.println("DETECTED GRAMMAR: " + grammar);
            bufferedReader.close();
            reader.close();
            return grammar;
        } catch (IOException e) {
            e.printStackTrace();
            return grammar;
        }
    }

    public static HashMap<String, ArrayList<String>> computeFirst(LinkedHashMap<String, ArrayList<String>> grammar){
        LinkedHashMap<String, ArrayList<String>> tempHashMap = new LinkedHashMap<String, ArrayList<String>>();
        LinkedHashMap<String, ArrayList<String>> reversedGrammar = new LinkedHashMap<String, ArrayList<String>>();
        HashMap<String, ArrayList<String>> first = new HashMap<String, ArrayList<String>>();
        tempHashMap.putAll(grammar);
        List<Map.Entry<String, ArrayList<String>>>  entries = new ArrayList<>(tempHashMap.entrySet());
        Collections.reverse(entries);
        for (Map.Entry<String, ArrayList<String>> entry : entries) {
            reversedGrammar.put(entry.getKey(), entry.getValue());
        }
        for(int loop = 0; loop < 2; loop++){
            for (Map.Entry<String, ArrayList<String>> entry : reversedGrammar.entrySet()) {
                ArrayList<String> producciones = entry.getValue();
                String key = entry.getKey();

                for(int i = 0; i < producciones.size(); i++){
                    String currentProduction = producciones.get(i);
                    String word = "";
                    for(int getLM = 0; getLM < currentProduction.length(); getLM++){
                        if(currentProduction.charAt(getLM) != ' '){
                            word += currentProduction.charAt(getLM);
                            if(getLM == currentProduction.length()-1 && !grammar.containsKey(word)){
                                if(!first.containsKey(key) && (getLM - (word.length()-1) == 0)){
                                    ArrayList<String> allfirsts = new ArrayList<String>();
                                    if(!allfirsts.contains(word)){
                                        allfirsts.add(word);
                                    }
                                    first.put(key, allfirsts);
                                    break;
                                }
                                else if((getLM - (word.length()-1) == 0)){
                                    ArrayList<String> values = first.get(key);
                                    if(!values.contains(word)){
                                        values.add(word);
                                        first.put(key, values);
                                    }
                                    break;
                                }
                            }
                            else if(first.containsKey(word)){
                                first.put(key, first.get(word));
                            }
                        }
                        else if(word.length() > 0){
                            if(!grammar.containsKey(word) && (getLM - word.length() == 0)){
                                if(!first.containsKey(key)){
                                    ArrayList<String> allfirsts = new ArrayList<String>();
                                    if(!allfirsts.contains(word)){
                                        allfirsts.add(word);
                                    }
                                    first.put(key, allfirsts);
                                    break;
                                }
                                else if((getLM - word.length() == 0)){
                                    ArrayList<String> values = first.get(key);
                                    if(!values.contains(word)){
                                        values.add(word);
                                        first.put(key, values);
                                    }
                                    break;
                                }
                            }
                            else if(first.containsKey(word)){
                                first.put(word, first.get(word));
                            }
                            word = "";
                        }
                    }
                }

            }
        }


        System.out.println("FIRST: " + first);
        return first;
    }

    public static void computeFollow(){

    }

    public static Map<String, Set<String>> computeFollow(LinkedHashMap<String, ArrayList<String>> grammar, HashMap<String, ArrayList<String>> first) {
        LinkedHashMap<String, Set<String>> follow = new LinkedHashMap<>();
        for (String nonTerminal : grammar.keySet()) {
            follow.put(nonTerminal, new HashSet<>());
        }
        follow.get(grammar.keySet().toArray()[0]).add("$");
        
        boolean changed;
        do {
            changed = false;
            for (String nonTerminal : grammar.keySet()) {
                for (String production : grammar.get(nonTerminal)) {
                    for (int i = 0; i < production.length(); i++) {
                        String word = "";
                        for(int getWord = i; getWord < production.length(); getWord++){
                            if(production.charAt(getWord) != ' '){
                                word += production.charAt(getWord);
                            }
                            else{
                                i += word.length();
                                break;
                            }
                        }
                        if (grammar.containsKey(word)) {
                            String B = word;
                            Set<String> beta = new HashSet<>();
                            if (i < production.length() - 1) {
                                beta.addAll(first.get(word));
                            } else {
                                beta.add("$");
                            }
                            int oldSize = follow.get(B).size();
                            follow.get(B).addAll(beta);
                            if (follow.get(B).size() != oldSize) {
                                changed = true;
                            }
                            if (first.get(word).contains("")) {
                                int j = i + 1;
                                while (j < production.length() && first.get(Character.toString(production.charAt(j))).contains("")) {
                                    for (String nonTerminalA : grammar.keySet()) {
                                        if (production.substring(j).contains(nonTerminalA)) {
                                            int oldSizeA = follow.get(nonTerminalA).size();
                                            follow.get(nonTerminalA).addAll(follow.get(B));
                                            if (follow.get(nonTerminalA).size() != oldSizeA) {
                                                changed = true;
                                            }
                                        }
                                    }
                                    j++;
                                }
                                if (j == production.length()) {
                                    for (String nonTerminalA : grammar.keySet()) {
                                        if (production.contains(nonTerminalA)) {
                                            int oldSizeA = follow.get(nonTerminalA).size();
                                            follow.get(nonTerminalA).addAll(follow.get(B));
                                            if (follow.get(nonTerminalA).size() != oldSizeA) {
                                                changed = true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } while (changed);
        System.out.println("FOLLOW: " + follow);
        return follow;
    }

    public static void computeClosure(LinkedHashMap<String, ArrayList<String>> grammar){
        LinkedHashMap<String ,LinkedHashMap<String, ArrayList<String>>> closure = new LinkedHashMap<String ,LinkedHashMap<String, ArrayList<String>>>();
        HashMap<String, Vertex> nodos = new HashMap<String, Vertex>();
        LinkedHashMap<String, ArrayList<String>> newGrammar = new LinkedHashMap<String, ArrayList<String>>();
        Iterator<String> iterator = grammar.keySet().iterator();
        String firstKey = iterator.next();
        ArrayList<String> addNewGrammar = new ArrayList<String>();
        addNewGrammar.add(firstKey);
        newGrammar.put("S'", addNewGrammar);
        newGrammar.putAll(grammar);

        Vertex root = new Vertex();
        int nodeID = 0;
        root.setID(nodeID);
        ArrayList<Edges> nextEdges = new ArrayList<Edges>();
        root.setNextEdge(nextEdges);
        nodos.put(Integer.toString(nodeID), root);
        int endLimit = 0;

        for (Map.Entry<String, ArrayList<String>> entry : newGrammar.entrySet()) {
            String key = entry.getKey();
            ArrayList<String> value = new ArrayList<String>();
            value.addAll(entry.getValue());
            for (int i = 0; i < value.size(); i++) {
                String str = value.get(i);
                value.set(i, "." + str);
                endLimit++;
            }
            newGrammar.put(key, value);
        }

        LinkedHashMap<String, ArrayList<String>> copias = new LinkedHashMap<String, ArrayList<String>>();

        for (Map.Entry<String, ArrayList<String>> copyCheck : newGrammar.entrySet()) {
            ArrayList<String> value = new ArrayList<String>();
            value.addAll(copyCheck.getValue());
            for (int search = 0; search <  value.size(); search++){
                String current = value.get(search);
                int dotPos = current.indexOf(".");
                String word = "";
                for (int getWord = dotPos+1; getWord < current.length(); getWord++){
                    if(current.charAt(getWord) != ' '){
                        word += current.charAt(getWord);
                    }
                    else{
                        break;
                    }
                }

                if(!copias.containsKey(word) && newGrammar.containsKey(word)){
                    ArrayList<String> esperando = new ArrayList<String>();
                    esperando.add(word);
                    while(!esperando.isEmpty()){
                        String id = esperando.get(0);
                        esperando.remove(0);

                        ArrayList<String> esperandoList = new ArrayList<String>();
                        esperandoList.addAll(newGrammar.get(id));

                        for (Map.Entry<String, ArrayList<String>> copiasAdd : copias.entrySet()) {
                            String refKey = copiasAdd.getKey();
                            ArrayList<String> copiasAddList = new ArrayList<String>();
                            copiasAddList.addAll(copiasAdd.getValue());
                            for(int deez = 0; deez < copiasAddList.size(); deez++){
                                if(copiasAddList.get(deez).contains(id)){
                                    copiasAddList.addAll(newGrammar.get(id));
                                    copias.put(refKey, copiasAddList);
                                    break;
                                }
                            }
                        }
                        copias.put(id, esperandoList);

                        for (int getDeriv = 0; getDeriv < esperandoList.size(); getDeriv++){
                            String currentEsperando = esperandoList.get(getDeriv);
                            int dotPosEsperando = current.indexOf(".");
                            String wordEsperando = "";
                            for (int getWord = dotPosEsperando+1; getWord < currentEsperando.length(); getWord++){
                                if(currentEsperando.charAt(getWord) != ' '){
                                    wordEsperando += currentEsperando.charAt(getWord);
                                    if(getWord == currentEsperando.length()-1 && !esperando.contains(wordEsperando) && !copias.containsKey(wordEsperando) && newGrammar.containsKey(wordEsperando)){
                                        esperando.add(wordEsperando);
                                    }
                                }
                                else if(!esperando.contains(wordEsperando) && !copias.containsKey(wordEsperando) && newGrammar.containsKey(wordEsperando)){
                                    esperando.add(wordEsperando);
                                    break;
                                }
                                else{
                                    break;
                                }
                            }
                        }
                        
                    }
                }
            }
        }
        System.out.println("NG: " + newGrammar);
        closure.put(Integer.toString(nodeID), newGrammar);
        nodeID++;

        int endStates = 0;

        LinkedHashMap<String ,ArrayList<LinkedHashMap<String, ArrayList<String>>>> byWord = new LinkedHashMap<String ,ArrayList<LinkedHashMap<String, ArrayList<String>>>>();

        for (Map.Entry<String ,LinkedHashMap<String, ArrayList<String>>> conjuntoI : closure.entrySet()) {
            String closureKey = conjuntoI.getKey();
            System.out.println(conjuntoI);
            LinkedHashMap<String, ArrayList<String>> currentConjuntoI = conjuntoI.getValue();
            
            for (Map.Entry<String, ArrayList<String>> produccion : currentConjuntoI.entrySet()) {
                String key = produccion.getKey();
                ArrayList<String> value = new ArrayList<String>();
                value.addAll(produccion.getValue());
                String word = "";
                String newProd = "";
                for(int getSingleProd = 0; getSingleProd < value.size(); getSingleProd++){
                    String currentSingleProd = value.get(getSingleProd);
                    int dotPosition = currentSingleProd.indexOf(".");
                    word = "";
                    for(int getWord = dotPosition+1; getWord < currentSingleProd.length(); getWord++){
                        if(currentSingleProd.charAt(getWord) != ' '){
                            word += currentSingleProd.charAt(getWord);
                            if(getWord == currentSingleProd.length()-1){
                                String antes = currentSingleProd.substring(0, getWord);
                                String despues = currentSingleProd.substring(getWord, currentSingleProd.length());
                                antes = antes.replace(".","");
                                despues += ".";
                                newProd = "";
                                newProd = antes + despues;
                                value.set(getSingleProd, newProd);                            

                                int copyNewDotPos = newProd.indexOf(".");
                                String checkWord = "";
                                for(int checkCopy = copyNewDotPos+1; checkCopy < newProd.length(); checkCopy++){
                                    if(newProd.charAt(checkCopy) != ' '){
                                        checkWord += newProd.charAt(checkCopy);
                                        if(checkCopy == newProd.length()-1 && copias.containsKey(checkWord)){
                                            value.addAll(copias.get(checkWord));
                                        }
                                    }
                                    else{
                                        break;
                                    }
                                }
                            }
                        }
                        else{
                            String antes = currentSingleProd.substring(0, getWord);
                            String despues = currentSingleProd.substring(getWord, currentSingleProd.length());
                            antes = antes.replace(".","");
                            despues = "." + despues;
                            newProd = "";
                            newProd = antes + despues;
                            value.set(getSingleProd, newProd);
                            int copyNewDotPos = newProd.indexOf(".");
                                String checkWord = "";
                                for(int checkCopy = copyNewDotPos+2; checkCopy < newProd.length(); checkCopy++){
                                    if(newProd.charAt(checkCopy) != ' '){
                                        checkWord += newProd.charAt(checkCopy);
                                        if(checkCopy == newProd.length()-1 && copias.containsKey(checkWord)){
                                            value.addAll(copias.get(checkWord));
                                        }
                                        else{
                                            break;
                                        }
                                    }
                                    else{
                                        break;
                                    }
                                }
                            break;
                        }
                    }
                    if(!byWord.containsKey(word)){
                        LinkedHashMap<String, ArrayList<String>> insertHashMap = new LinkedHashMap<String, ArrayList<String>>();
                        ArrayList<LinkedHashMap<String, ArrayList<String>>> lista = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
                        ArrayList<String> fake = new ArrayList<String>();
                        int getFInal = newProd.indexOf(".");
                        if(getFInal == newProd.length()-1){
                            endStates++;
                        }
                        fake.add(newProd);
                        insertHashMap.put(key, fake);
                        lista.add(insertHashMap);
                        byWord.put(word, lista);
                    }
                    else if(byWord.containsKey(word)){
                        LinkedHashMap<String, ArrayList<String>> insertHashMap = new LinkedHashMap<String, ArrayList<String>>();
                        ArrayList<LinkedHashMap<String, ArrayList<String>>> lista = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
                        ArrayList<String> fake = new ArrayList<String>();
                        int getFInal = newProd.indexOf(".");
                        if(getFInal == newProd.length()-1){
                            endStates++;
                        }
                        fake.add(newProd);
                        lista.addAll(byWord.get(word));
                        insertHashMap.put(key, fake);
                        lista.add(insertHashMap);
                        byWord.put(word, lista);

                    }
                    //System.out.println("WORD: " + word);
                    

                }
                
            }
            System.out.println("BY WORD: " + byWord);
            System.out.println("CLOSURE: " + closure);
            for (Map.Entry<String ,ArrayList<LinkedHashMap<String, ArrayList<String>>>> newSet : byWord.entrySet()) {
                String key = newSet.getKey();
                ArrayList<LinkedHashMap<String, ArrayList<String>>> values = new ArrayList<LinkedHashMap<String, ArrayList<String>>>();
                values.addAll(newSet.getValue());
                LinkedHashMap<String, ArrayList<String>> insert = new LinkedHashMap<String, ArrayList<String>>();
                LinkedHashMap<String, ArrayList<String>> convert = new LinkedHashMap<String, ArrayList<String>>();
                for (int i = 0; i < values.size(); i++){
                     convert = values.get(i);
                     for (Map.Entry<String, ArrayList<String>> deez : convert.entrySet()) {
                        insert.put(deez.getKey(), deez.getValue());
                     }
                }
                closure.put(Integer.toString(nodeID), insert);
                ArrayList<Edges> addEdges = root.getNextEdges();
                Edges edge = new Edges();
                edge.setID(key);
                Vertex destVert = new Vertex();
                destVert.setID(nodeID);
                edge.setDestVert(destVert);
                addEdges.add(edge);
                nodeID++;
            }
            System.out.println(closure);
        }
        while(true){
            for (Map.Entry<String ,LinkedHashMap<String, ArrayList<String>>> main : closure.entrySet()) {
                boolean firstSecond = true;
                if(!main.getKey().equals("0")){
                    boolean firstString = true;
                    for (Map.Entry<String, ArrayList<String>> second : main.getValue().entrySet()) {
                        ArrayList<String> getSecondValue = second.getValue();
                        String rootValue = getSecondValue.get(0);
                        if(firstSecond == true && rootValue.charAt(rootValue.length()-1) == '.'){
                            firstString = false;
                            break;
                        }
                        else if(rootValue.charAt(rootValue.length()-1) != '.'){
                            System.out.println(getSecondValue);
                        }
                        System.out.println(second.getValue());
                        firstSecond = false;
                    }
                }
                
            }
        }
    }

    public static boolean hasToken(Vertex rootAFD, String word){
        ArrayList<Vertex> esperando = new ArrayList<Vertex>();
        esperando.add(rootAFD);
        while(!esperando.isEmpty()){
            Vertex newVert = new Vertex();
            newVert = esperando.remove(0);
            ArrayList<Edges> getNextVerts = newVert.getNextEdges();
            for(int indexEdges = 0; indexEdges < getNextVerts.size(); indexEdges++){
                if(getNextVerts.get(indexEdges).getID().equals(word)){
                    return true;
                }
                else if(!esperando.contains(getNextVerts.get(indexEdges).getDestVert())){
                    esperando.add(getNextVerts.get(indexEdges).getDestVert());
                }
            }
        }
        return false;
    }

}
