package com.example.mbpro.testwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

public class WebviewActivity extends AppCompatActivity implements MusejamSoundcloudInterface.MusejamSoundcloudCallback {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webView = (WebView) findViewById(R.id.webview);
        String streamUrl = "https://api.soundcloud.com/tracks/50047366";
        String iframe = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<iframe width=\"100%\" height=\"450\" id=\"sc-widget\" scrolling=\"no\" frameborder=\"no\" src=\"https://w.soundcloud.com/player/?url="+streamUrl+"&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true\"></iframe>" +
                "<script src=\"https://w.soundcloud.com/player/api.js\" type=\"text/javascript\"></script>" +
                "<script type=\"text/javascript\">" +
                "(function(){" +
                "var widgetIframe = document.getElementById(\"sc-widget\")," +
                "widget       = SC.Widget(widgetIframe);" +
                "window.widget = widget;" +
                "window.widget.bind(SC.Widget.Events.READY, function() {" +
                "Musejam.onReady();" +
                "});" +
                "}());" +
                "</script>" +
                "</body>" +
                "</html>";
        Log.d("Iframe ", "onCreate: " + iframe);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
            }
        });
        webView.setNetworkAvailable(true);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        webView.addJavascriptInterface(new MusejamSoundcloudInterface(this), "Musejam");
        webView.loadDataWithBaseURL("", iframe, "text/html", "UTF-8", "");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            webView.createWebMessageChannel();
//        }
        Button button = (Button) findViewById(R.id.playMe);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("javascript:widget.play()");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        webView.loadUrl("javascript:widget.pause()");

    }

    @Override
    public void enableButton() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Button button = (Button) findViewById(R.id.playMe);
                button.setEnabled(true);
            }
        });
    }
}
