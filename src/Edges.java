public class Edges {
    public Vertex initVert;
    public Vertex destVert;
    public String id;

    public Edges(){
        initVert = null;
        destVert = null;
        id = "-";
    }

    public void setDestVert(Vertex destVert){
        this.destVert = destVert;
    }
    public void setInitVert(Vertex initVert){
        this.initVert = initVert;
    }
    public void setID(String id){
        this.id = id;
    }

    public void setAll(Vertex initVert, Vertex destVert, String id){
        this.destVert = destVert;
        this.initVert = initVert;
        this.id = id;
    }

    public Vertex getDestVert(){
        return this.destVert;
    }
    public Vertex getInitVert(){
        return this.initVert;
    }

    public String getID(){
        return id;
    }

}
