package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.autenticacion;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Usuario;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadPrincipal;

public class ActividadLogin extends AppCompatActivity {

    private static final int CODIGO_PERMISO_SMS = 300;
    private EditText etCorreo, etPass;
    private Button btnLogin, btnRegister;
    private Switch switchEsAdmin;
    private BaseDatosApp baseDatos;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.login_layout);

        // iniciamos la base de datos
        baseDatos = BaseDatosApp.obtenerInstancia(this);
        pedirPermisoSms();

        etCorreo = findViewById(R.id.etCorreo);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString().trim();
            String pass = etPass.getText().toString();

            if (!ValidadorCorreo.isValid(correo)) {
                etCorreo.setError(getString(R.string.correo_invalido));
                etCorreo.requestFocus();
                return;
            }

            Usuario usuario = baseDatos.daoApp().iniciarSesion(correo, pass);
            if (usuario != null) {
                // si el iniciarSesion es correcto pasamos al main con los datos del usuario
                Intent intencion = new Intent(ActividadLogin.this, ActividadPrincipal.class);
                intencion.putExtra("Nombre de usuario", usuario.nombre); // Pasamos datos (Requisito 3)
                intencion.putExtra("IS_ADMIN", usuario.esAdmin); // Para cambiar menus
                startActivity(intencion);
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        // REGISTRO
        btnRegister.setOnClickListener(v -> {
            // Navegar a la pantalla de registro
            Intent intencion = new Intent(ActividadLogin.this, ActividadRegistro.class);
            startActivity(intencion);
        });
    }

    private void pedirPermisoSms() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.SEND_SMS},
                    CODIGO_PERMISO_SMS);
        }
    }

    // Envio de la notificacion al crear un nuevo usuario
    private void lanzarNotificacionRegistro() {
        NotificationManager gestor = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String idCanal = "users_channel";

        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            NotificationChannel canal = new NotificationChannel(idCanal, "Usuarios", NotificationManager.IMPORTANCE_DEFAULT);
            gestor.createNotificationChannel(canal);
        }

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(this, idCanal)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bienvenido")
                .setContentText("Tu cuenta ha sido creada correctamente")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        gestor.notify(1, constructor.build());
    }
}

