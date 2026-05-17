package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.os.Bundle;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class DetallesPeliActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_peli_layout);
        setDrawerTitle(R.string.detalles);

        ImageView imgContactDetail = findViewById(R.id.imgPeliDetail);
        TextView tvDirector = findViewById(R.id.txtDirectorDetail);
        TextView tvNombre = findViewById(R.id.txtNombreDetail);
        TextView tvProta = findViewById(R.id.txtProtagonista);
        TextView tvAntagonista = findViewById(R.id.txtAntagonista);
        TextView tvGenero = findViewById(R.id.txtGenero);
        TextView tvTipo = findViewById(R.id.txtTipo);
        TextView tvFechaSalida = findViewById(R.id.txtFechaEstreno);
        TextView tvHora = findViewById(R.id.txtHora);
        TextView tvDuracion = findViewById(R.id.txtDuracion);
        TextView tvFavorita = findViewById(R.id.txtFav);
        TextView tvVista = findViewById(R.id.txtVista);
        TextView tvNota = findViewById(R.id.txtNota);
        TextView tvSinopsis = findViewById(R.id.txtSinopsis);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String imageUri = extras.getString("imageUri");
            if (imageUri != null && !imageUri.isEmpty()) {
                imgContactDetail.setImageURI(Uri.parse(imageUri));
            } else {
                imgContactDetail.setImageResource(extras.getInt("imageId"));
            }
            tvNombre.setText(extras.getString("nombre"));
            tvDirector.setText(getString(R.string.detalle_director, extras.getString("director")));
            tvProta.setText(getString(R.string.detalle_protagonista, extras.getString("protagonista")));
            tvAntagonista.setText(getString(R.string.detalle_antagonista, extras.getString("antagonista")));
            tvGenero.setText(getString(R.string.detalle_genero, extras.getString("genero")));
            tvTipo.setText(getString(R.string.detalle_tipo, extras.getString("tipo")));
            tvFechaSalida.setText(getString(R.string.detalle_fecha, extras.getString("fecha")));
            tvHora.setText(getString(R.string.detalle_hora, extras.getString("hora")));
            tvDuracion.setText(getString(R.string.detalle_duracion, extras.getInt("duracion")));
            tvFavorita.setText(extras.getBoolean("fav?")
                    ? R.string.detalle_favorita_si
                    : R.string.detalle_favorita_no);
            tvVista.setText(extras.getBoolean("vista?")
                    ? R.string.detalle_vista_si
                    : R.string.detalle_vista_no);
            tvNota.setText(getString(R.string.detalle_nota, extras.getFloat("nota")));
            tvSinopsis.setText(getString(R.string.detalle_sinopsis, extras.getString("sinopsis")));
        }
    }
}
