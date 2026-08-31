package com.example.educampus.ui.absences;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;

import java.util.List;

public class AbsenceAdapter extends RecyclerView.Adapter<AbsenceAdapter.AbsenceViewHolder> {

    private final List<AbsenceDisplay> absences;

    public AbsenceAdapter(List<AbsenceDisplay> absences) {
        this.absences = absences;
    }

    @NonNull
    @Override
    public AbsenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_absence, parent, false);
        return new AbsenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AbsenceViewHolder holder, int position) {
        AbsenceDisplay absence = absences.get(position);

        holder.tvMatiere.setText(absence.getNomCours());
        holder.tvDate.setText("Date : " + absence.getDate());
        holder.tvEnseignant.setText("Enseignant : " + absence.getNomEnseignant());
        holder.tvMotif.setText("Motif : " + absence.getMotif());
        holder.tvStatut.setText("Statut : " + absence.getStatut());

        boolean justifiee = absence.getStatut().equalsIgnoreCase("Justifiée");
        holder.tvStatut.setTextColor(justifiee ? Color.parseColor("#2E7D32") : Color.parseColor("#C62828"));
    }

    @Override
    public int getItemCount() {
        return absences.size();
    }

    static class AbsenceViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatiere, tvDate, tvEnseignant, tvMotif, tvStatut;

        public AbsenceViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatiere = itemView.findViewById(R.id.tvMatiereAbsence);
            tvDate = itemView.findViewById(R.id.tvDateAbsence);
            tvEnseignant = itemView.findViewById(R.id.tvEnseignantAbsence);
            tvMotif = itemView.findViewById(R.id.tvMotifAbsence);
            tvStatut = itemView.findViewById(R.id.tvStatutAbsence);
        }
    }
}