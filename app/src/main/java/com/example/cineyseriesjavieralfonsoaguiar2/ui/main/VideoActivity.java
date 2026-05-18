package com.example.cineyseriesjavieralfonsoaguiar2.ui.main;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.cineyseriesjavieralfonsoaguiar2.R;

public class VideoActivity extends BaseDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setDrawerTitle(R.string.ver_video);

        VideoView videoView = findViewById(R.id.videoPresentacion);
        videoView.setMediaController(new MediaController(this));
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.splash_gato);
        videoView.setVideoURI(uri);
        videoView.start();
    }
}

