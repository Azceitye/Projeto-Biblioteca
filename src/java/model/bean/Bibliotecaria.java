
package model.bean;


public class Bibliotecaria {
    private long ID;
    private Long responsavelID;
    private long conjuntoID;
    private String nome;
    private String nivel;
    private String instituicao;

    public Bibliotecaria(long ID, long conjuntoID, String nome, String nivel, String instituicao) {
        this.ID = ID;
        this.conjuntoID = conjuntoID;
        this.nome = nome;
        this.nivel = nivel;
        this.instituicao = instituicao;
    }
    
    public Bibliotecaria(Long respID, long conjuntoID, String nome, String nivel, String instituicao) {
        this.responsavelID = respID;
        this.conjuntoID = conjuntoID;
        this.nome = nome;
        this.nivel = nivel;
        this.instituicao = instituicao;
    }

    public Bibliotecaria(long ID, Long responsavelID, long conjuntoID, String nome, String nivel, String instituicao) {
        this.ID = ID;
        this.responsavelID = responsavelID;
        this.conjuntoID = conjuntoID;
        this.nome = nome;
        this.nivel = nivel;
        this.instituicao = instituicao;
    }
    
    

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Long getResponsavelID() {
        return responsavelID;
    }

    public void setResponsavelID(Long responsavelID) {
        this.responsavelID = responsavelID;
    }

    public long getConjuntoID() {
        return conjuntoID;
    }

    public void setConjuntoID(long conjuntoID) {
        this.conjuntoID = conjuntoID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
    
    
    
}
