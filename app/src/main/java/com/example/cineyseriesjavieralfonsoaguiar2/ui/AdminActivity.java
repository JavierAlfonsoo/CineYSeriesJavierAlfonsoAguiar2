package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.UsuariosAdapter;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.Usuario;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private UsuariosAdapter adapter;
    private AppDatabase db;
    private List<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        recyclerView = findViewById(R.id.rvUsers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(this);
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        listaUsuarios = db.appDao().getAllUsers();

        adapter = new UsuariosAdapter(listaUsuarios, user -> {
            // Acción al pulsar el botón de borrar
            eliminarUsuario(user);
        });
        recyclerView.setAdapter(adapter);
    }

    private void eliminarUsuario(Usuario user) {
        // 1. Evitar borrarse a uno mismo (opcional pero recomendado)
        // Puedes añadir un check aquí si quieres.

        // 2. Borrar de BBDD
        db.appDao().deleteUser(user);

        // 3. Actualizar la lista visualmente
        listaUsuarios.remove(user);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Usuario eliminado: " + user.nombre, Toast.LENGTH_SHORT).show();

        lanzarNotificacionBorrado();
    }

    private void lanzarNotificacionBorrado() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "admin_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Admin Channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_menu_delete)
                .setContentTitle("Registro Eliminado")
                .setContentText("Un administrador ha eliminado un usuario.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Permiso check
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        manager.notify(200, builder.build());
    }
}