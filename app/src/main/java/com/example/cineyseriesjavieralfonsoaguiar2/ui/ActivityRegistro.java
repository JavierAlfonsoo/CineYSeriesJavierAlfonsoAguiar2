package com.example.cineyseriesjavieralfonsoaguiar2.ui;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.R;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.AppDatabase;
import com.example.cineyseriesjavieralfonsoaguiar2.bd.Usuario;

public class ActivityRegistro extends BaseDrawerActivity {

    private static final int CODIGO_PERMISO_NOTIFICACION = 100;
    private static final int CODIGO_PERMISO_CONTACTOS = 101;
    private static final int CODIGO_ELEGIR_CONTACTO = 102;

    private EditText etNombre, etCorreo, etPass, etTelefono;
    private Switch switchAdmin;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setDrawerTitle(R.string.nuevaCuenta);

        db = AppDatabase.getInstance(this);
        etNombre = findViewById(R.id.etNombreReg);
        etCorreo = findViewById(R.id.etCorreoReg);
        etPass = findViewById(R.id.etPassReg);
        etTelefono = findViewById(R.id.etTelefonoReg);
        switchAdmin = findViewById(R.id.switchAdminReg);
        Button btnGuardar = findViewById(R.id.btnGuardarRegistro);
        Button btnImportarContacto = findViewById(R.id.btnImportarContacto);

        pedirPermisoNotificaciones();
        btnGuardar.setOnClickListener(v -> guardarUsuario());
        btnImportarContacto.setOnClickListener(v -> importarContacto());
    }

    private void pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.POST_NOTIFICATIONS},
                    CODIGO_PERMISO_NOTIFICACION);
        }
    }

    private void importarContacto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    CODIGO_PERMISO_CONTACTOS);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, CODIGO_ELEGIR_CONTACTO);
    }

    private void guardarUsuario() {
        String nombre = etNombre.getText().toString();
        String correo = etCorreo.getText().toString().trim();
        String pass = etPass.getText().toString();
        String tlf = etTelefono.getText().toString();
        boolean esAdmin = switchAdmin.isChecked();

        if (nombre.isEmpty() || correo.isEmpty() || pass.isEmpty() || tlf.isEmpty()) {
            Toast.makeText(this, R.string.rellena_campos, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!EmailValidator.isValid(correo)) {
            etCorreo.setError(getString(R.string.correo_invalido));
            etCorreo.requestFocus();
            return;
        }

        db.appDao().insertUser(new Usuario(nombre, correo, pass, tlf, esAdmin));
        lanzarNotificacionRegistro(nombre);
        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void lanzarNotificacionRegistro(String nombre) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String channelId = "users_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Usuarios", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bienvenido " + nombre)
                .setContentText("Tu cuenta ha sido creada correctamente.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.notify(1, builder.build());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PERMISO_CONTACTOS
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            importarContacto();
        } else if (requestCode == CODIGO_PERMISO_NOTIFICACION
                && grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Sin permiso, no recibirás confirmación de registro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_ELEGIR_CONTACTO && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            String[] columnas = {
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            try (Cursor cursor = getContentResolver().query(uri, columnas, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    etNombre.setText(cursor.getString(0));
                    etTelefono.setText(cursor.getString(1));
                }
            }
        }
    }
}
