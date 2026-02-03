package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class DetallesPeliActivity extends AppCompatActivity{
    ImageView imgContactDetail;
    TextView tvDirector, tvNombre, tvProta, tvAntagonista, tvGenero, tvFechaSalida, tvDuracion, tvFavorita, tvVista, tvNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_peli_layout);
        imgContactDetail = findViewById(R.id.imgPeliDetail);
        tvDirector = findViewById(R.id.txtDirectorDetail);
        tvNombre = findViewById(R.id.txtNombreDetail);
        tvProta = findViewById(R.id.txtProtagonista);
        tvAntagonista = findViewById(R.id.txtAntagonista);
        tvGenero = findViewById(R.id.txtGenero);
        tvFechaSalida = findViewById(R.id.txtFechaEstreno);
        tvDuracion = findViewById(R.id.txtDuracion);
        tvFavorita = findViewById(R.id.txtFav);
        tvVista = findViewById(R.id.txtVista);
        tvNota = findViewById(R.id.txtNota);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            imgContactDetail.setImageResource(extras.getInt("imageId"));
            tvNombre.setText(extras.getString("nombre"));
            tvDirector.setText("Dirigida por: "+extras.getString("director"));
            tvProta.setText("Protagonista: "+extras.getString("protagonista"));
            tvAntagonista.setText("Antagonista: "+extras.getString("antagonista"));
            tvGenero.setText("Género: "+extras.getString("genero"));
            tvFechaSalida.setText("Fecha de salida: "+extras.getString("fecha"));
            tvDuracion.setText("Duracion: "+extras.getInt("duracion")+" minutos");
            if(extras.getBoolean("fav?")){
                tvFavorita.setText("Está en tu lista de favoritos");
            }else{
                tvFavorita.setText("No está en tu lista de favoritos");
            }

            if(extras.getBoolean("vista?")){
                tvVista.setText("Has visto esta película");
            }else{
                tvVista.setText("No has visto esta película");
            }
            tvNota.setText("Has valorado esta película con un: "+extras.getInt("nota"));
        }
    }
}
