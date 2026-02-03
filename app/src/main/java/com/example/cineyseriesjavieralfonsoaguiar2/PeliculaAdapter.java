package com.example.cineyseriesjavieralfonsoaguiar2;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cineyseriesjavieralfonsoaguiar2.DetallesPeliActivity;
import com.example.cineyseriesjavieralfonsoaguiar2.R;

import java.util.ArrayList;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private ArrayList<com.example.cineyseriesjavieralfonsoaguiar2.Pelicula> listaPelis;
    private Context context;

    //Constructor del adaptador
    public PeliculaAdapter (Context context, ArrayList<com.example.cineyseriesjavieralfonsoaguiar2.Pelicula> listaPelis){
        this.context = context;
        this.listaPelis = listaPelis;
    }

    //Creamos el viewholder para las tarjetas del main
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPeli;
        TextView txtNombre, txtGenero;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPeli = itemView.findViewById(R.id.imgPeliSerie);
            txtNombre = itemView.findViewById(R.id.tvNombre);
            txtGenero = itemView.findViewById(R.id.tvGenero);
        }
    }

    @Override
    public PeliculaAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeliculaAdapter.ViewHolder holder, int position) {
        com.example.cineyseriesjavieralfonsoaguiar2.Pelicula peli = listaPelis.get(position);
        holder.imgPeli.setImageResource(peli.getImagenId());
        holder.txtNombre.setText(peli.getNombre());
        holder.txtGenero.setText(peli.getGenero());

        //Con clic corto muestro el detalle de la película
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetallesPeliActivity.class);
                intent.putExtra("imageId", peli.getImagenId());
                intent.putExtra("director", peli.getDirector());
                intent.putExtra("nombre", peli.getNombre());
                intent.putExtra("protagonista", peli.getProtagonista());
                intent.putExtra("antagonista", peli.getAntagonista());
                intent.putExtra("genero", peli.getGenero());
                intent.putExtra("fecha", peli.getFechaEstreno());
                intent.putExtra("duracion", peli.getDuracionMin());
                intent.putExtra("fav?", peli.isFavorito());
                intent.putExtra("vista?", peli.isVista());
                intent.putExtra("nota", peli.getNota());
                context.startActivity(intent);
            }
        });

        //Con el clic largo borro (de momento)
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listaPelis.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, peli.getNombre() + " ha sido eliminada", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPelis.size();
    }
}
