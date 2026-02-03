package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "peliculas")
public class Pelicula {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String titulo;
    public String sinopsis;
    public String imagenUrl;
    public float rating;
}