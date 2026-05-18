package com.example.cineyseriesjavieralfonsoaguiar2.ui.main;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class AcercaDeActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acerca_de_layout);
        setDrawerTitle(R.string.acercaDe);
    };
}

