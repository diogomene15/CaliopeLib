package br.ufms.progmobile.caliopelib.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.ufms.progmobile.caliopelib.entities.Alarme;
import br.ufms.progmobile.caliopelib.entities.Livro;

@Dao
public interface LivroDAO {
    @Query("SELECT * FROM Livro WHERE livroId = :idLivro")
    public Alarme getLivro(long idLivro);

    @Query("SELECT * FROM Livro WHERE usuarioId = :idUsuario")
    public List<Livro> getLivrosByUsuario(long idUsuario);

    @Query("SELECT * FROM Livro WHERE usuarioId is NULL")
    public List<Livro> getLivrosSemUsuario();


    @Query("SELECT * FROM Livro")
    public List<Alarme> getAll();

    @Insert
    long insert(Livro livro);

    @Update
    void update(Livro livro);

    @Delete
    void delete(Livro livro);
}
