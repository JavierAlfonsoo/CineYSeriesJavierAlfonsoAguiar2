package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, Pelicula.class, Genero.class, UsuarioPeliRef.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "peliSeries_bd")
                    .allowMainThreadQueries() //
                    .build();
        }
        return INSTANCE;
    }
}