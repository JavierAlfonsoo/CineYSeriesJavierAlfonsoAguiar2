package com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.data.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.main.BaseDrawerActivity;

import java.util.Calendar;

public class NuevaPeliculaActivity extends BaseDrawerActivity {

    private static final int CODIGO_SELECCIONAR_IMAGEN = 50;

    private EditText etNombre, etDirector, etProtagonista, etAntagonista, etGenero, etFecha, etHora, etDuracion, etSinopsis;
    private Spinner spTipo;
    private RatingBar rbNota;
    private CheckBox cbVista, cbFavorito;
    private AppDatabase db;
    private ImageView imgPreview;
    private String imagenSeleccionadaUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nueva_pelicula_layout);
        setDrawerTitle(R.string.nuevaPelicula);

        db = AppDatabase.getInstance(this);
        etNombre = findViewById(R.id.et_nombre);
        etDirector = findViewById(R.id.et_director);
        etProtagonista = findViewById(R.id.et_protagonista);
        etAntagonista = findViewById(R.id.et_antagonista);
        etGenero = findViewById(R.id.et_genero);
        etFecha = findViewById(R.id.et_fecha);
        etHora = findViewById(R.id.et_hora);
        etDuracion = findViewById(R.id.et_duracion);
        etSinopsis = findViewById(R.id.et_sinopsis);
        spTipo = findViewById(R.id.sp_tipo);
        rbNota = findViewById(R.id.rb_nota);
        cbVista = findViewById(R.id.cb_vista);
        cbFavorito = findViewById(R.id.cb_favorito);
        imgPreview = findViewById(R.id.img_preview);
        Button btnAceptar = findViewById(R.id.btn_aceptar);
        Button btnSeleccionarImagen = findViewById(R.id.btn_seleccionar_imagen);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipo_contenido, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adapter);

        etFecha.setOnClickListener(v -> mostrarSelectorFecha());
        etHora.setOnClickListener(v -> mostrarSelectorHora());
        btnSeleccionarImagen.setOnClickListener(v -> abrirSelectorImagen());
        btnAceptar.setOnClickListener(v -> guardarPelicula());
    }

    private void abrirSelectorImagen() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intent, CODIGO_SELECCIONAR_IMAGEN);
    }

    private void mostrarSelectorFecha() {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) ->
                etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)),
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void mostrarSelectorHora() {
        Calendar calendario = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this, (view, hourOfDay, minute) ->
                etHora.setText(String.format("%02d:%02d", hourOfDay, minute)),
                calendario.get(Calendar.HOUR_OF_DAY),
                calendario.get(Calendar.MINUTE),
                true);
        dialog.show();
    }

    private void guardarPelicula() {
        String nombre = etNombre.getText().toString().trim();
        String director = etDirector.getText().toString().trim();
        String protagonista = etProtagonista.getText().toString().trim();
        String antagonista = etAntagonista.getText().toString().trim();
        String genero = etGenero.getText().toString().trim();
        String fecha = etFecha.getText().toString().trim();
        String hora = etHora.getText().toString().trim();
        String duracionStr = etDuracion.getText().toString().trim();
        String sinopsis = etSinopsis.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(director) ||
                TextUtils.isEmpty(protagonista) || TextUtils.isEmpty(antagonista) ||
                TextUtils.isEmpty(genero) || TextUtils.isEmpty(fecha) ||
                TextUtils.isEmpty(hora) || TextUtils.isEmpty(duracionStr)) {
            Toast.makeText(this, R.string.rellena_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        int duracion;
        try {
            duracion = Integer.parseInt(duracionStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.duracion_invalida, Toast.LENGTH_SHORT).show();
            return;
        }

        Pelicula nuevaPelicula = new Pelicula(
                nombre,
                sinopsis,
                director,
                protagonista,
                antagonista,
                genero,
                spTipo.getSelectedItem().toString(),
                fecha,
                hora,
                duracion,
                cbVista.isChecked(),
                cbFavorito.isChecked(),
                rbNota.getRating(),
                R.drawable.palomitas_logo,
                imagenSeleccionadaUri);

        db.appDao().insertPelicula(nuevaPelicula);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_SELECCIONAR_IMAGEN && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri == null) {
                Toast.makeText(this, R.string.imagen_no_disponible, Toast.LENGTH_SHORT).show();
                return;
            }
            int flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            try {
                if ((data.getFlags() & flags) == flags) {
                    getContentResolver().takePersistableUriPermission(uri, flags);
                }
            } catch (SecurityException ignored) {
                // Algunas apps no ofrecen permiso persistente; la vista previa sigue funcionando en la sesion actual.
            }
            imagenSeleccionadaUri = uri.toString();
            imgPreview.setImageURI(uri);
        }
    }
}

