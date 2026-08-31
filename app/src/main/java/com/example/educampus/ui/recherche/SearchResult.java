package com.example.educampus.ui.recherche;

public class SearchResult {

    private final String type; // "Cours", "Enseignant" ou "Annonce"
    private final String titre;
    private final String sousTitre;

    public SearchResult(String type, String titre, String sousTitre) {
        this.type = type;
        this.titre = titre;
        this.sousTitre = sousTitre;
    }

    public String getType() { return type; }
    public String getTitre() { return titre; }
    public String getSousTitre() { return sousTitre; }
}