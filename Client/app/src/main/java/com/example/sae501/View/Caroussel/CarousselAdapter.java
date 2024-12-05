package com.example.sae501.View.Caroussel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sae501.R;

import java.util.List;

public class CarousselAdapter extends RecyclerView.Adapter<CarousselAdapter.CarouselViewHolder> {
    private final List<PageClasse> pagesClasses;

    /**
     * constructeur de la classe carroussel adapter
     *
     * @param pagesClasses pages contenues dans le carroussel
     * @author Matisse Gallouin
     */
    public CarousselAdapter(List<PageClasse> pagesClasses) {
        this.pagesClasses = pagesClasses;
    }

    @NonNull
    @Override
    public CarousselAdapter.CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.classe_carrousel, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarousselAdapter.CarouselViewHolder holder, int position) {
        PageClasse pageClasse = pagesClasses.get(position);
        holder.logoImage.setImageResource(pageClasse.getLogoResId());
        holder.titleView.setText(pageClasse.getTitle());
        holder.descriptionView.setText(pageClasse.getDescription());
        holder.buttonChoixClasse.setText(pageClasse.getButtonText());
        holder.buttonChoixClasse.setOnClickListener(v -> pageClasse.getClickListener().onClick(v));
    }

    @Override
    public int getItemCount() {
        return pagesClasses.size();
    }

    //classe définissant la composition de la vue du carroussel et de récupérer les éléments pour les attacher à des valeurs ensuite
    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView logoImage;
        TextView titleView;
        TextView descriptionView;
        Button buttonChoixClasse;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            logoImage = itemView.findViewById(R.id.logoImage);
            titleView = itemView.findViewById(R.id.titleView);
            descriptionView = itemView.findViewById(R.id.descriptionView);
            buttonChoixClasse = itemView.findViewById(R.id.buttonChoixClasse);
        }
    }
}
