package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import java.io.Serializable;

public class Pelicula implements Serializable {

    private int imagenId;
    private String nombre;
    private String director;
    private String protagonista;
    private String antagonista;
    private String genero;
    private String fechaEstreno;
    private int duracionMin;
    private float nota;
    private boolean isVista;
    private boolean favorito;

    // Constructores, setters y getters
    public Pelicula() {
    }

    public Pelicula(int imagenId, String director, String nombre, String protagonista,
            String antagonista, String genero, String fechaEstreno,
            int duracionMin, boolean favorito, boolean isVista, float nota) {
        this.imagenId = imagenId;
        this.director = director;
        this.nombre = nombre;
        this.protagonista = protagonista;
        this.antagonista = antagonista;
        this.genero = genero;
        this.fechaEstreno = fechaEstreno;
        this.duracionMin = duracionMin;
        this.favorito = favorito;
        this.isVista = isVista;
        this.nota = nota;
    }

    public int getImagenId() {
        return imagenId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDirector() {
        return director;
    }

    public String getProtagonista() {
        return protagonista;
    }

    public String getAntagonista() {
        return antagonista;
    }

    public String getGenero() {
        return genero;
    }

    public String getFechaEstreno() {
        return fechaEstreno;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public float getNota() {
        return nota;
    }

    public boolean isVista() {
        return isVista;
    }

    public boolean isFavorito() {
        return favorito;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                ", nombre='" + nombre + '\'' +
                ", director='" + director + '\'' +
                ", protagonista='" + protagonista + '\'' +
                ", antagonista='" + antagonista + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaEstreno='" + fechaEstreno + '\'' +
                ", duracionMin=" + duracionMin +
                ", nota=" + nota +
                ", isVista=" + isVista +
                ", favorito=" + favorito +
                '}';
    }
}
