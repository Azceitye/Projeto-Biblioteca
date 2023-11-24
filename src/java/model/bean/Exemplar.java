
package model.bean;

public class Exemplar {
    private final String ISBN;
    private long estanteID;
    private long livroID;
    private String status;

    public Exemplar(String ISBN, long estanteID, long livroID, String status) {
        this.ISBN = ISBN;
        this.estanteID = estanteID;
        this.livroID = livroID;
        this.status = status;
    }

    public long getEstanteID() {
        return estanteID;
    }

    public void setEstanteID(long estanteID) {
        this.estanteID = estanteID;
    }

    public long getLivroID() {
        return livroID;
    }

    public void setLivroID(long livroID) {
        this.livroID = livroID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getISBN() {
        return ISBN;
    }
    
    
}
