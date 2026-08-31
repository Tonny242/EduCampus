package com.example.educampus.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Note;
import com.example.educampus.data.entity.User;

public class NoteDetails {
    @Embedded
    public Note note;

    @Relation(
            parentColumn = "etudiantId",
            entityColumn = "id"
    )
    public Etudiant etudiant;

    @Relation(
            parentColumn = "coursId",
            entityColumn = "id"
    )
    public Cours cours;
}