package com.example.educampus.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "notes",
        foreignKeys = {
                @ForeignKey(
                        entity = Etudiant.class,
                        parentColumns = "id",
                        childColumns = "etudiantId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Cours.class,
                        parentColumns = "id",
                        childColumns = "coursId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int etudiantId;
    private int coursId;
    private double cc;
    private double examen;
    private double moyenne;
    private double coefficient;
    private String semestre;

    public Note() {
    }

    public Note(int etudiantId, int coursId,
                double cc, double examen,
                double moyenne, double coefficient,
                String semestre) {
        this.etudiantId = etudiantId;
        this.coursId = coursId;
        this.cc = cc;
        this.examen = examen;
        this.moyenne = moyenne;
        this.coefficient = coefficient;
        this.semestre = semestre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(int etudiantId) {
        this.etudiantId = etudiantId;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public double getCc() {
        return cc;
    }

    public void setCc(double cc) {
        this.cc = cc;
    }

    public double getExamen() {
        return examen;
    }

    public void setExamen(double examen) {
        this.examen = examen;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}