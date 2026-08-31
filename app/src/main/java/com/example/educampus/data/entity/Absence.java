package com.example.educampus.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "absences",
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
                ),
                @ForeignKey(
                        entity = Enseignant.class,
                        parentColumns = "id",
                        childColumns = "enseignantId",
                        onDelete = ForeignKey.RESTRICT
                )
        }
)
public class Absence {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int etudiantId;
    private int coursId;
    private int enseignantId;
    private String date;
    private String motif;
    private String statut;

    public Absence() {
    }

    public Absence(int etudiantId, int coursId,
                   int enseignantId, String date,
                   String motif, String statut) {
        this.etudiantId = etudiantId;
        this.coursId = coursId;
        this.enseignantId = enseignantId;
        this.date = date;
        this.motif = motif;
        this.statut = statut;
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

    public int getEnseignantId() {
        return enseignantId;
    }

    public void setEnseignantId(int enseignantId) {
        this.enseignantId = enseignantId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}