package com.example.sae501.View.Partie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.sae501.HomeActivity;
import com.example.sae501.MainActivity;

public class ExclusionFragment extends DialogFragment {
    /**
     * Création d'une popup en cas d'exclusion de la partie afin de prévenir le joueur, effacer ses variables globales
     * et le renvoyer sur l'écran d'accueil
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return le dialog créé
     * @author Matisse Gallouin
     */
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Exclue de la partie")
                .setMessage("Vous avez été exclu de la partie")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.joueursPartie.clear();
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
