package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ConfigFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Vinculamos el XML que creamos antes
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}