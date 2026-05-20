package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.servicio;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.cineyseriesjavieralfonsoaguiar2.datos.BaseDatosApp;

public class ServicioSmsBateria extends Service {

    @Override
    public int onStartCommand(Intent intencion, int banderas, int startId) {
        comprobarBateriaYEnviarSms();
        stopSelf();
        return START_NOT_STICKY;
    }

    private void comprobarBateriaYEnviarSms() {
        Intent estadoBateria = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (estadoBateria == null) {
            return;
        }
        int nivel = estadoBateria.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int escala = estadoBateria.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int porcentaje = escala > 0 ? (int) ((nivel / (float) escala) * 100) : 100;

        if (porcentaje < 5 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            String telefonoAdmin = BaseDatosApp.obtenerInstancia(this).daoApp().obtenerTelefonoAdmin();
            if (telefonoAdmin != null && !telefonoAdmin.trim().isEmpty()) {
                SmsManager.getDefault().sendTextMessage(telefonoAdmin, null,
                        "Alerta: bateria por debajo del 5% en Cine y Series.", null, null);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intencion) {
        return null;
    }
}

