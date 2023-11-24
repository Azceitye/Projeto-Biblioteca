/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.bean;

import java.util.Date;

/**
 *
 * @author telva
 */
public class Livro {
    long ID;
    String titulo;
    String subtitulo;
    String edicao;
    String autor;
    String editora;
    Date datapublic;

    public Livro(String titulo, String subtitulo, String edicao, String autor, String editora, Date datapublic) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.edicao = edicao;
        this.autor = autor;
        this.editora = editora;
        this.datapublic = datapublic;
    }

    public Livro(long ID, String titulo, String subtitulo, String edicao, String autor, String editora, Date datapublic) {
        this.ID = ID;
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.edicao = edicao;
        this.autor = autor;
        this.editora = editora;
        this.datapublic = datapublic;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public Date getDatapublic() {
        return datapublic;
    }

    public void setDatapublic(Date datapublic) {
        this.datapublic = datapublic;
    }

    
    
}
