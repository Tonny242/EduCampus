package com.example.educampus.ui.admin.cours;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.model.CoursDetails;

import java.util.List;

public class AdminCoursAdapter extends RecyclerView.Adapter<AdminCoursAdapter.ViewHolder> {

    private List<CoursDetails> coursList;
    private final OnCoursActionListener listener;

    public interface OnCoursActionListener {
        void onEdit(CoursDetails cours);
        void onDelete(CoursDetails cours);
    }

    public AdminCoursAdapter(List<CoursDetails> coursList, OnCoursActionListener listener) {
        this.coursList = coursList;
        this.listener = listener;
    }

    public void updateList(List<CoursDetails> newList) {
        this.coursList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_cours, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CoursDetails details = coursList.get(position);
        holder.tvNomCours.setText(details.cours.getNom());
        holder.tvCodeSemestre.setText(details.cours.getCode() + " - " + details.cours.getSemestre());
        
        String enseignantName = details.enseignant != null ? details.enseignant.getPrenom() + " " + details.enseignant.getNom() : "N/A";
        String formationName = details.formation != null ? details.formation.getNom() : "N/A";
        holder.tvEnseignantFormation.setText(enseignantName + " | " + formationName);

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(details));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(details));
    }

    @Override
    public int getItemCount() {
        return coursList != null ? coursList.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomCours, tvCodeSemestre, tvEnseignantFormation;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvNomCours = view.findViewById(R.id.tvNomCours);
            tvCodeSemestre = view.findViewById(R.id.tvCodeSemestre);
            tvEnseignantFormation = view.findViewById(R.id.tvEnseignantFormation);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}