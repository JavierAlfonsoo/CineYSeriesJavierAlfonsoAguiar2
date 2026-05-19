package com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.data.remote.dto.TvMazeSearchResult;

import java.util.List;

public class RecomendacionAdapter extends RecyclerView.Adapter<RecomendacionAdapter.ViewHolder> {

    public interface OnGuardarClickListener {
        void onGuardar(TvMazeSearchResult recomendacion);
    }

    private final List<TvMazeSearchResult> recomendaciones;
    private final OnGuardarClickListener listener;

    public RecomendacionAdapter(List<TvMazeSearchResult> recomendaciones, OnGuardarClickListener listener) {
        this.recomendaciones = recomendaciones;
        this.listener = listener;
    }

    public void actualizar(List<TvMazeSearchResult> nuevasRecomendaciones) {
        recomendaciones.clear();
        recomendaciones.addAll(nuevasRecomendaciones);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recomendacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TvMazeSearchResult recomendacion = recomendaciones.get(position);
        if (recomendacion.show == null) {
            return;
        }

        holder.tvTitulo.setText(recomendacion.show.name);
        holder.tvGenero.setText(recomendacion.show.generosComoTexto());
        holder.tvSinopsis.setText(recomendacion.show.resumenLimpio());

        String imagen = recomendacion.show.imagenMedia();
        if (TextUtils.isEmpty(imagen)) {
            holder.imgPoster.setImageResource(R.drawable.palomitas_logo);
        } else {
            Glide.with(holder.itemView)
                    .load(imagen)
                    .placeholder(R.drawable.palomitas_logo)
                    .error(R.drawable.palomitas_logo)
                    .into(holder.imgPoster);
        }

        holder.btnGuardar.setOnClickListener(v -> listener.onGuardar(recomendacion));
    }

    @Override
    public int getItemCount() {
        return recomendaciones.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitulo;
        TextView tvGenero;
        TextView tvSinopsis;
        Button btnGuardar;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPosterRecomendacion);
            tvTitulo = itemView.findViewById(R.id.tvTituloRecomendacion);
            tvGenero = itemView.findViewById(R.id.tvGeneroRecomendacion);
            tvSinopsis = itemView.findViewById(R.id.tvSinopsisRecomendacion);
            btnGuardar = itemView.findViewById(R.id.btnGuardarRecomendacion);
        }
    }
}
