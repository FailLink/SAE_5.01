package com.example.sae501.View;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sae501.Controller.Connexion.ConnexionRepository;

//Il est possible de modifier le visuel du dialogfragment en créant un layout personalisé
// attention voir comment modifier le code dans ce cas
public class ConnexionErrorFragment extends DialogFragment {
    private ConnexionRepository connexionRepository ;

    public ConnexionErrorFragment(ConnexionRepository connexionRepository) {
        this.connexionRepository = connexionRepository;
    }

    /**
     *Création d'une popup en cas d'erreur de connexion permettant de tenter de se reconnecter ou de quitter l'application
     * @author Matisse Gallouin
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     * @return retourne le dialog créé
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Erreur de connexion")
                .setMessage("Le serveur n'est pas joignable.\nVeuillez vérifier votre connexion.")
                .setPositiveButton("Essayer de se connecter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        connexionRepository.getTestConnexion();
                    }
                }).setNegativeButton("Quitter",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finishAffinity();
                    }
                });
        return builder.create();
    }
}