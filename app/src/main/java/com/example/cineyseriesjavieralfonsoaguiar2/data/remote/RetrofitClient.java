package com.example.cineyseriesjavieralfonsoaguiar2.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {
    private static final String BASE_URL = "https://api.tvmaze.com/";
    private static TvMazeApi api;

    private RetrofitClient() {
    }

    public static TvMazeApi getApi() {
        if (api == null) {
            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TvMazeApi.class);
        }
        return api;
    }
}
