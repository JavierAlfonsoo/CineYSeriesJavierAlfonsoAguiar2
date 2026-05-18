package com.example.cineyseriesjavieralfonsoaguiar2.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.data.dao.AppDao;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Genero;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Usuario;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.UsuarioPeliRef;

@Database(entities = {Usuario.class, Pelicula.class, Genero.class, UsuarioPeliRef.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AppDao appDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "peliSeries_bd")
                    .allowMainThreadQueries() //
                    .fallbackToDestructiveMigration()
                    .build();
            precargarDatos();
        }
        return INSTANCE;
    }

    private static void precargarDatos() {
        AppDao dao = INSTANCE.appDao();
        if (dao.countGeneros() == 0) {
            dao.insertGenero(new Genero(1, "Comedia"));
            dao.insertGenero(new Genero(2, "Suspense"));
            dao.insertGenero(new Genero(3, "Ciencia ficcion"));
            dao.insertGenero(new Genero(4, "Drama"));
        }
        if (dao.countPeliculas() == 0) {
            dao.insertPelicula(new Pelicula(
                    "Snatch: cerdos y diamantes",
                    "Una comedia criminal de ritmo rapido con boxeadores, ladrones y diamantes.",
                    "Guy Ritchie",
                    "Jason Statham",
                    "Alan Ford",
                    "Comedia",
                    "Película",
                    "04/11/2000",
                    "21:00",
                    99,
                    true,
                    true,
                    5,
                    R.drawable.snatch,
                    null));
            dao.insertPelicula(new Pelicula(
                    "El juego",
                    "Un banquero recibe una experiencia misteriosa que altera por completo su vida.",
                    "David Fincher",
                    "Michael Douglas",
                    "Sean Penn",
                    "Suspense",
                    "Película",
                    "12/09/1997",
                    "22:30",
                    128,
                    false,
                    false,
                    4.5f,
                    R.drawable.eljuego,
                    null));
        }
    }
}

