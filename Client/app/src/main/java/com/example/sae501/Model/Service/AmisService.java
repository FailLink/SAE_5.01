package com.example.sae501.Model.Service;

import com.example.sae501.Model.Entity.DemandeAmi;
import com.example.sae501.Model.Entity.Joueur;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AmisService {
    @POST("/sendInvitationAmi")
    Call<DemandeAmi> sendInvitationAmis(@Query("idJoueurInviteur")Long idJoueurInviteur,@Query("idJoueurInvite")Long idJoueurInvite);
    @POST("/getAllInvitationReceipt")
    Call<List<DemandeAmi>> getAllInvitationReceipt();
    @POST("/getAllInvitationSend")
    Call<String> getAllInvitationSend();
    @POST("/addAmi")
    Call<String> addAmi(@Query("idAmi")Long idAmi);
    @POST("/getAllAmi")
    Call<List<Joueur>> getAllAmi();
    @POST("/deleteInvitation")
    Call<String> deleteInvitation(@Query("joueurInviteur") Long idJoueurInviteur);
}
