package com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto;

import com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto.dto.ResultadoBusquedaTvMaze;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiTvMaze {
    @GET("search/shows")
    Call<List<ResultadoBusquedaTvMaze>> buscarSeries(@Query("q") String busqueda);
}
