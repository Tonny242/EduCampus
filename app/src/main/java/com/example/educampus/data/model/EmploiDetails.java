package com.example.educampus.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.EmploiDuTemps;

public class EmploiDetails {
    @Embedded
    public EmploiDuTemps emploi;

    @Relation(
            parentColumn = "coursId",
            entityColumn = "id"
    )
    public Cours cours;
}