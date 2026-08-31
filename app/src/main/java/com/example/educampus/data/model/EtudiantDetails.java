package com.example.educampus.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Formation;
import com.example.educampus.data.entity.User;

public class EtudiantDetails {
    @Embedded
    public Etudiant etudiant;

    @Relation(
            parentColumn = "userId",
            entityColumn = "id"
    )
    public User user;

    @Relation(
            parentColumn = "formationId",
            entityColumn = "id"
    )
    public Formation formation;
}