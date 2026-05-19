package com.example.cineyseriesjavieralfonsoaguiar2.data.remote;

import com.example.cineyseriesjavieralfonsoaguiar2.data.remote.dto.TvMazeSearchResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvMazeApi {
    @GET("search/shows")
    Call<List<TvMazeSearchResult>> buscarSeries(@Query("q") String busqueda);
}
