package com.example.educampus.utils;

import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Formation;
import com.example.educampus.data.entity.User;

import java.util.List;

public class EtudiantHelper {

    public static int getOrCreateEtudiantId(AppDatabase db, User user) {

        Etudiant etudiant = db.etudiantDao().getByUserId(user.getId());

        if (etudiant != null) {
            return etudiant.getId();
        }

        int formationId = getOrCreateFormationId(db, user.getFormation());

        return (int) db.etudiantDao().insert(
                new Etudiant(user.getId(), formationId, user.getNiveau())
        );
    }

    private static int getOrCreateFormationId(AppDatabase db, String nomFormation) {

        List<Formation> formations = db.formationDao().getAll();

        for (Formation f : formations) {
            if (f.getNom().equalsIgnoreCase(nomFormation)) {
                return f.getId();
            }
        }

        return (int) db.formationDao().insert(
                new Formation(nomFormation, "")
        );
    }
}