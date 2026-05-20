package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class FragmentoDialogoConfirmarEliminacion extends DialogFragment {

    public interface EscuchadorConfirmarEliminacion {
        void alConfirmarEliminacion();
    }

    public static FragmentoDialogoConfirmarEliminacion nuevaInstancia(String titulo) {
        FragmentoDialogoConfirmarEliminacion fragment = new FragmentoDialogoConfirmarEliminacion();
        Bundle args = new Bundle();
        args.putString("titulo", titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle estadoGuardado) {
        String titulo = getArguments() != null ? getArguments().getString("titulo") : "";
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirmar_borrado)
                .setMessage(getString(R.string.confirmar_borrado_mensaje, titulo))
                .setPositiveButton(R.string.eliminar, (dialog, which) ->
                        ((EscuchadorConfirmarEliminacion) requireActivity()).alConfirmarEliminacion())
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}

