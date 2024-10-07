package com.example.SAE501;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.SAE501.Controller.ConnexionRepository;

public class MainActivity extends AppCompatActivity {
    private ConnexionRepository connexionRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connexionRepository=new ConnexionRepository(this);
        connexionRepository.getTestConnexion();
    }
}