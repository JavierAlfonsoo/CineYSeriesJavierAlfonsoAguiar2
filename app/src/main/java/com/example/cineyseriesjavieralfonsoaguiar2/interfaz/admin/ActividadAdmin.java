package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.admin;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Usuario;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadBaseMenuLateral;

import java.util.List;

public class ActividadAdmin extends ActividadBaseMenuLateral {

    private RecyclerView RecyclerView;
    private AdaptadorUsuarios adaptador;
    private BaseDatosApp baseDatos;
    private List<Usuario> listaUsuarios;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_admin);
        establecerTituloMenuLateral(R.string.panelAdmin);

        RecyclerView = findViewById(R.id.rvUsers);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));

        baseDatos = BaseDatosApp.obtenerInstancia(this);
        cargarUsuarios();
    }

    private void cargarUsuarios() {
        listaUsuarios = baseDatos.daoApp().obtenerTodosUsuarios();

        adaptador = new AdaptadorUsuarios(listaUsuarios, usuario -> {
            // Accion al pulsar el boton de borrar
            eliminarUsuario(usuario);
        });
        RecyclerView.setAdapter(adaptador);
    }

    private void eliminarUsuario(Usuario usuario) {
        // 1. Evitar borrarse a uno mismo (opcional pero recomendado)
        // Puedes anadir un check aqui si quieres.

        // 2. Borrar de BBDD
        baseDatos.daoApp().eliminarUsuario(usuario);

        // 3. Actualizar la lista visualmente
        listaUsuarios.remove(usuario);
        adaptador.notifyDataSetChanged();

        Toast.makeText(this, "Usuario eliminado: " + usuario.nombre, Toast.LENGTH_SHORT).show();

        lanzarNotificacionBorrado();
    }

    private void lanzarNotificacionBorrado() {
        NotificationManager gestor = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String idCanal = "admin_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(idCanal, "Admin canal", NotificationManager.IMPORTANCE_HIGH);
            gestor.createNotificationChannel(canal);
        }

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(this, idCanal)
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

        gestor.notify(200, constructor.build());
    }
}

