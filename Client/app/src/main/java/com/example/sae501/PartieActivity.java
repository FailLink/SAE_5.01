package com.example.sae501;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PartieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie);
        MainActivity.currentActivity=this;
        System.out.println(MainActivity.currentActivity+"activité");
    }
    public void onClickLancerPartie(View view){
        Intent intent=new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}