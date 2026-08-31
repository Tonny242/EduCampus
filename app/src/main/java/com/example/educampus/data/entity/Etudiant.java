package com.example.educampus.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "etudiants",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Formation.class,
                        parentColumns = "id",
                        childColumns = "formationId",
                        onDelete = ForeignKey.RESTRICT
                )
        }
)
public class Etudiant {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private int formationId;
    private String niveau;

    public Etudiant() {
    }

    public Etudiant(int userId, int formationId, String niveau) {
        this.userId = userId;
        this.formationId = formationId;
        this.niveau = niveau;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFormationId() {
        return formationId;
    }

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}