package com.example.sae501.View.Amis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sae501.Controller.Amis.AmisRepository;
import com.example.sae501.Model.Entity.DemandeAmi;
import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.R;

import java.util.List;

public class DemandeAmiAdapter extends RecyclerView.Adapter<DemandeAmiAdapter.DemandeAmisViewHolder>{
    public List<DemandeAmi> demandeAmis;
    private AmisRepository amisRepository=new AmisRepository();

    public DemandeAmiAdapter(List<DemandeAmi> demandeAmis){
        this.demandeAmis=demandeAmis;
    }

    @NonNull
    @Override
    public DemandeAmiAdapter.DemandeAmisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demande_ami_element, parent, false);
        return new DemandeAmisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemandeAmisViewHolder holder, int position) {
        holder.pseudoInviteur.setText(demandeAmis.get(position).getJoueurInviteur().getPseudo());
        holder.buttonAccepter.setOnClickListener(v -> {
            amisRepository.addAmi(demandeAmis.get(position).getJoueurInviteur().getId());
            demandeAmis.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, demandeAmis.size());
        });

        holder.buttonRefuser.setOnClickListener(v -> {
            amisRepository.deleteInvitation(demandeAmis.get(position).getJoueurInviteur().getId());
            demandeAmis.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, demandeAmis.size());
        });
    }

    @Override
    public int getItemCount() {
        return demandeAmis.size();
    }
    static class DemandeAmisViewHolder extends RecyclerView.ViewHolder {
        TextView pseudoInviteur;
        Button buttonAccepter;
        Button buttonRefuser;

        DemandeAmisViewHolder(View itemView) {
            super(itemView);
            pseudoInviteur = itemView.findViewById(R.id.pseudoInviteur);
            buttonAccepter=itemView.findViewById(R.id.accepterInvitation);
            buttonRefuser=itemView.findViewById(R.id.refuserInvitation);
        }
    }
}
