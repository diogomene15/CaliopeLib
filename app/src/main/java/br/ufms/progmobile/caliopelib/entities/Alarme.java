package br.ufms.progmobile.caliopelib.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Alarme implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long alarmeId;
    private String livro;
    private int hora;
    private int minuto;
    public Alarme() {
    }

    public Alarme(String livro, int hora, int minuto) {
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
}