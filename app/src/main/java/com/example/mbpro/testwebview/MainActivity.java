package com.example.mbpro.testwebview;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button playerButton = (Button) findViewById(R.id.playerButton);
        Button webviewButton = (Button) findViewById(R.id.webviewButton);
        playerButton.setOnClickListener(this);
        webviewButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.playerButton:
                intent = new Intent(this, PlayerActivity.class);
                break;
            case R.id.webviewButton:
                intent = new Intent(this, WebviewActivity.class);
                break;
        }
        startActivity(intent);
    }
}
