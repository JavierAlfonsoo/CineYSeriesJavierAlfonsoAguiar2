package com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ClienteRetrofit {
    private static final String BASE_URL = "https://api.tvmaze.com/";
    private static ApiTvMaze api;

    private ClienteRetrofit() {
    }

    public static ApiTvMaze obtenerApi() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiTvMaze.class);
        }
        return api;
    }
}
