package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Entity;

@Entity(tableName = "user_movie_join",
        primaryKeys = {"userId", "movieId"})
public class UsuarioPeliRef {
    public int userId;
    public int movieId;
}