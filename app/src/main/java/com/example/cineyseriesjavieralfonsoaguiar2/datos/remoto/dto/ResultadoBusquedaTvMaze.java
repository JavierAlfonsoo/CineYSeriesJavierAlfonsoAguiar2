package com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto.dto;

import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultadoBusquedaTvMaze {
    @SerializedName("score")
    public double puntuacion;

    @SerializedName("show")
    public SerieTvMaze serie;

    public Pelicula convertirAPelicula(int imagenPorDefecto) {
        String genero = serie.generosComoTexto();
        return new Pelicula(
                serie.nombre,
                serie.resumenLimpio(),
                "TVMaze",
                "No indicado",
                "No indicado",
                genero.isEmpty() ? "Sin genero" : genero,
                "Serie",
                serie.estreno == null ? "Sin fecha" : serie.estreno,
                "21:00",
                serie.duracionMinutos == null ? 0 : serie.duracionMinutos,
                false,
                false,
                serie.valoracion == null || serie.valoracion.media == null ? 0 : serie.valoracion.media / 2,
                imagenPorDefecto,
                serie.imagenMedia());
    }

    public static class SerieTvMaze {
        @SerializedName("name")
        public String nombre;

        @SerializedName("summary")
        public String resumen;

        @SerializedName("genres")
        public List<String> generos;

        @SerializedName("premiered")
        public String estreno;

        @SerializedName("runtime")
        public Integer duracionMinutos;

        @SerializedName("rating")
        public ValoracionTvMaze valoracion;

        @SerializedName("image")
        public ImagenTvMaze imagen;

        public String generosComoTexto() {
            if (generos == null || generos.isEmpty()) {
                return "";
            }
            return String.join(", ", generos);
        }

        public String resumenLimpio() {
            if (resumen == null) {
                return "Sin sinopsis disponible";
            }
            return resumen.replaceAll("<[^>]*>", "").trim();
        }

        public String imagenMedia() {
            if (imagen == null) {
                return null;
            }
            if (imagen.mediana != null) {
                return imagen.mediana;
            }
            return imagen.original;
        }
    }

    public static class ValoracionTvMaze {
        @SerializedName("average")
        public Float media;
    }

    public static class ImagenTvMaze {
        @SerializedName("medium")
        public String mediana;

        @SerializedName("original")
        public String original;
    }
}
