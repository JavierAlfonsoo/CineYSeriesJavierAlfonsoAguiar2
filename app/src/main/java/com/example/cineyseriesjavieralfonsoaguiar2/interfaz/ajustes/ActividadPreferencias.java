package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.ajustes;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadBaseMenuLateral;

public class ActividadPreferencias extends ActividadBaseMenuLateral {

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_settings);
        establecerTituloMenuLateral(R.string.ajustes);

        if (estadoGuardado == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new FragmentoConfig())
                    .commit();
        }
    }
}

