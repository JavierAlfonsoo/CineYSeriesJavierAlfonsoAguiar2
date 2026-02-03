package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.Usuario;

public class ActivityRegistro extends AppCompatActivity {

    private static final int CODIGO_PERMISO_NOTIFICACION = 100;
    private EditText etNombre, etCorreo, etPass, etTelefono;
    private Switch switchAdmin;
    private Button btnGuardar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = AppDatabase.getInstance(this);

        etNombre = findViewById(R.id.etNombreReg);
        etCorreo = findViewById(R.id.etCorreoReg);
        etPass = findViewById(R.id.etPassReg);
        etTelefono = findViewById(R.id.etTelefonoReg);
        switchAdmin = findViewById(R.id.switchAdminReg);
        btnGuardar = findViewById(R.id.btnGuardarRegistro);

        //Comprobamos si tenemos permiso para mandar notificaciones y si no lo teneos lo pedimos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        CODIGO_PERMISO_NOTIFICACION);
            }
        }

        btnGuardar.setOnClickListener(v -> guardarUsuario());
    }

    private void guardarUsuario() {
        String nombre = etNombre.getText().toString();
        String correo = etCorreo.getText().toString();
        String pass = etPass.getText().toString();
        String tlf = etTelefono.getText().toString();
        boolean esAdmin = switchAdmin.isChecked();

        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || tlf.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombre, correo, pass, tlf, esAdmin);

        // insertamos los datos recogidos del formulario en la BDD
        db.appDao().insertUser(nuevoUsuario);

        // mandamos la notificacion habiendo comprobado si tenemos permiso
        lanzarNotificacionRegistro();

        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();

        // al hacer clic en registrar volvemos a la pagina del login
        finish();
    }

    private void lanzarNotificacionRegistro() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "users_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Usuarios", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bienvenido " + etNombre.getText().toString())
                .setContentText("Tu cuenta ha sido creada correctamente.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }

        manager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODIGO_PERMISO_NOTIFICACION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // si tenemos permiso no hacemos nada
            } else {

                Toast.makeText(this, "Sin permiso, no recibirás confirmación de registro", Toast.LENGTH_SHORT).show();
            }
        }
    }
}