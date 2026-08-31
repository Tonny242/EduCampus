package com.example.educampus.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;

import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final List<NoteDisplay> notes;

    public NoteAdapter(List<NoteDisplay> notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        NoteDisplay note = notes.get(position);

        holder.tvMatiere.setText(note.getNomCours());
        holder.tvSemestre.setText(note.getSemestre());
        holder.tvCc.setText(String.format(Locale.FRANCE, "Contrôle continu : %.1f / 20", note.getCc()));
        holder.tvExamen.setText(String.format(Locale.FRANCE, "Examen : %.1f / 20", note.getExamen()));
        holder.tvMoyenne.setText(String.format(Locale.FRANCE, "Moyenne : %.1f / 20", note.getMoyenne()));
        holder.tvCoefficient.setText("Coefficient : " + note.getCoefficient());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvMatiere, tvCc, tvExamen, tvMoyenne, tvCoefficient, tvSemestre;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMatiere = itemView.findViewById(R.id.tvMatiere);
            tvCc = itemView.findViewById(R.id.tvCc);
            tvExamen = itemView.findViewById(R.id.tvExamen);
            tvMoyenne = itemView.findViewById(R.id.tvMoyenne);
            tvCoefficient = itemView.findViewById(R.id.tvCoefficient);
            tvSemestre = itemView.findViewById(R.id.tvSemestre);
        }
    }
}