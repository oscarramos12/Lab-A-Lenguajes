import java.util.ArrayList;

public class treeNode {
    String value;
    ArrayList<treeNode> hojas = new ArrayList<treeNode>();

    public treeNode(String value) {
        this.value = value;
        this.hojas = new ArrayList<treeNode>();;
    }
    public String getValue(){
        return value;
    }
    public ArrayList<treeNode> getHojas(){
        return hojas;
    }
    public void setHoja(treeNode hoja){
        hojas.add(hoja);
    }
    public void setValue(String value){
        this.value = value;
    }
}
