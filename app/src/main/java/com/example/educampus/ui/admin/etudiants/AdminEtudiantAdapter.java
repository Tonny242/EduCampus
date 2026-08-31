package com.example.educampus.ui.admin.etudiants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.model.EtudiantDetails;

import java.util.List;

public class AdminEtudiantAdapter extends RecyclerView.Adapter<AdminEtudiantAdapter.ViewHolder> {

    private List<EtudiantDetails> etudiants;
    private final OnEtudiantActionListener listener;

    public interface OnEtudiantActionListener {
        void onEdit(EtudiantDetails etudiant);
        void onDelete(EtudiantDetails etudiant);
    }

    public AdminEtudiantAdapter(List<EtudiantDetails> etudiants, OnEtudiantActionListener listener) {
        this.etudiants = etudiants;
        this.listener = listener;
    }

    public void updateList(List<EtudiantDetails> newList) {
        this.etudiants = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_etudiant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EtudiantDetails details = etudiants.get(position);
        if (details.user != null) {
            holder.tvNomPrenom.setText(details.user.getNom() + " " + details.user.getPrenom());
            holder.tvEmail.setText(details.user.getEmail());
            
            String formationName = details.formation != null ? details.formation.getNom() : "N/A";
            holder.tvFormationNiveau.setText(formationName + " - " + details.etudiant.getNiveau());
        }

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(details));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(details));
    }

    @Override
    public int getItemCount() {
        return etudiants != null ? etudiants.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomPrenom, tvEmail, tvFormationNiveau;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvNomPrenom = view.findViewById(R.id.tvNomPrenom);
            tvEmail = view.findViewById(R.id.tvEmail);
            tvFormationNiveau = view.findViewById(R.id.tvFormationNiveau);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}