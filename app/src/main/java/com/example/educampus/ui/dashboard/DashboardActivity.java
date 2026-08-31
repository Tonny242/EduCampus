package com.example.educampus.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Annonce;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.ui.absences.AbsencesActivity;
import com.example.educampus.ui.annonces.AnnonceActivity;
import com.example.educampus.ui.auth.LoginActivity;
import com.example.educampus.ui.cours.CoursActivity;
import com.example.educampus.ui.emploi.EmploiActivity;
import com.example.educampus.ui.notes.NotesActivity;
import com.example.educampus.ui.recherche.SearchResult;
import com.example.educampus.ui.recherche.SearchResultAdapter;
import com.example.educampus.utils.NotificationHelper;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private MaterialButton btnCours;
    private MaterialButton btnEmploi;
    private MaterialButton btnNotes;
    private MaterialButton btnAbsences;
    private MaterialButton btnAnnonces;
    private MaterialButton btnProfil;
    private MaterialButton btnLogout;

    private EditText etRecherche;
    private RecyclerView recyclerViewRecherche;
    private AppDatabase db;

    private final ActivityResultLauncher<String> permissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                // Que la permission soit accordée ou non, l'app continue de fonctionner normalement.
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vérification de la session et du rôle
        int userId = SessionManager.getUserId(this);
        String role = SessionManager.getUserRole(this);

        if (userId == -1) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        if (SessionManager.ROLE_ADMIN.equals(role)) {
            startActivity(new Intent(this, com.example.educampus.ui.admin.AdminDashboardActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_dashboard);

        db = DatabaseProvider.getDatabase(this);

        NotificationHelper.createNotificationChannel(this);
        demanderPermissionNotificationSiNecessaire();

        btnCours = findViewById(R.id.btnCours);
        btnEmploi = findViewById(R.id.btnEmploi);
        btnNotes = findViewById(R.id.btnNotes);
        btnAbsences = findViewById(R.id.btnAbsences);
        btnAnnonces = findViewById(R.id.btnAnnonces);
        btnProfil = findViewById(R.id.btnProfil);
        btnLogout = findViewById(R.id.btnLogout);

        etRecherche = findViewById(R.id.etRecherche);
        recyclerViewRecherche = findViewById(R.id.recyclerViewRecherche);
        recyclerViewRecherche.setLayoutManager(new LinearLayoutManager(this));

        etRecherche.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                effectuerRecherche(s.toString().trim());
            }
        });

        // Cours
        btnCours.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    CoursActivity.class
            );

            startActivity(intent);
        });

        // Emploi du temps
        btnEmploi.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    EmploiActivity.class
            );

            startActivity(intent);
        });

        // Notes
        btnNotes.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    NotesActivity.class
            );

            startActivity(intent);
        });

        // Absences
        btnAbsences.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    AbsencesActivity.class
            );

            startActivity(intent);
        });

        // Annonces
        btnAnnonces.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    AnnonceActivity.class
            );

            startActivity(intent);
        });

        // Profil
        btnProfil.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    com.example.educampus.ui.profil.ProfilActivity.class
            );

            startActivity(intent);
        });

        // Déconnexion
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    DashboardActivity.this,
                    LoginActivity.class
            );

            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }

    private void effectuerRecherche(String requete) {

        if (requete.isEmpty()) {
            recyclerViewRecherche.setVisibility(android.view.View.GONE);
            recyclerViewRecherche.setAdapter(null);
            return;
        }

        List<SearchResult> resultats = new ArrayList<>();

        List<Cours> cours = db.coursDao().search(requete);
        for (Cours c : cours) {
            resultats.add(new SearchResult(
                    "Cours",
                    c.getNom(),
                    "Code : " + c.getCode() + " · Semestre " + c.getSemestre()
            ));
        }

        List<Enseignant> enseignants = db.enseignantDao().search(requete);
        for (Enseignant e : enseignants) {
            resultats.add(new SearchResult(
                    "Enseignant",
                    e.getPrenom() + " " + e.getNom(),
                    e.getSpecialite()
            ));
        }

        List<Annonce> annonces = db.annonceDao().search(requete);
        for (Annonce a : annonces) {
            resultats.add(new SearchResult(
                    "Annonce",
                    a.getTitre(),
                    a.getDate() + " · " + a.getAuteur()
            ));
        }

        recyclerViewRecherche.setVisibility(android.view.View.VISIBLE);
        recyclerViewRecherche.setAdapter(new SearchResultAdapter(resultats, resultat -> {

            Intent intent;

            if (resultat.getType().equals("Annonce")) {
                intent = new Intent(DashboardActivity.this, AnnonceActivity.class);
            } else {
                intent = new Intent(DashboardActivity.this, CoursActivity.class);
            }

            startActivity(intent);
        }));
    }

    private void demanderPermissionNotificationSiNecessaire() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            boolean dejaAccordee = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED;

            if (!dejaAccordee) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
        // En dessous d'Android 13, aucune permission n'est nécessaire.
    }
}