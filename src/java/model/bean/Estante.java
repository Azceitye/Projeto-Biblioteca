
package model.bean;


public class Estante {
    private long ID;
    private long conjuntoID;
    private String local;

    public Estante(long conjuntoID, String local) {
        this.conjuntoID = conjuntoID;
        this.local = local;
    }

    public Estante(long ID, long conjuntoID, String local) {
        this.ID = ID;
        this.conjuntoID = conjuntoID;
        this.local = local;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getConjuntoID() {
        return conjuntoID;
    }

    public void setConjuntoID(long conjuntoID) {
        this.conjuntoID = conjuntoID;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
