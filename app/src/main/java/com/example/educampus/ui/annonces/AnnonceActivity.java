package com.example.educampus.ui.annonces;

import android.content.Intent;
import android.os.Bundle;
import com.example.educampus.utils.NotificationHelper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Annonce;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class AnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_annonces);

        AppDatabase db = DatabaseProvider.getDatabase(this);

        MaterialButton btnRetour = findViewById(R.id.btnRetourDashboard);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewAnnonces);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        seedAnnoncesIfEmpty(db);

        List<Annonce> annonces = db.annonceDao().getAll(); // déjà trié DESC par id (plus récent en premier)

        List<AnnonceDisplay> displayList = new ArrayList<>();

        for (Annonce annonce : annonces) {
            displayList.add(new AnnonceDisplay(
                    annonce.getTitre(),
                    annonce.getContenu(),
                    annonce.getDate(),
                    annonce.getAuteur(),
                    annonce.getCategorie()
            ));
        }

        recyclerView.setAdapter(new AnnonceAdapter(displayList));

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AnnonceActivity.this,
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

    private void seedAnnoncesIfEmpty(AppDatabase db) {

        if (!db.annonceDao().getAll().isEmpty()) {
            return;
        }

        db.annonceDao().insert(new Annonce(
                "Changement de salle - Oracle",
                "Le cours d'Oracle de mercredi aura exceptionnellement lieu en Salle 5 au lieu de la Salle 3.",
                "28/08/2026",
                "Scolarité",
                "",
                true,
                "Changement de salle"
        ));

        db.annonceDao().insert(new Annonce(
                "Report du cours d'Interconnexion des réseaux",
                "Le cours de vendredi est reporté au lundi suivant, même horaire, même salle.",
                "27/08/2026",
                "M. DIOP Amadou",
                "",
                false,
                "Report de cours"
        ));

        db.annonceDao().insert(new Annonce(
                "Examen final - Sécurité informatique",
                "L'examen final se tiendra le 15 septembre 2026 à 9h00 en Salle 3. Documents non autorisés.",
                "25/08/2026",
                "M. DIENG Mansour",
                "",
                true,
                "Examen"
        ));

        db.annonceDao().insert(new Annonce(
                "Réunion pédagogique de rentrée",
                "Une réunion d'information sur le semestre à venir se tiendra pour tous les étudiants MIAGE.",
                "20/08/2026",
                "Direction pédagogique",
                "",
                false,
                "Réunion"
        ));

        db.annonceDao().insert(new Annonce(
                "Journée portes ouvertes EduCampus",
                "Venez découvrir les projets étudiants et rencontrer les enseignants le samedi 5 septembre.",
                "18/08/2026",
                "Direction",
                "",
                false,
                "Événement"
        ));

        db.annonceDao().insert(new Annonce(
                "Fermeture exceptionnelle du campus",
                "Le campus sera fermé le 1er septembre 2026 pour travaux de maintenance électrique.",
                "15/08/2026",
                "Administration",
                "",
                true,
                "Fermeture exceptionnelle"
        ));

        // Simulation de notification locale pour la nouvelle annonce la plus importante
        NotificationHelper.showAnnonceNotification(
                this,
                "🔔 Nouvelle annonce",
                "Le cours d'Oracle de mercredi aura exceptionnellement lieu en Salle 5."
        );
    }
}