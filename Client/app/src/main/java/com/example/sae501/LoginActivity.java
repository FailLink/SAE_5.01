package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            WebView webView = findViewById(R.id.webview);
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);

                    if (url.equals("http://10.0.2.2:8080/testConnexion")) {
                        String cookies = CookieManager.getInstance().getCookie(url);
                        if (cookies != null && cookies.contains("JSESSIONID")) {
                            // Enregistrez le cookie JSESSIONID pour les requêtes Retrofit
                            SharedPreferences sharedPreferences = getSharedPreferences("SlayMonstersData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("session_cookie", cookies);
                            editor.apply();
                        }
                        finish();
                    }

                }
            });
            webView.getSettings().setJavaScriptEnabled(true); // Si nécessaire
            webView.loadUrl("http://10.0.2.2:8080/login");

        }
}