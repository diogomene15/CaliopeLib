package br.ufms.progmobile.caliopelib.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Livro implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long livroId;
    private long usuarioId;
    private String titulo;
    private String descricao;
    private String fotoPath;
    private int avaliacao;

    public Livro(){}

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

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(titulo, livro.titulo) && Objects.equals(descricao, livro.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, descricao);
    }

}
