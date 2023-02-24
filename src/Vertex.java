import java.util.ArrayList;

public class Vertex {
    public int id;
    public ArrayList<Edges> prevEdge;
    public ArrayList<Edges> nextEdge;
    public boolean visited;


    public Vertex() {
        this.id = 99;
        prevEdge = new ArrayList<Edges>();
        nextEdge = new ArrayList<Edges>();
        visited = false;
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

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public void setAll(ArrayList<Edges> prevEdge, ArrayList<Edges> nextEdge, int id){
        this.nextEdge = nextEdge;
        this.prevEdge = prevEdge;
        this.id = id;
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

    public boolean getVisited(){
        return visited;
    }

}
