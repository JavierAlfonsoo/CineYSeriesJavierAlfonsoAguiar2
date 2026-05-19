package com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.data.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.data.remote.RetrofitClient;
import com.example.cineyseriesjavieralfonsoaguiar2.data.remote.dto.TvMazeSearchResult;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.main.BaseDrawerActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecomendacionesActivity extends BaseDrawerActivity {

    private EditText etBusqueda;
    private TextView tvEstado;
    private ProgressBar progressBar;
    private RecomendacionAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
        setDrawerTitle(R.string.recomendaciones);

        db = AppDatabase.getInstance(this);
        etBusqueda = findViewById(R.id.etBusquedaRecomendaciones);
        Button btnBuscar = findViewById(R.id.btnBuscarRecomendaciones);
        tvEstado = findViewById(R.id.tvEstadoRecomendaciones);
        progressBar = findViewById(R.id.progressRecomendaciones);
        RecyclerView recyclerView = findViewById(R.id.rvRecomendaciones);

        adapter = new RecomendacionAdapter(new ArrayList<>(), this::guardarRecomendacion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBuscar.setOnClickListener(v -> buscarRecomendaciones());
        buscarRecomendaciones("comedy");
    }

    private void buscarRecomendaciones() {
        String busqueda = etBusqueda.getText().toString().trim();
        buscarRecomendaciones(TextUtils.isEmpty(busqueda) ? "comedy" : busqueda);
    }

    private void buscarRecomendaciones(String busqueda) {
        progressBar.setVisibility(View.VISIBLE);
        tvEstado.setText(R.string.fuente_tvmaze);

        RetrofitClient.getApi()
                .buscarSeries(busqueda)
                .enqueue(new Callback<List<TvMazeSearchResult>>() {
                    @Override
                    public void onResponse(Call<List<TvMazeSearchResult>> call,
                                           Response<List<TvMazeSearchResult>> response) {
                        progressBar.setVisibility(View.GONE);
                        if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                            adapter.actualizar(new ArrayList<>());
                            tvEstado.setText(R.string.sin_resultados);
                            return;
                        }
                        adapter.actualizar(response.body());
                        tvEstado.setText(R.string.fuente_tvmaze);
                    }

                    @Override
                    public void onFailure(Call<List<TvMazeSearchResult>> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        adapter.actualizar(new ArrayList<>());
                        tvEstado.setText(R.string.error_retrofit);
                    }
                });
    }

    private void guardarRecomendacion(TvMazeSearchResult recomendacion) {
        if (recomendacion == null || recomendacion.show == null) {
            return;
        }
        Pelicula pelicula = recomendacion.toPelicula(R.drawable.palomitas_logo);
        db.appDao().insertPelicula(pelicula);
        Toast.makeText(this, R.string.recomendacion_anadida, Toast.LENGTH_SHORT).show();
    }
}

