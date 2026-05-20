package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.peliculas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Pelicula;

import java.util.ArrayList;

public class AdaptadorPelicula extends RecyclerView.Adapter<AdaptadorPelicula.SoporteVistaPelicula> {

    private final ArrayList<Pelicula> listaPelis;
    private final Context contexto;
    private final EscuchadorAccionPelicula escuchador;

    public interface EscuchadorAccionPelicula {
        void alEliminar(Pelicula pelicula);
        void alFavorito(Pelicula pelicula);
    }

    public AdaptadorPelicula(Context contexto, ArrayList<Pelicula> listaPelis, EscuchadorAccionPelicula escuchador) {
        this.contexto = contexto;
        this.listaPelis = listaPelis;
        this.escuchador = escuchador;
    }

    public static class SoporteVistaPelicula extends RecyclerView.ViewHolder {
        ImageView imgPeli;
        TextView txtNombre, txtGenero, txtDuracion;

        public SoporteVistaPelicula(View vistaElemento) {
            super(vistaElemento);
            imgPeli = vistaElemento.findViewById(R.id.imgPeliSerie);
            txtNombre = vistaElemento.findViewById(R.id.tvNombre);
            txtGenero = vistaElemento.findViewById(R.id.tvGenero);
            txtDuracion = vistaElemento.findViewById(R.id.tvDuracion);
        }
    }

    @Override
    public SoporteVistaPelicula onCreateViewHolder(ViewGroup padre, int tipoVista) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.layout_card, padre, false);
        return new SoporteVistaPelicula(vista);
    }

    @Override
    public void onBindViewHolder(SoporteVistaPelicula soporte, int position) {
        Pelicula peli = listaPelis.get(position);
        if (peli.imagenUri != null && !peli.imagenUri.isEmpty()) {
            Glide.with(contexto)
                    .load(Uri.parse(peli.imagenUri))
                    .placeholder(peli.imagenResId)
                    .error(peli.imagenResId)
                    .into(soporte.imgPeli);
        } else {
            soporte.imgPeli.setImageResource(peli.imagenResId);
        }
        soporte.txtNombre.setText(peli.titulo);
        soporte.txtGenero.setText(peli.genero + " - " + peli.tipo);
        soporte.txtDuracion.setText(peli.duracion + " min - " + peli.valoracion + "/5");

        soporte.itemView.setOnClickListener(v -> {
            Intent intencion = new Intent(contexto, ActividadDetallesPeli.class);
            intencion.putExtra("imageId", peli.imagenResId);
            intencion.putExtra("uriImagen", peli.imagenUri);
            intencion.putExtra("director", peli.director);
            intencion.putExtra("nombre", peli.titulo);
            intencion.putExtra("protagonista", peli.protagonista);
            intencion.putExtra("antagonista", peli.antagonista);
            intencion.putExtra("genero", peli.genero);
            intencion.putExtra("tipo", peli.tipo);
            intencion.putExtra("fecha", peli.fechaEstreno);
            intencion.putExtra("hora", peli.horaRecordatorio);
            intencion.putExtra("duracion", peli.duracion);
            intencion.putExtra("fav?", peli.favorito);
            intencion.putExtra("vista?", peli.visto);
            intencion.putExtra("nota", peli.valoracion);
            intencion.putExtra("sinopsis", peli.sinopsis);
            contexto.startActivity(intencion);
        });

        soporte.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(contexto, v);
            popupMenu.getMenu().add(0, 1, 0, R.string.marcar_favorita);
            popupMenu.getMenu().add(0, 2, 1, R.string.eliminar);
            popupMenu.setOnMenuItemClickListener(item -> gestionarAccionContextual(item, peli));
            popupMenu.show();
            return true;
        });
    }

    private boolean gestionarAccionContextual(MenuItem item, Pelicula peli) {
        if (item.getItemId() == 1) {
            escuchador.alFavorito(peli);
            Toast.makeText(contexto, R.string.favorita_actualizada, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == 2) {
            escuchador.alEliminar(peli);
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return listaPelis.size();
    }
}

