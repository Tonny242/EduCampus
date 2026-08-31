package com.example.educampus.ui.emploi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmploiActivity extends AppCompatActivity {

    // Ordre d'affichage des jours dans la semaine
    private static final List<String> ORDRE_JOURS = Arrays.asList(
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_emploi);

        AppDatabase db = DatabaseProvider.getDatabase(this);

        MaterialButton btnRetour = findViewById(R.id.btnRetourDashboard);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewEmploi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<EmploiDuTemps> seances = db.emploiDuTempsDao().getAll();

        // Tri par ordre de jour de la semaine (Lundi -> Vendredi)
        seances.sort((a, b) -> {
            int indexA = ORDRE_JOURS.indexOf(a.getDate());
            int indexB = ORDRE_JOURS.indexOf(b.getDate());
            return Integer.compare(indexA, indexB);
        });

        List<SeanceDisplay> displayList = new ArrayList<>();

        for (EmploiDuTemps seance : seances) {
            Cours cours = db.coursDao().getById(seance.getCoursId());

            String nomCours = cours != null ? cours.getNom() : "Cours inconnu";
            String nomEnseignant = "Non assigné";

            if (cours != null) {
                Enseignant enseignant = db.enseignantDao().getById(cours.getEnseignantId());
                if (enseignant != null) {
                    nomEnseignant = enseignant.getPrenom() + " " + enseignant.getNom();
                }
            }

            displayList.add(new SeanceDisplay(
                    seance.getDate(),
                    seance.getHeureDebut(),
                    seance.getHeureFin(),
                    nomCours,
                    seance.getSalle(),
                    nomEnseignant,
                    seance.getTypeSeance()
            ));
        }

        recyclerView.setAdapter(new EmploiAdapter(displayList));

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(
                    EmploiActivity.this,
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
}