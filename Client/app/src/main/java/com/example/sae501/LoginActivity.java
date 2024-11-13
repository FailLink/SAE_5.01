package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.sae501.Controller.Connexion.ConnexionRepository;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;

public class LoginActivity extends AppCompatActivity {
        ConnexionRepository connexionRepository;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            WebView webView = findViewById(R.id.webview);

            connexionRepository=new ConnexionRepository(this,new ScheduleConnexion(this));
            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (url.equals("http://10.0.2.2:8080/testConnexion")) {
                        String cookies = CookieManager.getInstance().getCookie(url);
                        System.out.println(cookies);
                        if (cookies != null && cookies.contains("JSESSIONID")) {
                            // Trouver uniquement la valeur de JSESSIONID
                            String[] cookieArray = cookies.split(";");
                            String jsessionId = null;
                            for (String cookie : cookieArray) {
                                if (cookie.trim().startsWith("JSESSIONID")) {
                                    jsessionId = cookie.split("=")[1].trim();
                                    break;
                                }
                            }

                            if (jsessionId != null) {
                                // Enregistrez uniquement le JSESSIONID pour les requêtes Retrofit
                                SharedPreferences sharedPreferences = getSharedPreferences("SlayMonstersData", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("session_cookie", jsessionId);
                                editor.apply();
                                MainActivity.sessionID = jsessionId;
                            }
                            finish();
                            connexionRepository.getJoueurBySessionId();
                        }
                    }
                }
            });
            webView.getSettings().setJavaScriptEnabled(true); // Si nécessaire
            webView.loadUrl("http://10.0.2.2:8080/login");

        }
}