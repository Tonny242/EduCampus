package com.example.educampus.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.data.entity.Formation;

public class CoursDetails {
    @Embedded
    public Cours cours;

    @Relation(
            parentColumn = "enseignantId",
            entityColumn = "id"
    )
    public Enseignant enseignant;

    @Relation(
            parentColumn = "formationId",
            entityColumn = "id"
    )
    public Formation formation;
}