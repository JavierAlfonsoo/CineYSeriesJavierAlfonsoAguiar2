package com.example.cineyseriesjavieralfonsoaguiar2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.Usuario;
import java.util.List;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.UserViewHolder> {

    private List<Usuario> userList;
    private OnUserClickListener listener;

    // Interfaz para gestionar el click desde la Actividad
    public interface OnUserClickListener {
        void onDeleteClick(Usuario user);
    }

    public UsuariosAdapter(List<Usuario> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Usuario user = userList.get(position);
        holder.tvNombre.setText(user.nombre);
        holder.tvCorreo.setText(user.correo);

        // Configurar el botón de eliminar
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre, tvCorreo;
        ImageButton btnDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreUser);
            tvCorreo = itemView.findViewById(R.id.tvCorreoUser);
            btnDelete = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}