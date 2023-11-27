
package model.bean;

import java.util.Date;


public class Cliente {
    private long ID;
    private String nome;
    private Date datanasc;

    public Cliente(long ID, String nome, Date datanasc) {
        this.ID = ID;
        this.nome = nome;
        this.datanasc = datanasc;
    }

    public Cliente(String nome, Date datanasc) {
        this.nome = nome;
        this.datanasc = datanasc;
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

    public Date getDatanasc() {
        return datanasc;
    }

    public void setDatanasc(Date datanasc) {
        this.datanasc = datanasc;
    }
}
