package com.example.educampus.ui.admin.emploi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.model.EmploiDetails;

import java.util.List;

public class AdminEmploiAdapter extends RecyclerView.Adapter<AdminEmploiAdapter.ViewHolder> {

    private List<EmploiDetails> list;
    private final OnEmploiActionListener listener;

    public interface OnEmploiActionListener {
        void onEdit(EmploiDetails details);
        void onDelete(EmploiDetails details);
    }

    public AdminEmploiAdapter(List<EmploiDetails> list, OnEmploiActionListener listener) {
        this.list = list;
        this.listener = listener;
    }

    public void updateList(List<EmploiDetails> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_emploi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmploiDetails details = list.get(position);
        String coursName = details.cours != null ? details.cours.getNom() : "Cours inconnu";
        holder.tvCoursName.setText(coursName);
        holder.tvDateTime.setText(details.emploi.getDate() + " | " + details.emploi.getHeureDebut() + " - " + details.emploi.getHeureFin());
        holder.tvSalleType.setText(details.emploi.getSalle() + " - " + details.emploi.getTypeSeance());

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(details));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(details));
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoursName, tvDateTime, tvSalleType;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvCoursName = view.findViewById(R.id.tvCoursName);
            tvDateTime = view.findViewById(R.id.tvDateTime);
            tvSalleType = view.findViewById(R.id.tvSalleType);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}