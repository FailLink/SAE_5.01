package com.example.sae501.View.RejoindrePartie;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.sae501.MainActivity;
import com.example.sae501.R;

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
        TextView title=new TextView(MainActivity.currentActivity);
        title.setText("Partie non trouvé");
        title.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setCustomTitle(title)
                .setMessage("La partie que vous recherchez n'existe pas")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        Dialog dialog= builder.create();
        dialog.getWindow().setBackgroundDrawable(
                ContextCompat.getDrawable(MainActivity.currentActivity,R.color.backgroundColor));

        dialog.setOnShowListener(dialogInterface -> {
            TextView messageView = dialog.findViewById(android.R.id.message);
            messageView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

            Button negativeButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(ContextCompat.getColor(getActivity(), R.color.red));
        });

        return dialog;
    }
}
