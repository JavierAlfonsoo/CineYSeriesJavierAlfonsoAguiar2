package com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "user_movie_join",
        primaryKeys = {"idUsuario", "idPelicula"},
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "idUsuario",
                        childColumns = "idUsuario",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Pelicula.class,
                        parentColumns = "id",
                        childColumns = "idPelicula",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("idUsuario"), @Index("idPelicula")})
public class UsuarioPeliRef {
    public int idUsuario;
    public int idPelicula;
}

