package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal;

import android.os.Bundle;
import android.content.Intent;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ActividadAyuda extends ActividadBaseMenuLateral {

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.ayuda_layout);
        establecerTituloMenuLateral(R.string.ayuda);
        findViewById(R.id.btnVerVideo).setOnClickListener(v ->
                startActivity(new Intent(this, ActividadVideo.class)));
    };
}

