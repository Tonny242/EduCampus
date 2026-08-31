package com.example.educampus.ui.annonces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;

import java.util.List;

public class AnnonceAdapter extends RecyclerView.Adapter<AnnonceAdapter.AnnonceViewHolder> {

    private final List<AnnonceDisplay> annonces;

    public AnnonceAdapter(List<AnnonceDisplay> annonces) {
        this.annonces = annonces;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_annonce, parent, false);
        return new AnnonceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder holder, int position) {
        AnnonceDisplay annonce = annonces.get(position);

        holder.tvCategorie.setText(annonce.getCategorie().toUpperCase());
        holder.tvTitre.setText(annonce.getTitre());
        holder.tvContenu.setText(annonce.getContenu());
        holder.tvMeta.setText(annonce.getDate() + " · " + annonce.getAuteur());
    }

    @Override
    public int getItemCount() {
        return annonces.size();
    }

    static class AnnonceViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategorie, tvTitre, tvContenu, tvMeta;

        public AnnonceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategorie = itemView.findViewById(R.id.tvCategorieAnnonce);
            tvTitre = itemView.findViewById(R.id.tvTitreAnnonce);
            tvContenu = itemView.findViewById(R.id.tvContenuAnnonce);
            tvMeta = itemView.findViewById(R.id.tvMetaAnnonce);
        }
    }
}