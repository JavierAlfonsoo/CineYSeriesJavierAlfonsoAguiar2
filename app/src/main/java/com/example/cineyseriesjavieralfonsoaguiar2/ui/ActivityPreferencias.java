package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.os.Bundle;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ActivityPreferencias extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setDrawerTitle(R.string.ajustes);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new ConfigFragment())
                    .commit();
        }
    }
}
