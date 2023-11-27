
package model.bean;

import java.util.Date;
import java.util.List;


public class Emprestimo {
    private long ID;
    private long clienteID;
    private long biblioID;
    private Date datadevolucao;
    private Date datareg;
    private double valmultadiaria;
    private String status;
    private List<String> listItensISBN;

    public Emprestimo(long ID, long clienteID, long biblioID, Date datadevolucao, Date datareg, double valmultadiaria, String status, List<String> listItensID) {
        this.ID = ID;
        this.clienteID = clienteID;
        this.biblioID = biblioID;
        this.datadevolucao = datadevolucao;
        this.datareg = datareg;
        this.valmultadiaria = valmultadiaria;
        this.status = status;
        this.listItensISBN = listItensID;
    }

    public Emprestimo(long clienteID, long biblioID, Date datadevolucao, double valmultadiaria, String status,List<String> listItensID) {
        this.clienteID = clienteID;
        this.biblioID = biblioID;
        this.datadevolucao = datadevolucao;
        this.valmultadiaria = valmultadiaria;
        this.status = status;
        this.listItensISBN = listItensID;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getClienteID() {
        return clienteID;
    }

    public void setClienteID(long clienteID) {
        this.clienteID = clienteID;
    }

    public long getBiblioID() {
        return biblioID;
    }

    public void setBiblioID(long biblioID) {
        this.biblioID = biblioID;
    }

    public Date getDatadevolucao() {
        return datadevolucao;
    }

    public void setDatadevolucao(Date datadevolucao) {
        this.datadevolucao = datadevolucao;
    }

    public Date getDatareg() {
        return datareg;
    }

    public void setDatareg(Date datareg) {
        this.datareg = datareg;
    }

    public double getValmultadiaria() {
        return valmultadiaria;
    }

    public void setValmultadiaria(double valmultadiaria) {
        this.valmultadiaria = valmultadiaria;
    }

    public List<String> getlistItensISBN() {
        return listItensISBN;
    }

    public void setlistItensISBN(List<String> listItensID) {
        this.listItensISBN = listItensID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }    
}
