package com.example.educampus.ui.emploi;

public class SeanceDisplay {

    private final String jour;
    private final String heureDebut;
    private final String heureFin;
    private final String nomCours;
    private final String salle;
    private final String nomEnseignant;
    private final String typeSeance;

    public SeanceDisplay(String jour, String heureDebut, String heureFin,
                         String nomCours, String salle,
                         String nomEnseignant, String typeSeance) {
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.nomCours = nomCours;
        this.salle = salle;
        this.nomEnseignant = nomEnseignant;
        this.typeSeance = typeSeance;
    }

    public String getJour() {
        return jour;
    }

    public String getHoraire() {
        return heureDebut + " - " + heureFin;
    }

    public String getNomCours() {
        return nomCours;
    }

    public String getSalle() {
        return salle;
    }

    public String getNomEnseignant() {
        return nomEnseignant;
    }

    public String getTypeSeance() {
        return typeSeance;
    }
}