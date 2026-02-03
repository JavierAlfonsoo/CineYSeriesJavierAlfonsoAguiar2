package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class NuevaPeliculaActivity extends AppCompatActivity {

    private EditText etNombre, etDirector, etProtagonista, etAntagonista, etGenero, etFecha, etDuracion;
    private RatingBar rbNota;
    private CheckBox cbVista, cbFavorito;
    private Button btnAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_pelicula_layout);

        etNombre = findViewById(R.id.et_nombre);
        etDirector = findViewById(R.id.et_director);
        etProtagonista = findViewById(R.id.et_protagonista);
        etAntagonista = findViewById(R.id.et_antagonista);
        etGenero = findViewById(R.id.et_genero);
        etFecha = findViewById(R.id.et_fecha);
        etDuracion = findViewById(R.id.et_duracion);
        rbNota = findViewById(R.id.rb_nota);
        cbVista = findViewById(R.id.cb_vista);
        cbFavorito = findViewById(R.id.cb_favorito);
        btnAceptar = findViewById(R.id.btn_aceptar);

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPelicula();
            }
        });
    }

    private void guardarPelicula() {
        // Obtener los valores de los EditText
        String nombre = etNombre.getText().toString().trim();
        String director = etDirector.getText().toString().trim();
        String protagonista = etProtagonista.getText().toString().trim();
        String antagonista = etAntagonista.getText().toString().trim();
        String genero = etGenero.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String duracionStr = etDuracion.getText().toString().trim();
        // Nota se obtiene directamente del RatingBar, no como String

        // recojo valores de los cb
        boolean isVista = cbVista.isChecked();
        boolean isFavorito = cbFavorito.isChecked();

        // compruebo que nada este vaio
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(director) ||
                TextUtils.isEmpty(protagonista) || TextUtils.isEmpty(antagonista) ||
                TextUtils.isEmpty(genero) || TextUtils.isEmpty(fecha) ||
                TextUtils.isEmpty(duracionStr)) {

            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int duracion = Integer.parseInt(duracionStr);
        float nota = rbNota.getRating();

        Pelicula nuevaPelicula = new Pelicula(
                0, // imagenId
                director,
                nombre,
                protagonista,
                antagonista,
                genero,
                fecha,
                duracion,
                isFavorito,
                isVista,
                nota);

        // 4. Mostrar Toast con los datos
        Intent intent = new Intent();
        intent.putExtra("nueva_pelicula", nuevaPelicula);
        setResult(RESULT_OK, intent);
        finish();

    }
}
