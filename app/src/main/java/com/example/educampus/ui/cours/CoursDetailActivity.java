package com.example.educampus.ui.cours;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Enseignant;
import com.google.android.material.button.MaterialButton;

public class CoursDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cours_detail);

        AppDatabase db = DatabaseProvider.getDatabase(this);

        int coursId = getIntent().getIntExtra(CoursActivity.EXTRA_COURS_ID, -1);

        MaterialButton btnRetour = findViewById(R.id.btnRetourCours);
        TextView tvNom = findViewById(R.id.tvDetailNom);
        TextView tvCode = findViewById(R.id.tvDetailCode);
        TextView tvEnseignant = findViewById(R.id.tvDetailEnseignant);
        TextView tvVolumeHoraire = findViewById(R.id.tvDetailVolumeHoraire);
        TextView tvSemestre = findViewById(R.id.tvDetailSemestre);
        TextView tvCoefficient = findViewById(R.id.tvDetailCoefficient);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);

        btnRetour.setOnClickListener(v -> finish());

        if (coursId == -1) {
            tvNom.setText("Cours introuvable");
            return;
        }

        Cours cours = db.coursDao().getById(coursId);

        if (cours == null) {
            tvNom.setText("Cours introuvable");
            return;
        }

        tvNom.setText(cours.getNom());
        tvCode.setText(
                cours.getCode() != null ? cours.getCode() : "Non renseigné"
        );
        tvVolumeHoraire.setText(
                cours.getVolumeHoraire() > 0
                        ? cours.getVolumeHoraire() + " heures"
                        : "Non renseigné"
        );
        tvSemestre.setText(
                cours.getSemestre() != null ? cours.getSemestre() : "Non renseigné"
        );
        tvCoefficient.setText(
                cours.getCoefficient() > 0
                        ? String.valueOf(cours.getCoefficient())
                        : "Non renseigné"
        );
        tvDescription.setText(
                cours.getDescription() != null
                        ? cours.getDescription()
                        : "Aucune description disponible"
        );

        Enseignant enseignant = db.enseignantDao().getById(cours.getEnseignantId());

        if (enseignant != null) {
            tvEnseignant.setText(
                    enseignant.getPrenom() + " " + enseignant.getNom()
            );
        } else {
            tvEnseignant.setText("Non assigné");
        }
    }
}