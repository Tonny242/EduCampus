package com.example.educampus.ui.annonces;

public class AnnonceDisplay {

    private final String titre;
    private final String contenu;
    private final String date;
    private final String auteur;
    private final String categorie;

    public AnnonceDisplay(String titre, String contenu, String date,
                          String auteur, String categorie) {
        this.titre = titre;
        this.contenu = contenu;
        this.date = date;
        this.auteur = auteur;
        this.categorie = categorie;
    }

    public String getTitre() { return titre; }
    public String getContenu() { return contenu; }
    public String getDate() { return date; }
    public String getAuteur() { return auteur; }
    public String getCategorie() { return categorie; }
}