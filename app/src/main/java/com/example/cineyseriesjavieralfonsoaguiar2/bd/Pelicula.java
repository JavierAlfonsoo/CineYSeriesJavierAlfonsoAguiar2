package com.example.cineyseriesjavieralfonsoaguiar2.bd;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "peliculas")
public class Pelicula {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String titulo;
    public String sinopsis;
    public String director;
    public String protagonista;
    public String antagona;
    public String genero;
    public String fechaEstreno;
    public int duracion;
    public boolean visto;
    public boolean favorito;
    public float rating;
}