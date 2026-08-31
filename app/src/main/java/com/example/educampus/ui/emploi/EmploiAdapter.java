package com.example.educampus.ui.emploi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;

import java.util.List;

public class EmploiAdapter extends RecyclerView.Adapter<EmploiAdapter.SeanceViewHolder> {

    private final List<SeanceDisplay> seances;

    public EmploiAdapter(List<SeanceDisplay> seances) {
        this.seances = seances;
    }

    @NonNull
    @Override
    public SeanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_emploi, parent, false);
        return new SeanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeanceViewHolder holder, int position) {
        SeanceDisplay seance = seances.get(position);

        holder.tvJour.setText(seance.getJour());
        holder.tvHoraire.setText(seance.getHoraire());
        holder.tvNomCours.setText(seance.getNomCours());
        holder.tvSalle.setText(seance.getSalle());
        holder.tvProf.setText("Prof : " + seance.getNomEnseignant());
        holder.tvType.setText("Type : " + seance.getTypeSeance());
    }

    @Override
    public int getItemCount() {
        return seances.size();
    }

    static class SeanceViewHolder extends RecyclerView.ViewHolder {
        TextView tvJour, tvHoraire, tvNomCours, tvSalle, tvProf, tvType;

        public SeanceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJour = itemView.findViewById(R.id.tvJour);
            tvHoraire = itemView.findViewById(R.id.tvHoraire);
            tvNomCours = itemView.findViewById(R.id.tvNomCours);
            tvSalle = itemView.findViewById(R.id.tvSalle);
            tvProf = itemView.findViewById(R.id.tvProf);
            tvType = itemView.findViewById(R.id.tvType);
        }
    }
}