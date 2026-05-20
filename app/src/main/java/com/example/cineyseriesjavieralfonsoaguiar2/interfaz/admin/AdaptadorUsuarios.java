package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Usuario;
import java.util.List;

public class AdaptadorUsuarios extends RecyclerView.Adapter<AdaptadorUsuarios.SoporteVistaUsuario> {

    private List<Usuario> listaUsuarios;
    private EscuchadorClicUsuario escuchador;

    // Interfaz para gestionar el click desde la Actividad
    public interface EscuchadorClicUsuario {
        void alPulsarEliminar(Usuario usuario);
    }

    public AdaptadorUsuarios(List<Usuario> listaUsuarios, EscuchadorClicUsuario escuchador) {
        this.listaUsuarios = listaUsuarios;
        this.escuchador = escuchador;
    }

    @NonNull
    @Override
    public SoporteVistaUsuario onCreateViewHolder(@NonNull ViewGroup padre, int tipoVista) {
        View vista = LayoutInflater.from(padre.getContext()).inflate(R.layout.usuario_item, padre, false);
        return new SoporteVistaUsuario(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull SoporteVistaUsuario soporte, int position) {
        Usuario usuario = listaUsuarios.get(position);
        soporte.tvNombre.setText(usuario.nombre);
        soporte.tvCorreo.setText(usuario.correo);

        // Configurar el boton de eliminar
        soporte.btnDelete.setOnClickListener(v -> escuchador.alPulsarEliminar(usuario));
    }

    @Override
    public int getItemCount() {
        return listaUsuarios.size();
    }

    public static class SoporteVistaUsuario extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCorreo;
        ImageButton btnDelete;

        public SoporteVistaUsuario(@NonNull View vistaElemento) {
            super(vistaElemento);
            tvNombre = vistaElemento.findViewById(R.id.tvNombreUser);
            tvCorreo = vistaElemento.findViewById(R.id.tvCorreoUser);
            btnDelete = vistaElemento.findViewById(R.id.btnDeleteUser);
        }
    }
}

