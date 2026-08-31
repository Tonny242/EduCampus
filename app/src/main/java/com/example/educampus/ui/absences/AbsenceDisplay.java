package com.example.educampus.ui.absences;

public class AbsenceDisplay {

    private final String date;
    private final String nomCours;
    private final String nomEnseignant;
    private final String motif;
    private final String statut;

    public AbsenceDisplay(String date, String nomCours, String nomEnseignant,
                          String motif, String statut) {
        this.date = date;
        this.nomCours = nomCours;
        this.nomEnseignant = nomEnseignant;
        this.motif = motif;
        this.statut = statut;
    }

    public String getDate() { return date; }
    public String getNomCours() { return nomCours; }
    public String getNomEnseignant() { return nomEnseignant; }
    public String getMotif() { return motif; }
    public String getStatut() { return statut; }
}