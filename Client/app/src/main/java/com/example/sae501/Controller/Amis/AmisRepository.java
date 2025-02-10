package com.example.sae501.Controller.Amis;

import android.util.Log;
import android.widget.Toast;

import com.example.sae501.MainActivity;
import com.example.sae501.Model.Entity.DemandeAmi;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.Model.Serveur.RetroFitClient;
import com.example.sae501.Model.Service.AmisService;
import com.example.sae501.View.Amis.AmisDialogFragment;
import com.example.sae501.View.Amis.DemandeAmiDialogFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AmisRepository {
    AmisService amisService;
    public AmisRepository() {
        Retrofit retroFitClient = RetroFitClient.getRetrofitInstance();
        this.amisService = retroFitClient.create(AmisService.class);
    }

        public void getAllAmis(){
        Call<List<Joueur>> responseHttp=amisService.getAllAmi();
        responseHttp.enqueue(new Callback<List<Joueur>>() {
            @Override
            public void onResponse(Call<List<Joueur>> call, Response<List<Joueur>> response) {
                new AmisDialogFragment(response.body()).show(MainActivity.currentActivity.getSupportFragmentManager(),"amis");
            }

            @Override
            public void onFailure(Call<List<Joueur>> call, Throwable t) {

            }
        });
    }
    public void getInvitReceipt() {
        Call<List<DemandeAmi>> responseHttp = amisService.getAllInvitationReceipt();
        responseHttp.enqueue(new Callback<List<DemandeAmi>>() {

            @Override
            public void onResponse(Call<List<DemandeAmi>> call, Response<List<DemandeAmi>> response) {
                new DemandeAmiDialogFragment(response.body()).show(MainActivity.currentActivity.getSupportFragmentManager(),"amis");
            }

            @Override
            public void onFailure(Call<List<DemandeAmi>> call, Throwable t) {
                Log.d("allez","alleree");
            }
        });
    }
    public void deleteInvitation(long joueurInviteur){
        Call<String> responseHttp=amisService.deleteInvitation(joueurInviteur);
        responseHttp.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void addAmi(long idJoueurInviteur){
        Call<String> responseHttp=amisService.addAmi(idJoueurInviteur);
        responseHttp.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                deleteInvitation(idJoueurInviteur);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void sendInvitationAmi(Long idJoueurInvite){
        Call<DemandeAmi> responseHttp=amisService.sendInvitationAmis(MainActivity.joueur.getId(),idJoueurInvite);
        responseHttp.enqueue(new Callback<DemandeAmi>() {
            @Override
            public void onResponse(Call<DemandeAmi> call, Response<DemandeAmi> response) {
                Toast toast=new Toast(MainActivity.currentActivity.getApplicationContext());
                toast.setText("l'invitation a été envoyé");
                toast.show();
            }

            @Override
            public void onFailure(Call<DemandeAmi> call, Throwable t) {
                Toast toast=new Toast(MainActivity.currentActivity.getApplicationContext());
                toast.setText("l'invitation n'a pas été envoyé");
                toast.show();
            }
        });
    }
}
