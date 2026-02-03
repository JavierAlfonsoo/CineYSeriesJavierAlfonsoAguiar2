package com.example.cineyseriesjavieralfonsoaguiar2;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.bd.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText etCorreo, etPass;
    private Button btnLogin, btnRegister;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        // iniciamos la base de datos
        db = AppDatabase.getInstance(this);

        etCorreo = findViewById(R.id.etCorreo);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // Lógica LOGIN
        btnLogin.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String pass = etPass.getText().toString();

            Usuario user = db.appDao().login(correo, pass);
            if (user != null) {
                // si el login es correcto pasamos al main con los datos del usuario
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("Nombre de usuario", user.nombre); // Pasamos datos (Requisito 3)
                intent.putExtra("IS_ADMIN", user.esAdmin); // Para cambiar menús
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        // Lógica REGISTRO (Simplificado: crea un usuario admin de prueba)
        btnRegister.setOnClickListener(v -> {
            String correo = etCorreo.getText().toString();
            String pass = etPass.getText().toString();

            // Creamos usuario (Ej: Admin con teléfono para las pruebas del SMS)
            Usuario nuevoUser = new Usuario("Javier", correo, pass, "666777888", true);
            db.appDao().insertUser(nuevoUser);

            lanzarNotificacionRegistro();
            Toast.makeText(this, "Usuario Registrado!", Toast.LENGTH_SHORT).show();
        });
    }

    // Envio de la notificacion al crear un nuevo usuario
    private void lanzarNotificacionRegistro() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "users_channel";

        if (Build.VERSION_CODES.O <= Build.VERSION.SDK_INT) {
            NotificationChannel channel = new NotificationChannel(channelId, "Usuarios", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bienvenido")
                .setContentText("Tu cuenta ha sido creada correctamente")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(1, builder.build());
    }
}