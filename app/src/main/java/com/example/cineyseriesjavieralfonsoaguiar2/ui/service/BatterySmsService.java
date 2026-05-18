package com.example.cineyseriesjavieralfonsoaguiar2.ui.service;

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

import com.example.cineyseriesjavieralfonsoaguiar2.data.AppDatabase;

public class BatterySmsService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        comprobarBateriaYEnviarSms();
        stopSelf();
        return START_NOT_STICKY;
    }

    private void comprobarBateriaYEnviarSms() {
        Intent batteryStatus = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        if (batteryStatus == null) {
            return;
        }
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int porcentaje = scale > 0 ? (int) ((level / (float) scale) * 100) : 100;

        if (porcentaje < 5 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            String telefonoAdmin = AppDatabase.getInstance(this).appDao().getAdminPhone();
            if (telefonoAdmin != null && !telefonoAdmin.trim().isEmpty()) {
                SmsManager.getDefault().sendTextMessage(telefonoAdmin, null,
                        "Alerta: bateria por debajo del 5% en Cine y Series.", null, null);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

