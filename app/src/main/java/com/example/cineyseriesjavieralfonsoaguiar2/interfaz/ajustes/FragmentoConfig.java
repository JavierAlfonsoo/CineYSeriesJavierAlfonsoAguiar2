package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.ajustes;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class FragmentoConfig extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle estadoGuardado, String rootKey) {
        // Vinculamos el XML que creamos antes
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
