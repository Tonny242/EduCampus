package com.example.educampus.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "cours",
        foreignKeys = {
                @ForeignKey(
                        entity = Enseignant.class,
                        parentColumns = "id",
                        childColumns = "enseignantId",
                        onDelete = ForeignKey.RESTRICT
                ),
                @ForeignKey(
                        entity = Formation.class,
                        parentColumns = "id",
                        childColumns = "formationId",
                        onDelete = ForeignKey.RESTRICT
                )
        }
)
public class Cours {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nom;
    private String code;
    private int enseignantId;
    private int formationId;
    private int volumeHoraire;
    private String description;
    private String semestre;
    private double coefficient;

    public Cours() {
    }

    public Cours(String nom, String code, int enseignantId,
                 int formationId, int volumeHoraire,
                 String description, String semestre,
                 double coefficient) {
        this.nom = nom;
        this.code = code;
        this.enseignantId = enseignantId;
        this.formationId = formationId;
        this.volumeHoraire = volumeHoraire;
        this.description = description;
        this.semestre = semestre;
        this.coefficient = coefficient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }

    public int getFormationId() {
        return formationId;
    }

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

    public int getVolumeHoraire() {
        return volumeHoraire;
    }

    public void setVolumeHoraire(int volumeHoraire) {
        this.volumeHoraire = volumeHoraire;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}