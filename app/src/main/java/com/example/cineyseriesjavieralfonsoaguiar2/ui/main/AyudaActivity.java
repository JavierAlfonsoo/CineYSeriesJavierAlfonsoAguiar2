package com.example.cineyseriesjavieralfonsoaguiar2.ui.main;

import android.os.Bundle;
import android.content.Intent;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class AyudaActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ayuda_layout);
        setDrawerTitle(R.string.ayuda);
        findViewById(R.id.btnVerVideo).setOnClickListener(v ->
                startActivity(new Intent(this, VideoActivity.class)));
    };
}

