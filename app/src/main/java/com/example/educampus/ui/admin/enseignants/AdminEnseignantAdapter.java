package com.example.educampus.ui.admin.enseignants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.entity.Enseignant;

import java.util.List;

public class AdminEnseignantAdapter extends RecyclerView.Adapter<AdminEnseignantAdapter.ViewHolder> {

    private List<Enseignant> enseignants;
    private final OnEnseignantActionListener listener;

    public interface OnEnseignantActionListener {
        void onEdit(Enseignant enseignant);
        void onDelete(Enseignant enseignant);
    }

    public AdminEnseignantAdapter(List<Enseignant> enseignants, OnEnseignantActionListener listener) {
        this.enseignants = enseignants;
        this.listener = listener;
    }

    public void updateList(List<Enseignant> newList) {
        this.enseignants = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_enseignant, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Enseignant e = enseignants.get(position);
        holder.tvNomPrenom.setText(e.getNom() + " " + e.getPrenom());
        holder.tvSpecialite.setText(e.getSpecialite());
        holder.tvContact.setText(e.getEmail() + " | " + e.getTelephone());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(e));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(e));
    }

    @Override
    public int getItemCount() {
        return enseignants != null ? enseignants.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomPrenom, tvSpecialite, tvContact;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvNomPrenom = view.findViewById(R.id.tvNomPrenom);
            tvSpecialite = view.findViewById(R.id.tvSpecialite);
            tvContact = view.findViewById(R.id.tvContact);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}