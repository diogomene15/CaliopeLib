package br.ufms.progmobile.caliopelib.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.ufms.progmobile.caliopelib.entities.Alarme;

@Dao
public interface LivroDAO {
    @Query("SELECT * FROM Alarme WHERE livroId = :idLivro")
    public Alarme getLivro(long idLivro);

    @Query("SELECT * FROM Alarme")
    public List<Alarme> getAll();

    @Insert
    long insert(Alarme livro);

    @Update
    void update(Alarme livro);

    @Delete
    void delete(Alarme livro);
}
