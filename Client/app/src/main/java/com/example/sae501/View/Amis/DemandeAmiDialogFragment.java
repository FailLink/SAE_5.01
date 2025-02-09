package com.example.sae501.View.Amis;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sae501.Controller.Amis.AmisRepository;
import com.example.sae501.Model.Entity.DemandeAmi;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.R;

import java.util.List;

public class DemandeAmiDialogFragment extends DialogFragment {
        private List<DemandeAmi> demandeAmis;
        public DemandeAmiDialogFragment(List<DemandeAmi> demandeAmis){
            Log.d("dialog","allez");
            this.demandeAmis=demandeAmis;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d("dialog","dialog créé");
            Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.demande_ami_dialog_fragment);

            RecyclerView recyclerView = dialog.findViewById(R.id.recyclerViewDemandeAmis);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new DemandeAmiAdapter(demandeAmis));

            Button btnClose = dialog.findViewById(R.id.btnCloseInvitation);
            btnClose.setOnClickListener(v -> dismiss());

            return dialog;
        }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();

            params.width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            params.height = (int) (getResources().getDisplayMetrics().heightPixels * 0.9);

            getDialog().getWindow().setAttributes(params);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK)); // Fond noir semi-transparent
        }
    }
}
