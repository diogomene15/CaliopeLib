package br.ufms.progmobile.caliopelib.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Alarme implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long alarmeId;

    private long pendingIntentId;

    private long usuarioId;
    private String livro;
    private int hora;
    private int minuto;
    public Alarme() {
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public long getPendingIntentId() {
        return pendingIntentId;
    }

    public void setPendingIntentId(long pendingIntentId) {
        this.pendingIntentId = pendingIntentId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Alarme(String livro, int hora, int minuto) {
        this.livro = livro;
        this.hora = hora;
        this.minuto = minuto;
    }

    public Alarme(long usuarioId, String livro, int hora, int minuto) {
        this.usuarioId = usuarioId;
        this.livro = livro;
        this.hora = hora;
        this.minuto = minuto;
    }

    public Alarme(long alarmeId, long pendingIntentId, long usuarioId, String livro, int hora, int minuto) {
        this.alarmeId = alarmeId;
        this.pendingIntentId = pendingIntentId;
        this.usuarioId = usuarioId;
        this.livro = livro;
        this.hora = hora;
        this.minuto = minuto;
    }

    public void setAlarmeId(long alarmeId) {
        this.alarmeId = alarmeId;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public long getAlarmeId() {
        return alarmeId;
    }

    public String getLivro() {
        return livro;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarme alarme = (Alarme) o;
        return usuarioId == alarme.usuarioId && hora == alarme.hora && minuto == alarme.minuto && Objects.equals(livro, alarme.livro);
    }

    public boolean equalsUsuarioLivro(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarme alarme = (Alarme) o;
        return usuarioId == alarme.usuarioId && Objects.equals(livro, alarme.livro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, livro, hora, minuto);
    }

    public String getHorarioString(){
        return String.format("%d:%d", this.hora, this.minuto);
    }

    @Override
    public String toString() {
        return "Alarme{" +
                "alarmeId=" + alarmeId +
                ", pendingIntentId=" + pendingIntentId +
                ", usuarioId=" + usuarioId +
                ", livro='" + livro + '\'' +
                ", hora=" + hora +
                ", minuto=" + minuto +
                '}';
    }
}