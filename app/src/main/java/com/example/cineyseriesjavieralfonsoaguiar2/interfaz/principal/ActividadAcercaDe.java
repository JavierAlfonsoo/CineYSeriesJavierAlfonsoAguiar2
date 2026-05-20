package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ActividadAcercaDe extends ActividadBaseMenuLateral {

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.acerca_de_layout);
        establecerTituloMenuLateral(R.string.acercaDe);
    };
}

