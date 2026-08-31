package com.example.educampus.ui.cours;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.entity.Cours;

import java.util.List;

public class CoursAdapter extends RecyclerView.Adapter<CoursAdapter.CoursViewHolder> {

    public interface OnCoursClickListener {
        void onCoursClick(Cours cours);
    }

    private final List<Cours> coursList;
    private final OnCoursClickListener listener;

    public CoursAdapter(List<Cours> coursList, OnCoursClickListener listener) {
        this.coursList = coursList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CoursViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cours, parent, false);
        return new CoursViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursViewHolder holder, int position) {
        Cours cours = coursList.get(position);

        holder.tvNom.setText(cours.getNom());
        holder.tvCode.setText(
                cours.getCode() != null ? cours.getCode() : "Code non renseigné"
        );

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCoursClick(cours);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursList.size();
    }

    public void updateList(List<Cours> newList) {
        coursList.clear();
        coursList.addAll(newList);
        notifyDataSetChanged();
    }

    static class CoursViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom;
        TextView tvCode;

        public CoursViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNomCours);
            tvCode = itemView.findViewById(R.id.tvCodeCours);
        }
    }
}