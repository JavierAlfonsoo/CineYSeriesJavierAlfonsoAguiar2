package com.example.cineyseriesjavieralfonsoaguiar2.data.remote.dto;

import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Pelicula;

import java.util.List;

public class TvMazeSearchResult {
    public double score;
    public TvMazeShow show;

    public Pelicula toPelicula(int imagenPorDefecto) {
        String genero = show.generosComoTexto();
        return new Pelicula(
                show.name,
                show.resumenLimpio(),
                "TVMaze",
                "No indicado",
                "No indicado",
                genero.isEmpty() ? "Sin genero" : genero,
                "Serie",
                show.premiered == null ? "Sin fecha" : show.premiered,
                "21:00",
                show.runtime == null ? 0 : show.runtime,
                false,
                false,
                show.rating == null || show.rating.average == null ? 0 : show.rating.average / 2,
                imagenPorDefecto,
                show.imagenMedia());
    }

    public static class TvMazeShow {
        public String name;
        public String summary;
        public List<String> genres;
        public String premiered;
        public Integer runtime;
        public TvMazeRating rating;
        public TvMazeImage image;

        public String generosComoTexto() {
            if (genres == null || genres.isEmpty()) {
                return "";
            }
            return String.join(", ", genres);
        }

        public String resumenLimpio() {
            if (summary == null) {
                return "Sin sinopsis disponible";
            }
            return summary.replaceAll("<[^>]*>", "").trim();
        }

        public String imagenMedia() {
            if (image == null) {
                return null;
            }
            if (image.medium != null) {
                return image.medium;
            }
            return image.original;
        }
    }

    public static class TvMazeRating {
        public Float average;
    }

    public static class TvMazeImage {
        public String medium;
        public String original;
    }
}
