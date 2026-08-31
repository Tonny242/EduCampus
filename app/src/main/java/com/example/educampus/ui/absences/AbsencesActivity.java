package com.example.educampus.ui.absences;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Absence;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.data.entity.User;
import com.example.educampus.ui.auth.LoginActivity;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.example.educampus.utils.EtudiantHelper;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AbsencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_absences);

        AppDatabase db = DatabaseProvider.getDatabase(this);

        MaterialButton btnRetour = findViewById(R.id.btnRetourDashboard);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAbsences);
        TextView tvTotal = findViewById(R.id.tvTotalAbsences);
        TextView tvJustifiees = findViewById(R.id.tvJustifiees);
        TextView tvNonJustifiees = findViewById(R.id.tvNonJustifiees);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int userId = SessionManager.getUserId(this);

        if (userId == -1) {
            redirectToLogin();
            return;
        }

        User user = db.userDao().getById(userId);

        if (user == null) {
            redirectToLogin();
            return;
        }

        int etudiantId = EtudiantHelper.getOrCreateEtudiantId(db, user);

        seedAbsencesIfEmpty(db, etudiantId);

        List<Absence> absences = db.absenceDao().getByEtudiantId(etudiantId);

        List<AbsenceDisplay> displayList = new ArrayList<>();

        int nbJustifiees = 0;
        int nbNonJustifiees = 0;

        for (Absence absence : absences) {
            Cours cours = db.coursDao().getById(absence.getCoursId());
            String nomCours = cours != null ? cours.getNom() : "Cours inconnu";

            Enseignant enseignant = db.enseignantDao().getById(absence.getEnseignantId());
            String nomEnseignant = enseignant != null
                    ? enseignant.getPrenom() + " " + enseignant.getNom()
                    : "Non assigné";

            displayList.add(new AbsenceDisplay(
                    absence.getDate(),
                    nomCours,
                    nomEnseignant,
                    absence.getMotif(),
                    absence.getStatut()
            ));

            if (absence.getStatut().equalsIgnoreCase("Justifiée")) {
                nbJustifiees++;
            } else {
                nbNonJustifiees++;
            }
        }

        recyclerView.setAdapter(new AbsenceAdapter(displayList));

        tvTotal.setText("Nombre total d'absences : " + absences.size());
        tvJustifiees.setText("Absences justifiées : " + nbJustifiees);
        tvNonJustifiees.setText("Absences non justifiées : " + nbNonJustifiees);

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AbsencesActivity.this,
                    DashboardActivity.class
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void seedAbsencesIfEmpty(AppDatabase db, int etudiantId) {

        List<Absence> existing = db.absenceDao().getByEtudiantId(etudiantId);

        if (!existing.isEmpty()) {
            return;
        }

        List<Cours> tousLesCours = db.coursDao().getAll();

        Cours theoriesReseaux = trouverCoursParNom(tousLesCours, "Théories des réseaux");
        Cours oracle = trouverCoursParNom(tousLesCours, "Oracle");

        if (theoriesReseaux != null) {
            db.absenceDao().insert(new Absence(
                    etudiantId,
                    theoriesReseaux.getId(),
                    theoriesReseaux.getEnseignantId(),
                    "12/08/2026",
                    "Maladie",
                    "Justifiée"
            ));
        }

        if (oracle != null) {
            db.absenceDao().insert(new Absence(
                    etudiantId,
                    oracle.getId(),
                    oracle.getEnseignantId(),
                    "19/08/2026",
                    "Non renseigné",
                    "Non justifiée"
            ));
        }
    }

    private Cours trouverCoursParNom(List<Cours> cours, String nom) {
        for (Cours c : cours) {
            if (c.getNom().equals(nom)) {
                return c;
            }
        }
        return null;
    }
}