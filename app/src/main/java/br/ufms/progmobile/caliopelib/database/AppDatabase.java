package br.ufms.progmobile.caliopelib.database;

import android.content.Context;

import androidx.room.Database;

import androidx.room.RoomDatabase;
import br.ufms.progmobile.caliopelib.dao.UsuarioDAO;
import br.ufms.progmobile.caliopelib.dao.LivroDAO;
import br.ufms.progmobile.caliopelib.dao.AlarmeDAO;
import br.ufms.progmobile.caliopelib.entities.Usuario;
import br.ufms.progmobile.caliopelib.entities.Livro;
import br.ufms.progmobile.caliopelib.entities.Alarme;
import androidx.room.Room;

@Database(entities = {Usuario.class, Livro.class, Alarme.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    private static AppDatabase INSTANCE;
    public static AppDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,"CaliopeLibApp")
                    .allowMainThreadQueries().build();
        }
        return INSTANCE;
    }
    public abstract UsuarioDAO usuarioDao();
    public abstract LivroDAO livroDao();
    public abstract AlarmeDAO alarmeDao();
}
