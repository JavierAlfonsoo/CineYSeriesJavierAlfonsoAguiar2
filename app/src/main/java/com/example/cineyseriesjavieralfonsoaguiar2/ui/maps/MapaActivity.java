package com.example.cineyseriesjavieralfonsoaguiar2.ui.maps;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.main.BaseDrawerActivity;

public class MapaActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        setDrawerTitle(R.string.mapa);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mapa_container, new MapaFragment())
                    .commit();
        }
    }
}

