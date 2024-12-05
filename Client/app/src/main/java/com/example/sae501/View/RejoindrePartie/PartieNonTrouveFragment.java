package com.example.sae501.View.RejoindrePartie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PartieNonTrouveFragment extends DialogFragment {
    /**
     * Création d'une popup pour prévenir le joueur que la partie qu'il recherche n'existe pas
     *
     * @param savedInstanceState The last saved instance state of the Fragment,
     *                           or null if this is a freshly created Fragment.
     * @return le dialog créé
     * @author Matisse Gallouin
     */
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Partie non trouvé")
                .setMessage("La partie que vous recherchez n'existe pas")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}
