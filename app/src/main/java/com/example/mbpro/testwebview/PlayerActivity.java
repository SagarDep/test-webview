package com.example.mbpro.testwebview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener {
    static String TAG = "MediaPlayer";
    MediaPlayer mediaPlayer;
    MediaObserver observer;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        progress = (ProgressBar) findViewById(R.id.progress);
        String url = "https://api.soundcloud.com/tracks/50047366/stream?client_id=a523e07fb9d3efeb1fb8735a4eeb9c49";
        Log.d(TAG, "onCreate: " + url);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnCompletionListener(this);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        observer = new MediaObserver();
        progress.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        new Thread(observer).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        observer.stop();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        Log.d(TAG, "onBufferingUpdate: " + mediaPlayer.getCurrentPosition());
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        mediaPlayer.release();
        observer.stop();
        Log.d(TAG, "onCompletion: done");
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
//        mediaPlayer.getCurrentPosition();
        return false;
    }

    class MediaObserver implements Runnable{
        private AtomicBoolean stop = new AtomicBoolean(false);

        public void stop() {
            stop.set(true);
        }

        @Override
        public void run() {
            while (!stop.get()) {
                progress.setProgress(mediaPlayer.getCurrentPosition());
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
