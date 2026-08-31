package com.example.educampus.data.database;

import android.content.Context;

import androidx.room.Room;

import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.data.entity.Formation;

import java.util.List;

public class DatabaseProvider {

    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "educampus_database"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

            seedDatabaseIfEmpty(INSTANCE);
        }

        return INSTANCE;
    }

    private static void seedDatabaseIfEmpty(AppDatabase db) {

        // On vérifie et peuple chaque table indépendamment,
        // pour ne jamais bloquer un seed à cause d'une autre table déjà remplie.

        int idFormation;
        if (db.formationDao().getAll().isEmpty()) {
            idFormation = (int) db.formationDao().insert(
                    new Formation("MIAGE", "Master Informatique Appliquée à la Gestion des Entreprises")
            );
        } else {
            idFormation = db.formationDao().getAll().get(0).getId();
        }

        int idDiengMansour, idDiopAmadou, idSabalyAdama;
        if (db.enseignantDao().getAll().isEmpty()) {
            idDiengMansour = (int) db.enseignantDao().insert(new Enseignant("Mansour", "DIENG", "", "", ""));
            idDiopAmadou = (int) db.enseignantDao().insert(new Enseignant("Amadou", "DIOP", "", "", ""));
            idSabalyAdama = (int) db.enseignantDao().insert(new Enseignant("Adama", "SABALY", "", "", ""));
        } else {
            List<Enseignant> ens = db.enseignantDao().getAll();
            idDiengMansour = ens.get(0).getId();
            idDiopAmadou = ens.get(1).getId();
            idSabalyAdama = ens.get(2).getId();
        }

        int idAndroidStudio, idTheoriesReseaux, idOracle, idSecuriteInfo, idInterconnexion;

        if (db.coursDao().getAll().isEmpty()) {
            idAndroidStudio = (int) db.coursDao().insert(new Cours(
                    "Android Studio", "INFO300", idDiengMansour, idFormation, 4,
                    "Développement d'applications mobiles Android natif.", "Semestre 1", 4.0
            ));
            idTheoriesReseaux = (int) db.coursDao().insert(new Cours(
                    "Théories des réseaux", "INFO301", idDiopAmadou, idFormation, 4,
                    "Fondamentaux des réseaux informatiques et protocoles de communication.", "Semestre 2", 4.0
            ));
            idOracle = (int) db.coursDao().insert(new Cours(
                    "Oracle", "NFO302", idSabalyAdama, idFormation, 4,
                    "Administration et requêtes avancées sur bases de données Oracle.", "Semestre 2", 4.0
            ));
            idSecuriteInfo = (int) db.coursDao().insert(new Cours(
                    "Sécurité informatique / MIAGE / Base de données avancées", "NFO303", idDiengMansour, idFormation, 4,
                    "Sécurité des systèmes d'information et gestion avancée des bases de données.", "Semestre 3", 3.0
            ));
            idInterconnexion = (int) db.coursDao().insert(new Cours(
                    "Interconnexion des réseaux", "NFO304", idDiopAmadou, idFormation, 4,
                    "Configuration et interconnexion de réseaux d'entreprise.", "Semestre 2", 4.0
            ));
        } else {
            List<Cours> coursExistants = db.coursDao().getAll();
            idAndroidStudio = coursExistants.get(0).getId();
            idTheoriesReseaux = coursExistants.get(1).getId();
            idOracle = coursExistants.get(2).getId();
            idSecuriteInfo = coursExistants.get(3).getId();
            idInterconnexion = coursExistants.get(4).getId();
        }

        if (db.emploiDuTempsDao().getAll().isEmpty()) {
            db.emploiDuTempsDao().insert(new EmploiDuTemps(idAndroidStudio, "Lundi", "17h00", "21h00", "Salle 21", "Pratique"));
            db.emploiDuTempsDao().insert(new EmploiDuTemps(idTheoriesReseaux, "Mardi", "17h00", "21h00", "Salle 21", "Pratique"));
            db.emploiDuTempsDao().insert(new EmploiDuTemps(idOracle, "Mercredi", "17h00", "21h00", "Salle 3", "Pratique"));
            db.emploiDuTempsDao().insert(new EmploiDuTemps(idSecuriteInfo, "Jeudi", "17h00", "21h00", "Salle 3", "Théorique"));
            db.emploiDuTempsDao().insert(new EmploiDuTemps(idInterconnexion, "Vendredi", "17h00", "21h00", "Salle 21", "Pratique"));
        }
    }
}