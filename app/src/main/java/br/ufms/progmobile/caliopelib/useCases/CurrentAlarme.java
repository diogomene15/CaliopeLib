package br.ufms.progmobile.caliopelib.useCases;

import br.ufms.progmobile.caliopelib.entities.Alarme;
import br.ufms.progmobile.caliopelib.entities.Usuario;

public class CurrentAlarme {
    private static CurrentAlarme instance;
    private Alarme alarme;

    private CurrentAlarme() {}

    public static synchronized CurrentAlarme getInstance() {
        if (instance == null) {
            instance = new CurrentAlarme();
        }
        return instance;
    }

    public void setAlarme(Alarme alarme) {
        this.alarme = alarme;
    }

    public Alarme getAlarme() {
        return alarme;
    }
}