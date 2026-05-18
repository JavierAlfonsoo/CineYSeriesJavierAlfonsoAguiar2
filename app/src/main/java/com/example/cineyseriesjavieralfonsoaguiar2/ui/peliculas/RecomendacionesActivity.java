package com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.ui.main.BaseDrawerActivity;

public class RecomendacionesActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
        setDrawerTitle(R.string.recomendaciones);
    }
}

