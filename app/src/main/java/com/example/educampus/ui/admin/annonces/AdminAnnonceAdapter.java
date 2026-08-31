package com.example.educampus.ui.admin.annonces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.entity.Annonce;

import java.util.List;

public class AdminAnnonceAdapter extends RecyclerView.Adapter<AdminAnnonceAdapter.ViewHolder> {

    private List<Annonce> annonces;
    private final OnAnnonceActionListener listener;

    public interface OnAnnonceActionListener {
        void onEdit(Annonce annonce);
        void onDelete(Annonce annonce);
    }

    public AdminAnnonceAdapter(List<Annonce> annonces, OnAnnonceActionListener listener) {
        this.annonces = annonces;
        this.listener = listener;
    }

    public void updateList(List<Annonce> newList) {
        this.annonces = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_annonce, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Annonce a = annonces.get(position);
        holder.tvTitreAnnonce.setText(a.getTitre());
        holder.tvDateAuteur.setText(a.getDate() + " | " + a.getAuteur());
        holder.tvCategorie.setText(a.getCategorie());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(a));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(a));
    }

    @Override
    public int getItemCount() {
        return annonces != null ? annonces.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitreAnnonce, tvDateAuteur, tvCategorie;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvTitreAnnonce = view.findViewById(R.id.tvTitreAnnonce);
            tvDateAuteur = view.findViewById(R.id.tvDateAuteur);
            tvCategorie = view.findViewById(R.id.tvCategorie);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}