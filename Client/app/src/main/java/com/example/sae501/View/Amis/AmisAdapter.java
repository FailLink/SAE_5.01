package com.example.sae501.View.Amis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sae501.Model.Entity.Joueur;
import com.example.sae501.R;

import java.util.List;

public class AmisAdapter extends RecyclerView.Adapter<AmisAdapter.AmisViewHolder> {
    public List<Joueur> amis;

    public AmisAdapter(List<Joueur> amis){
        this.amis=amis;
    }

    @NonNull
    @Override
    public AmisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ami_element, parent, false);
        return new AmisViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmisViewHolder holder, int position) {
        holder.pseudoAmi.setText(amis.get(position).getPseudo());
    }

    @Override
    public int getItemCount() {
        return amis.size();
    }
    static class AmisViewHolder extends RecyclerView.ViewHolder {
        TextView pseudoAmi;

        AmisViewHolder(View itemView) {
            super(itemView);
            pseudoAmi = itemView.findViewById(R.id.pseudoAmi);
        }
    }
}
