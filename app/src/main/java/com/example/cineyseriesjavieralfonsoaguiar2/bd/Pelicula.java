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
    public String antagonista;
    public String genero;
    public String tipo;
    public String fechaEstreno;
    public String horaRecordatorio;
    public int duracion;
    public boolean visto;
    public boolean favorito;
    public float rating;
    public int imagenResId;
    public String imagenUri;

    public Pelicula() {
    }

    public Pelicula(String titulo, String sinopsis, String director, String protagonista,
                    String antagonista, String genero, String tipo, String fechaEstreno,
                    String horaRecordatorio, int duracion, boolean visto, boolean favorito,
                    float rating, int imagenResId, String imagenUri) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.director = director;
        this.protagonista = protagonista;
        this.antagonista = antagonista;
        this.genero = genero;
        this.tipo = tipo;
        this.fechaEstreno = fechaEstreno;
        this.horaRecordatorio = horaRecordatorio;
        this.duracion = duracion;
        this.visto = visto;
        this.favorito = favorito;
        this.rating = rating;
        this.imagenResId = imagenResId;
        this.imagenUri = imagenUri;
    }
}
