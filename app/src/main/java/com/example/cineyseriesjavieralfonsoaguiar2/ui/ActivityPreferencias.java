package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ActivityPreferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings_container, new ConfigFragment())
                    .commit();
        }

        // flecha atras en la barra superior
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Preferencias");
        }
    }

    // Para que funcione la flecha de atrás
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}