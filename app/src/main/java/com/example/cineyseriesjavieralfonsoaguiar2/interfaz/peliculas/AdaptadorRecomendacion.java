package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas;

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
import com.example.cineyseriesjavieralfonsoaguiar2.datos.remoto.dto.ResultadoBusquedaTvMaze;

import java.util.List;

public class AdaptadorRecomendacion extends RecyclerView.Adapter<AdaptadorRecomendacion.SoporteVistaRecomendacion> {

    public interface EscuchadorGuardar {
        void alGuardar(ResultadoBusquedaTvMaze recomendacion);
    }

    private final List<ResultadoBusquedaTvMaze> recomendaciones;
    private final EscuchadorGuardar escuchador;

    public AdaptadorRecomendacion(List <ResultadoBusquedaTvMaze> recomendaciones, EscuchadorGuardar escuchador) {
        this.recomendaciones = recomendaciones;
        this.escuchador = escuchador;
    }

    public void actualizar(List<ResultadoBusquedaTvMaze> nuevasRecomendaciones) {
        recomendaciones.clear();
        recomendaciones.addAll(nuevasRecomendaciones);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SoporteVistaRecomendacion onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = LayoutInflater.from(padre.getContext())
                .inflate(R.layout.item_recomendacion, padre, false);
        return new SoporteVistaRecomendacion(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull SoporteVistaRecomendacion soporte, int position) {
        ResultadoBusquedaTvMaze recomendacion = recomendaciones.get(position);
        if (recomendacion.serie == null) {
            return;
        }

        soporte.tvTitulo.setText(recomendacion.serie.nombre);
        soporte.tvGenero.setText(recomendacion.serie.generosComoTexto());
        soporte.tvSinopsis.setText(recomendacion.serie.resumenLimpio());

        String imagen = recomendacion.serie.imagenMedia();
        if (TextUtils.isEmpty(imagen)) {
            soporte.imgPoster.setImageResource(R.drawable.palomitas_logo);
        } else {
            Glide.with(soporte.itemView)
                    .load(imagen)
                    .placeholder(R.drawable.palomitas_logo)
                    .error(R.drawable.palomitas_logo)
                    .into(soporte.imgPoster);
        }

        soporte.btnGuardar.setOnClickListener(v -> escuchador.alGuardar(recomendacion));
    }

    @Override
    public int getItemCount() {
        return recomendaciones.size();
    }

    static class SoporteVistaRecomendacion extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitulo;
        TextView tvGenero;
        TextView tvSinopsis;
        Button btnGuardar;

        SoporteVistaRecomendacion(@NonNull View vistaElemento) {
            super(vistaElemento);
            imgPoster = vistaElemento.findViewById(R.id.imgPosterRecomendacion);
            tvTitulo = vistaElemento.findViewById(R.id.tvTituloRecomendacion);
            tvGenero = vistaElemento.findViewById(R.id.tvGeneroRecomendacion);
            tvSinopsis = vistaElemento.findViewById(R.id.tvSinopsisRecomendacion);
            btnGuardar = vistaElemento.findViewById(R.id.btnGuardarRecomendacion);
        }
    }
}
