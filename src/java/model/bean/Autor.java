
package model.bean;

public class Autor {
    long ID;
    String nome;

    public Autor(String nome) {
        this.nome = nome;
    }

    
    
    public Autor(long ID, String nome) {
        this.ID = ID;
        this.nome = nome;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    
}
