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

public class ExclusionFragment extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Exclue de la partie")
                .setMessage("Vous avez été exclu de la partie")
                .setNegativeButton("Ok",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                });
        return builder.create();
    }
}
