package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas;

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
import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadBaseMenuLateral;

import java.util.Calendar;

public class ActividadNuevaPelicula extends ActividadBaseMenuLateral {

    private static final int CODIGO_SELECCIONAR_IMAGEN = 50;

    private EditText etNombre, etDirector, etProtagonista, etAntagonista, etGenero, etFecha, etHora, etDuracion, etSinopsis;
    private Spinner spTipo;
    private RatingBar rbNota;
    private CheckBox cbVista, cbFavorito;
    private BaseDatosApp baseDatos;
    private ImageView imgPreview;
    private String imagenSeleccionadaUri;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.nueva_pelicula_layout);
        establecerTituloMenuLateral(R.string.nuevaPelicula);

        baseDatos = BaseDatosApp.obtenerInstancia(this);
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

        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,
                R.array.tipo_contenido, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipo.setAdapter(adaptador);

        etFecha.setOnClickListener(v -> mostrarSelectorFecha());
        etHora.setOnClickListener(v -> mostrarSelectorHora());
        btnSeleccionarImagen.setOnClickListener(v -> abrirSelectorImagen());
        btnAceptar.setOnClickListener(v -> guardarPelicula());
    }

    private void abrirSelectorImagen() {
        Intent intencion = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intencion.addCategory(Intent.CATEGORY_OPENABLE);
        intencion.setType("image/*");
        intencion.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intencion.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivityForResult(intencion, CODIGO_SELECCIONAR_IMAGEN);
    }

    private void mostrarSelectorFecha() {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, (View, year, month, dayOfMonth) ->
                etFecha.setText(String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)),
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void mostrarSelectorHora() {
        Calendar calendario = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(this, (View, hourOfDay, minute) ->
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

        baseDatos.daoApp().insertarPelicula(nuevaPelicula);
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int codigoSolicitud, int codigoResultado, Intent datos) {
        super.onActivityResult(codigoSolicitud, codigoResultado, datos);
        if (codigoSolicitud == CODIGO_SELECCIONAR_IMAGEN && codigoResultado == RESULT_OK && datos != null) {
            Uri uri = datos.getData();
            if (uri == null) {
                Toast.makeText(this, R.string.imagen_no_disponible, Toast.LENGTH_SHORT).show();
                return;
            }
            int banderas = Intent.FLAG_GRANT_READ_URI_PERMISSION;
            try {
                if ((datos.getFlags() & banderas) == banderas) {
                    getContentResolver().takePersistableUriPermission(uri, banderas);
                }
            } catch (SecurityException ignored) {
                // Algunas apps no ofrecen permiso persistente; la vista previa sigue funcionando en la sesion actual.
            }
            imagenSeleccionadaUri = uri.toString();
            imgPreview.setImageURI(uri);
        }
    }
}

