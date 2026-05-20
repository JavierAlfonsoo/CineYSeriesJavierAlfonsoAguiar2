package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.autenticacion.ActividadLogin;

public class ActividadInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_splash);

        ImageView imageView = findViewById(R.id.ivGifSplash);

        Glide.with(this)
                .load(R.drawable.splash_gato)
                .into(imageView);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intencion = new Intent(ActividadInicio.this, ActividadLogin.class);
                startActivity(intencion);
                finish();
            }
        }, 3000); //
    }
}

