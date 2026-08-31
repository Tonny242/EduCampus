package com.example.educampus.ui.notes;

public class NoteDisplay {

    private final String nomCours;
    private final double cc;
    private final double examen;
    private final double moyenne;
    private final double coefficient;
    private final String semestre;

    public NoteDisplay(String nomCours, double cc, double examen,
                       double moyenne, double coefficient, String semestre) {
        this.nomCours = nomCours;
        this.cc = cc;
        this.examen = examen;
        this.moyenne = moyenne;
        this.coefficient = coefficient;
        this.semestre = semestre;
    }

    public String getNomCours() { return nomCours; }
    public double getCc() { return cc; }
    public double getExamen() { return examen; }
    public double getMoyenne() { return moyenne; }
    public double getCoefficient() { return coefficient; }
    public String getSemestre() { return semestre; }
}