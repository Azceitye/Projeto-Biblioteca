
package model.bean;


public class Editora {
    private long ID;
    private String nome;

    public Editora(long ID, String nome) {
        this.ID = ID;
        this.nome = nome;
    }

    public Editora(String nome) {
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
