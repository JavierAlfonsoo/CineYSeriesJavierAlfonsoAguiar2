package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ActividadGuiaAudio extends ActividadBaseMenuLateral {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_audio_guide);
        establecerTituloMenuLateral(R.string.audio_guia);

        TextView descripcion = findViewById(R.id.tvAudioDescripcion);
        Button btnPlay = findViewById(R.id.btnAudioPlay);
        Button btnPause = findViewById(R.id.btnAudioPause);
        Button btnStop = findViewById(R.id.btnAudioStop);

        descripcion.setText(R.string.audio_descripcion);
        mediaPlayer = MediaPlayer.create(this, R.raw.musica_series_y_pelis);

        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }
        });

        btnStop.setOnClickListener(v -> detenerAudio());
    }

    private void detenerAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}

