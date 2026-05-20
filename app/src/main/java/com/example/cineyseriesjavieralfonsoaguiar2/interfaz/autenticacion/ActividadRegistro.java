package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.autenticacion;

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
import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;
import com.example.cineyseriesjavieralfonsoaguiar2.datos.entidades.Usuario;
import com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal.ActividadBaseMenuLateral;

public class ActividadRegistro extends ActividadBaseMenuLateral {

    private static final int CODIGO_PERMISO_NOTIFICACION = 100;
    private static final int CODIGO_PERMISO_CONTACTOS = 101;
    private static final int CODIGO_ELEGIR_CONTACTO = 102;

    private EditText etNombre, etCorreo, etPass, etTelefono;
    private Switch switchAdmin;
    private BaseDatosApp baseDatos;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_registro);
        establecerTituloMenuLateral(R.string.nuevaCuenta);

        baseDatos = BaseDatosApp.obtenerInstancia(this);
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
        Intent intencion = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intencion, CODIGO_ELEGIR_CONTACTO);
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

        if (!ValidadorCorreo.isValid(correo)) {
            etCorreo.setError(getString(R.string.correo_invalido));
            etCorreo.requestFocus();
            return;
        }

        baseDatos.daoApp().insertarUsuario(new Usuario(nombre, correo, pass, tlf, esAdmin));
        lanzarNotificacionRegistro(nombre);
        Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void lanzarNotificacionRegistro(String nombre) {
        NotificationManager gestor = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        String idCanal = "users_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel canal = new NotificationChannel(idCanal, "Usuarios", NotificationManager.IMPORTANCE_DEFAULT);
            gestor.createNotificationChannel(canal);
        }

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(this, idCanal)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bienvenido " + nombre)
                .setContentText("Tu cuenta ha sido creada correctamente.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gestor.notify(1, constructor.build());
    }

    @Override
    public void onRequestPermissionsResult(int codigoSolicitud, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(codigoSolicitud, permissions, grantResults);
        if (codigoSolicitud == CODIGO_PERMISO_CONTACTOS
                && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            importarContacto();
        } else if (codigoSolicitud == CODIGO_PERMISO_NOTIFICACION
                && grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Sin permiso, no recibirÁs confirmación de registro", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int codigoSolicitud, int codigoResultado, Intent datos) {
        super.onActivityResult(codigoSolicitud, codigoResultado, datos);
        if (codigoSolicitud == CODIGO_ELEGIR_CONTACTO && codigoResultado == RESULT_OK && datos != null) {
            Uri uri = datos.getData();
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

