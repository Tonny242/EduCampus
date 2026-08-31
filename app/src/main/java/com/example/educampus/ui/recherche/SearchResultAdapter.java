package com.example.educampus.ui.recherche;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder> {

    public interface OnResultClickListener {
        void onResultClick(SearchResult resultat);
    }

    private final List<SearchResult> resultats;
    private final OnResultClickListener listener;

    public SearchResultAdapter(List<SearchResult> resultats, OnResultClickListener listener) {
        this.resultats = resultats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_resultat_recherche, parent, false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        SearchResult resultat = resultats.get(position);

        holder.tvType.setText(resultat.getType());
        holder.tvTitre.setText(resultat.getTitre());
        holder.tvSousTitre.setText(resultat.getSousTitre());

        holder.itemView.setOnClickListener(v -> listener.onResultClick(resultat));
    }

    @Override
    public int getItemCount() {
        return resultats.size();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {
        TextView tvType, tvTitre, tvSousTitre;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tvTypeResultat);
            tvTitre = itemView.findViewById(R.id.tvTitreResultat);
            tvSousTitre = itemView.findViewById(R.id.tvSousTitreResultat);
        }
    }
}