package com.example.educampus.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "annonces")
public class Annonce {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titre;
    private String contenu;
    private String date;
    private String auteur;
    private String image;
    private boolean importante;
    private String categorie;

    public Annonce() {
    }

    public Annonce(String titre, String contenu,
                   String date, String auteur,
                   String image, boolean importante,
                   String categorie) {
        this.titre = titre;
        this.contenu = contenu;
        this.date = date;
        this.auteur = auteur;
        this.image = image;
        this.importante = importante;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isImportante() {
        return importante;
    }

    public void setImportante(boolean importante) {
        this.importante = importante;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}