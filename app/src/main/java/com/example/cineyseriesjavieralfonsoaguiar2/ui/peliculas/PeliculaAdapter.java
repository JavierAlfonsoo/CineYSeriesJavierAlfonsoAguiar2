package com.example.cineyseriesjavieralfonsoaguiar2.ui.peliculas;

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
import com.example.cineyseriesjavieralfonsoaguiar2.data.entities.Pelicula;

import java.util.ArrayList;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private final ArrayList<Pelicula> listaPelis;
    private final Context context;
    private final OnPeliculaActionListener listener;

    public interface OnPeliculaActionListener {
        void onDelete(Pelicula pelicula);
        void onFavorite(Pelicula pelicula);
    }

    public PeliculaAdapter(Context context, ArrayList<Pelicula> listaPelis, OnPeliculaActionListener listener) {
        this.context = context;
        this.listaPelis = listaPelis;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPeli;
        TextView txtNombre, txtGenero, txtDuracion;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPeli = itemView.findViewById(R.id.imgPeliSerie);
            txtNombre = itemView.findViewById(R.id.tvNombre);
            txtGenero = itemView.findViewById(R.id.tvGenero);
            txtDuracion = itemView.findViewById(R.id.tvDuracion);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pelicula peli = listaPelis.get(position);
        if (peli.imagenUri != null && !peli.imagenUri.isEmpty()) {
            Glide.with(context)
                    .load(Uri.parse(peli.imagenUri))
                    .placeholder(peli.imagenResId)
                    .error(peli.imagenResId)
                    .into(holder.imgPeli);
        } else {
            holder.imgPeli.setImageResource(peli.imagenResId);
        }
        holder.txtNombre.setText(peli.titulo);
        holder.txtGenero.setText(peli.genero + " - " + peli.tipo);
        holder.txtDuracion.setText(peli.duracion + " min - " + peli.rating + "/5");

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetallesPeliActivity.class);
            intent.putExtra("imageId", peli.imagenResId);
            intent.putExtra("imageUri", peli.imagenUri);
            intent.putExtra("director", peli.director);
            intent.putExtra("nombre", peli.titulo);
            intent.putExtra("protagonista", peli.protagonista);
            intent.putExtra("antagonista", peli.antagonista);
            intent.putExtra("genero", peli.genero);
            intent.putExtra("tipo", peli.tipo);
            intent.putExtra("fecha", peli.fechaEstreno);
            intent.putExtra("hora", peli.horaRecordatorio);
            intent.putExtra("duracion", peli.duracion);
            intent.putExtra("fav?", peli.favorito);
            intent.putExtra("vista?", peli.visto);
            intent.putExtra("nota", peli.rating);
            intent.putExtra("sinopsis", peli.sinopsis);
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, v);
            popupMenu.getMenu().add(0, 1, 0, R.string.marcar_favorita);
            popupMenu.getMenu().add(0, 2, 1, R.string.eliminar);
            popupMenu.setOnMenuItemClickListener(item -> gestionarAccionContextual(item, peli));
            popupMenu.show();
            return true;
        });
    }

    private boolean gestionarAccionContextual(MenuItem item, Pelicula peli) {
        if (item.getItemId() == 1) {
            listener.onFavorite(peli);
            Toast.makeText(context, R.string.favorita_actualizada, Toast.LENGTH_SHORT).show();
            return true;
        }
        if (item.getItemId() == 2) {
            listener.onDelete(peli);
            return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return listaPelis.size();
    }
}

