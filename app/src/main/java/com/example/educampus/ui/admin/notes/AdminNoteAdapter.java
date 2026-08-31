package com.example.educampus.ui.admin.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.model.NoteDetails;

import java.util.List;
import java.util.Map;

public class AdminNoteAdapter extends RecyclerView.Adapter<AdminNoteAdapter.ViewHolder> {

    private List<NoteDetails> notes;
    private Map<Integer, String> etudiantNames;
    private final OnNoteActionListener listener;

    public interface OnNoteActionListener {
        void onEdit(NoteDetails note);
        void onDelete(NoteDetails note);
    }

    public AdminNoteAdapter(List<NoteDetails> notes, Map<Integer, String> etudiantNames, OnNoteActionListener listener) {
        this.notes = notes;
        this.etudiantNames = etudiantNames;
        this.listener = listener;
    }

    public void updateList(List<NoteDetails> newList, Map<Integer, String> newNames) {
        this.notes = newList;
        this.etudiantNames = newNames;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteDetails details = notes.get(position);
        
        String etudiantName = etudiantNames.get(details.note.getEtudiantId());
        holder.tvEtudiantName.setText(etudiantName != null ? etudiantName : "Étudiant inconnu");
        
        String coursName = details.cours != null ? details.cours.getNom() : "Cours inconnu";
        holder.tvCoursName.setText(coursName);
        
        holder.tvNoteInfo.setText(String.format("CC: %.2f | Exam: %.2f | Moy: %.2f", 
                details.note.getCc(), 
                details.note.getExamen(), 
                details.note.getMoyenne()));

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(details));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(details));
    }

    @Override
    public int getItemCount() {
        return notes != null ? notes.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEtudiantName, tvCoursName, tvNoteInfo;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View view) {
            super(view);
            tvEtudiantName = view.findViewById(R.id.tvEtudiantName);
            tvCoursName = view.findViewById(R.id.tvCoursName);
            tvNoteInfo = view.findViewById(R.id.tvNoteInfo);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}