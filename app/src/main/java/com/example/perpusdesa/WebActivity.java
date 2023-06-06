package com.example.perpusdesa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String url = getIntent().getStringExtra("url");

        WebView myWebView = (WebView) findViewById(R.id.web);
        myWebView.setWebViewClient(new WebViewClient());
        myWebView.getSettings().setSupportZoom(false);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.loadUrl("https://docs.google.com/viewer?url=" + url);

    }
}