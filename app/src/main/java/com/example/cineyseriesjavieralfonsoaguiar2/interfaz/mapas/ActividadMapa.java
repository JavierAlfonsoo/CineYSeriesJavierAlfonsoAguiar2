package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.mapas;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadBaseMenuLateral;

public class ActividadMapa extends ActividadBaseMenuLateral {

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_mapa);
        establecerTituloMenuLateral(R.string.mapa);
        if (estadoGuardado == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mapa_container, new FragmentoMapa())
                    .commit();
        }
    }
}

