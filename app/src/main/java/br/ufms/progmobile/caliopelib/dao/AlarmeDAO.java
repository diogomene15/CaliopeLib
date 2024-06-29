package br.ufms.progmobile.caliopelib.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import br.ufms.progmobile.caliopelib.entities.Alarme;

@Dao
public interface AlarmeDAO {
    @Query("SELECT * FROM Alarme WHERE alarmeId = :idAlarme")
    public Alarme getAlarme(long idAlarme);

    @Query("SELECT * FROM Alarme WHERE usuarioId = :idUsuario")
    public List<Alarme> getAlarmesByUsuario(long idUsuario);

    @Query("SELECT * FROM Alarme")
    public List<Alarme> getAll();

    @Insert
    long insert(Alarme alarm);

    @Query("UPDATE Alarme SET hora = :hora AND minuto = :minuto AND livro = :livro WHERE alarmeId = :id")
    void updateById(int hora, int minuto, String livro, long id);

    @Update
    void update(Alarme alarme);

    @Delete
    void delete(Alarme alarm);
}
