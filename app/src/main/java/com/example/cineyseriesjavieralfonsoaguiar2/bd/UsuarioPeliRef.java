package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "user_movie_join",
        primaryKeys = {"userId", "movieId"},
        foreignKeys = {
                @ForeignKey(entity = Usuario.class,
                        parentColumns = "uid",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Pelicula.class,
                        parentColumns = "id",
                        childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("userId"), @Index("movieId")})
public class UsuarioPeliRef {
    public int userId;
    public int movieId;
}
