package com.example.cineyseriesjavieralfonsoaguiar2.datos;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.dao.DaoApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Genero;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Usuario;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.UsuarioPeliRef;

@Database(entities = {Usuario.class, Pelicula.class, Genero.class, UsuarioPeliRef.class}, version = 4)
public abstract class BaseDatosApp extends RoomDatabase {
    public abstract DaoApp daoApp();

    private static BaseDatosApp INSTANCE;

    public static BaseDatosApp obtenerInstancia(Context context) {
        if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BaseDatosApp.class, "peliSeries_bd")
                    .allowMainThreadQueries() //
                    .fallbackToDestructiveMigration()
                    .build();
            precargarDatos();
        }
        return INSTANCE;
    }

    private static void precargarDatos() {
        DaoApp dao = INSTANCE.daoApp();
        if (dao.contarGeneros() == 0) {
            dao.insertarGenero(new Genero(1, "Comedia"));
            dao.insertarGenero(new Genero(2, "Suspense"));
            dao.insertarGenero(new Genero(3, "Ciencia ficción"));
            dao.insertarGenero(new Genero(4, "Drama"));
        }
        if (dao.contarPeliculas() == 0) {
            dao.insertarPelicula(new Pelicula(
                    "Snatch: cerdos y diamantes",
                    "Una comedia criminal de ritmo rÁpido con boxeadores, ladrones y diamantes.",
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
            dao.insertarPelicula(new Pelicula(
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

