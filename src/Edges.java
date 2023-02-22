public class Edges {
    public Vertex initVert;
    public Vertex destVert;
    public Character id;

    public Edges(){
        initVert = null;
        destVert = null;
        id = '-';
    }

    public void setDestVert(Vertex destVert){
        this.destVert = destVert;
    }
    public void setInitVert(Vertex initVert){
        this.initVert = initVert;
    }
    public void setID(Character id){
        this.id = id;
    }

    public void setAll(Vertex initVert, Vertex destVert, Character id){
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

    public Character getID(){
        return id;
    }

}
