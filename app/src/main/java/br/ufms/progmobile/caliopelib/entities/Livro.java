package br.ufms.progmobile.caliopelib.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Livro implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long livroId;
    private String titulo;
    private String descricao;
    private String fotoPath;
    private int avaliacao;


    public Livro(String titulo, String descricao, String fotoPath, int avaliacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.fotoPath = fotoPath;
        this.avaliacao = avaliacao;
    }

    public Livro(String titulo, int avaliacao) {
        this.titulo = titulo;
        this.avaliacao = avaliacao;
    }

    public long getLivroId() {
        return livroId;
    }

    public void setLivroId(long livroId) {
        this.livroId = livroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public int getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(int avaliacao) {
        this.avaliacao = avaliacao;
    }
}
