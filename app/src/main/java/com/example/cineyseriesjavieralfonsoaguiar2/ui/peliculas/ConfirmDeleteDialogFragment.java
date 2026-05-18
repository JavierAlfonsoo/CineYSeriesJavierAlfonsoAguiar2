package com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ConfirmDeleteDialogFragment extends DialogFragment {

    public interface ConfirmDeleteListener {
        void onDeleteConfirmed();
    }

    public static ConfirmDeleteDialogFragment newInstance(String titulo) {
        ConfirmDeleteDialogFragment fragment = new ConfirmDeleteDialogFragment();
        Bundle args = new Bundle();
        args.putString("titulo", titulo);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String titulo = getArguments() != null ? getArguments().getString("titulo") : "";
        return new AlertDialog.Builder(requireContext())
                .setTitle(R.string.confirmar_borrado)
                .setMessage(getString(R.string.confirmar_borrado_mensaje, titulo))
                .setPositiveButton(R.string.eliminar, (dialog, which) ->
                        ((ConfirmDeleteListener) requireActivity()).onDeleteConfirmed())
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }
}

