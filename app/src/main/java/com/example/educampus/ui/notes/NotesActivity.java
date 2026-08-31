package com.example.educampus.ui.notes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Note;
import com.example.educampus.data.entity.User;
import com.example.educampus.ui.auth.LoginActivity;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.example.educampus.utils.EtudiantHelper;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notes);

        AppDatabase db = DatabaseProvider.getDatabase(this);

        MaterialButton btnRetour = findViewById(R.id.btnRetourDashboard);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotes);
        TextView tvMoyenneGenerale = findViewById(R.id.tvMoyenneGenerale);
        TextView tvStatut = findViewById(R.id.tvStatut);

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

        seedNotesIfEmpty(db, etudiantId);

        List<Note> notes = db.noteDao().getByEtudiantId(etudiantId);

        List<NoteDisplay> displayList = new ArrayList<>();

        double sommeMoyennesPonderees = 0;
        double sommeCoefficients = 0;

        for (Note note : notes) {
            Cours cours = db.coursDao().getById(note.getCoursId());
            String nomCours = cours != null ? cours.getNom() : "Cours inconnu";

            displayList.add(new NoteDisplay(
                    nomCours,
                    note.getCc(),
                    note.getExamen(),
                    note.getMoyenne(),
                    note.getCoefficient(),
                    note.getSemestre()
            ));

            sommeMoyennesPonderees += note.getMoyenne() * note.getCoefficient();
            sommeCoefficients += note.getCoefficient();
        }

        recyclerView.setAdapter(new NoteAdapter(displayList));

        if (sommeCoefficients > 0) {
            double moyenneGenerale = sommeMoyennesPonderees / sommeCoefficients;

            tvMoyenneGenerale.setText(
                    String.format(Locale.FRANCE, "Moyenne générale : %.2f / 20", moyenneGenerale)
            );

            tvStatut.setText(
                    moyenneGenerale >= 10 ? "Statut : ADMIS" : "Statut : AJOURNÉ"
            );
        } else {
            tvMoyenneGenerale.setText("Moyenne générale : —");
            tvStatut.setText("Statut : —");
        }

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(
                    NotesActivity.this,
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

    private void seedNotesIfEmpty(AppDatabase db, int etudiantId) {

        List<Note> existingNotes = db.noteDao().getByEtudiantId(etudiantId);

        if (!existingNotes.isEmpty()) {
            return;
        }

        List<Cours> tousLesCours = db.coursDao().getAll();

        for (Cours cours : tousLesCours) {

            double noteDemo = getNoteDemoParNomCours(cours.getNom());

            db.noteDao().insert(new Note(
                    etudiantId,
                    cours.getId(),
                    noteDemo,
                    noteDemo,
                    noteDemo,
                    cours.getCoefficient(),
                    cours.getSemestre()
            ));
        }
    }

    private double getNoteDemoParNomCours(String nomCours) {
        switch (nomCours) {
            case "Théories des réseaux":
                return 15;
            case "Oracle":
                return 14;
            case "Sécurité informatique / MIAGE / Base de données avancées":
                return 16;
            case "Interconnexion des réseaux":
                return 13;
            case "Android Studio":
                return 17;
            default:
                return 12;
        }
    }
}