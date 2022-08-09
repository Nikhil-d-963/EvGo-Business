package com.example.evgobusiness;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webActivity extends Activity {

    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        webView = (WebView) findViewById(R.id.web);
        String url="https://www.evgo.tech/?m=0";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }

}