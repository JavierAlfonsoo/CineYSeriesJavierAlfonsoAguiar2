package com.example.cineyseriesjavieralfonsoaguiar2.interfaz.principal;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class ActividadVideo extends ActividadBaseMenuLateral {

    private static final int SALTO_VIDEO_MS = 5000;

    @Override
    protected void onCreate(Bundle estadoGuardado) {
        super.onCreate(estadoGuardado);
        setContentView(R.layout.activity_video);
        establecerTituloMenuLateral(R.string.ver_video);

        VideoView videoView = findViewById(R.id.videoPresentacion);
        Button btnRetroceder = findViewById(R.id.btnRetrocederVideo);
        Button btnPausar = findViewById(R.id.btnPausarVideo);
        Button btnAdelantar = findViewById(R.id.btnAdelantarVideo);

        videoView.setMediaController(new MediaController(this));
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_tierra);
        videoView.setVideoURI(uri);
        videoView.start();

        btnRetroceder.setOnClickListener(v ->
                videoView.seekTo(Math.max(videoView.getCurrentPosition() - SALTO_VIDEO_MS, 0)));

        btnPausar.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                btnPausar.setText(R.string.reproducir);
            } else {
                videoView.start();
                btnPausar.setText(R.string.pausar);
            }
        });

        btnAdelantar.setOnClickListener(v -> {
            int nuevaPosicion = videoView.getCurrentPosition() + SALTO_VIDEO_MS;
            int duracion = videoView.getDuration();
            videoView.seekTo(duracion > 0 ? Math.min(nuevaPosicion, duracion) : nuevaPosicion);
        });
    }
}

