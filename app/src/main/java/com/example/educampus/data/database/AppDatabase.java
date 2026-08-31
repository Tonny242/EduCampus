package com.example.educampus.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.educampus.data.dao.AbsenceDao;
import com.example.educampus.data.dao.AnnonceDao;
import com.example.educampus.data.dao.CoursDao;
import com.example.educampus.data.dao.EmploiDuTempsDao;
import com.example.educampus.data.dao.EnseignantDao;
import com.example.educampus.data.dao.EtudiantDao;
import com.example.educampus.data.dao.FormationDao;
import com.example.educampus.data.dao.NoteDao;
import com.example.educampus.data.dao.UserDao;

import com.example.educampus.data.entity.Absence;
import com.example.educampus.data.entity.Annonce;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Formation;
import com.example.educampus.data.entity.Note;
import com.example.educampus.data.entity.User;

@Database(
        entities = {
                User.class,
                Formation.class,
                Etudiant.class,
                Enseignant.class,
                Cours.class,
                EmploiDuTemps.class,
                Note.class,
                Absence.class,
                Annonce.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract FormationDao formationDao();

    public abstract EtudiantDao etudiantDao();

    public abstract EnseignantDao enseignantDao();

    public abstract CoursDao coursDao();

    public abstract EmploiDuTempsDao emploiDuTempsDao();

    public abstract NoteDao noteDao();

    public abstract AbsenceDao absenceDao();

    public abstract AnnonceDao annonceDao();
}