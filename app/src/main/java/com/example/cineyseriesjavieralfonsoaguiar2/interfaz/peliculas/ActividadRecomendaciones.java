package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas;

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
import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto.ClienteRetrofit;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto.dto.ResultadoBusquedaTvMaze;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadBaseMenuLateral;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadRecomendaciones extends ActividadBaseMenuLateral {

    private EditText etBusqueda;
    private TextView tvEstado;
    private ProgressBar progressBar;
    private AdaptadorRecomendacion adaptador;
    private BaseDatosApp baseDatos;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_recomendaciones);
        establecerTituloMenuLateral(R.string.recomendaciones);

        baseDatos = BaseDatosApp.obtenerInstancia(this);
        etBusqueda = findViewById(R.id.etBusquedaRecomendaciones);
        Button btnBuscar = findViewById(R.id.btnBuscarRecomendaciones);
        tvEstado = findViewById(R.id.tvEstadoRecomendaciones);
        progressBar = findViewById(R.id.progressRecomendaciones);
        RecyclerView RecyclerView = findViewById(R.id.rvRecomendaciones);

        adaptador = new AdaptadorRecomendacion(new ArrayList<>(), this::guardarRecomendacion);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adaptador);

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

        ClienteRetrofit.obtenerApi()
                .buscarSeries(busqueda)
                .enqueue(new Callback<List<ResultadoBusquedaTvMaze>>() {
                    @Override
                    public void onResponse(Call<List<ResultadoBusquedaTvMaze>> call,
                                           Response<List<ResultadoBusquedaTvMaze>> response) {
                        progressBar.setVisibility(View.GONE);
                        if (!response.isSuccessful() || response.body() == null || response.body().isEmpty()) {
                            adaptador.actualizar(new ArrayList<>());
                            tvEstado.setText(R.string.sin_resultados);
                            return;
                        }
                        adaptador.actualizar(response.body());
                        tvEstado.setText(R.string.fuente_tvmaze);
                    }

                    @Override
                    public void onFailure(Call<List<ResultadoBusquedaTvMaze>> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        adaptador.actualizar(new ArrayList<>());
                        tvEstado.setText(R.string.error_retrofit);
                    }
                });
    }

    private void guardarRecomendacion(ResultadoBusquedaTvMaze recomendacion) {
        if (recomendacion == null || recomendacion.serie == null) {
            return;
        }
        Pelicula pelicula = recomendacion.convertirAPelicula(R.drawable.palomitas_logo);
        baseDatos.daoApp().insertarPelicula(pelicula);
        Toast.makeText(this, R.string.recomendacion_anadida, Toast.LENGTH_SHORT).show();
    }
}

