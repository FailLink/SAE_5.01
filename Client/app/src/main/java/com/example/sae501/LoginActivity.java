package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public class ActivityLogin extends AppCompatActivity {

        // Variables locales pour simuler une connexion
        private final String correctEmail = "test@example.com";
        private final String correctPassword = "123456";

        private EditText emailEditText, passwordEditText;
        private Button loginButton, backButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            // Récupérer les champs de saisie et les boutons
            emailEditText = findViewById(R.id.emailEditText);
            passwordEditText = findViewById(R.id.passwordEditText);
            loginButton = findViewById(R.id.loginButton);
            backButton = findViewById(R.id.backButton);

            // Ajouter un événement au clic sur le bouton de connexion
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Récupérer les données saisies
                    String enteredEmail = emailEditText.getText().toString();
                    String enteredPassword = passwordEditText.getText().toString();

                    // Vérifier si l'email et le mot de passe correspondent aux valeurs prédéfinies
                    if (enteredEmail.equals(correctEmail) && enteredPassword.equals(correctPassword)) {
                        // Si les informations sont correctes, afficher un message de réussite
                        Toast.makeText(ActivityLogin.this, "Connexion réussie", Toast.LENGTH_SHORT).show();

                        // Rediriger l'utilisateur vers la page d'accueil du jeu (HomeActivity)
                        Intent intent = new Intent(ActivityLogin.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        // Si les informations sont incorrectes, afficher un message d'erreur
                        Toast.makeText(ActivityLogin.this, "Adresse e-mail ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Bouton de retour vers la MainActivity
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Rediriger vers MainActivity
                    Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}