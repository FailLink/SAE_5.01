package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.sae501.Controller.Connexion.ConnexionRepository;
import com.example.sae501.Model.ScheduleTask.ScheduleConnexion;

public class LoginActivity extends AppCompatActivity {
    ConnexionRepository connexionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        MainActivity.currentActivity = this;
        WebView webView = findViewById(R.id.webview);

        connexionRepository = new ConnexionRepository(new ScheduleConnexion());
        webView.setWebViewClient(new WebViewClient() {
            /**
             * fonction permettant de définir le comportement en cas de fin de chargement d'une page
             * elle permet d'enregistrer les informations concernant le session id du joueur et récupérer les informations du joueur
             * @param view The WebView that is initiating the callback.
             * @param url The url of the page.
             * @author Matisse Gallouin
             */
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("http://" + MainActivity.globalIP + "/testConnexion")) {
                    //stop la webview
                    finish();

                    //recherche le jsession id
                    String cookies = CookieManager.getInstance().getCookie(url);
                    if (cookies != null && cookies.contains("JSESSIONID")) {
                        String[] cookieArray = cookies.split(";");
                        String jsessionId = null;
                        for (String cookie : cookieArray) {
                            if (cookie.trim().startsWith("JSESSIONID=")) {
                                jsessionId = cookie.split("=")[1].trim();
                                break;
                            }
                        }

                        if (jsessionId != null) {
                            //enregistre le jsession id
                            SharedPreferences sharedPreferences = getSharedPreferences("SlayMonstersData", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("session_cookie", jsessionId);
                            editor.apply();
                            MainActivity.sessionID = jsessionId;
                        }
                        //récupère les informations du joueur
                        connexionRepository.getJoueurBySessionId();
                    }
                }
            }

            /**
             * fonction permttant de gérer les redirections
             * @param view The WebView that is initiating the callback.
             * @param request Object containing the details of the request.
             * @return true pour informer que la redirection a été pris en charge
             * @author Matisse Gallouin
             */
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            /**
             * fonction permettant la gestion et l'affichage des alertes js
             * @param view The WebView that initiated the callback.
             * @param url The url of the page requesting the dialog.
             * @param message Message to be displayed in the window.
             * @param result A JsResult to confirm that the user closed the window.
             * @return true pour signifier que l'alerte a été pris en charge
             * @author Matisse Gallouin
             */
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.currentActivity)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> result.confirm())
                        .setCancelable(false)
                        .create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                        ContextCompat.getColor(MainActivity.currentActivity, R.color.backgroundColor)));

                alertDialog.show();

                TextView messageView = alertDialog.findViewById(android.R.id.message);
                messageView.setTextColor(ContextCompat.getColor(MainActivity.currentActivity, R.color.white));

                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                negativeButton.setTextColor(ContextCompat.getColor(MainActivity.currentActivity, R.color.red));

                return true;
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://" + MainActivity.globalIP + "/login");

    }
}