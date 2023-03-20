import java.util.ArrayList;

public class Vertex {
    public int id;
    public ArrayList<Edges> prevEdge;
    public ArrayList<Edges> nextEdge;
    public boolean visitedGraph;
    public boolean visitedAFD;
    public boolean isInit;
    public boolean isEnd;


    public Vertex() {
        this.id = 99;
        prevEdge = new ArrayList<Edges>();
        nextEdge = new ArrayList<Edges>();
        visitedGraph = false;
        visitedAFD = false;
        isInit = false;
        isEnd = false;

    }

    public void setNextEdge(ArrayList<Edges> nextEdge){
        this.nextEdge = nextEdge;
    }
    public void setPrevEdge(ArrayList<Edges> prevEdge){
        this.prevEdge = prevEdge;
    }
    public void setID(int id){
        this.id = id;
    }

    public void setVisitedGraph(boolean visited){
        this.visitedGraph = visited;
    }

    public void setVisitedAFD(boolean visited){
        this.visitedAFD = visited;
    }

    public void setAll(ArrayList<Edges> prevEdge, ArrayList<Edges> nextEdge, int id){
        this.nextEdge = nextEdge;
        this.prevEdge = prevEdge;
        this.id = id;
    }

    public void setIsEnd(boolean isEnd){
        this.isEnd = isEnd;
    }

    public void setIsInit(boolean isInit){
        this.isInit = isInit;
    }

    public ArrayList<Edges> getNextEdges(){
        return this.nextEdge;
    }

    public ArrayList<Edges> getPrevEdges(){
        return this.prevEdge;
    }

    public int getID(){
        return id;
    }

    public boolean getVisitedGraph(){
        return visitedGraph;
    }
    public boolean getVisitedAFD(){
        return visitedAFD;
    }
    public boolean getIsInit(){
        return isInit;
    }

    public boolean getIsEnd(){
        return isEnd;
    }

}
