package com.example.educampus.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "emploi_du_temps",
        foreignKeys = {
                @ForeignKey(
                        entity = Cours.class,
                        parentColumns = "id",
                        childColumns = "coursId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class EmploiDuTemps {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int coursId;
    private String date;
    private String heureDebut;
    private String heureFin;
    private String salle;
    private String typeSeance;

    public EmploiDuTemps() {
    }

    public EmploiDuTemps(int coursId, String date,
                         String heureDebut, String heureFin,
                         String salle, String typeSeance) {
        this.coursId = coursId;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.salle = salle;
        this.typeSeance = typeSeance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoursId() {
        return coursId;
    }

    public void setCoursId(int coursId) {
        this.coursId = coursId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }
}