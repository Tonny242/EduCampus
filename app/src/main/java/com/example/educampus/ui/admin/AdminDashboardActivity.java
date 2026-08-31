package com.example.educampus.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.User;
import com.example.educampus.ui.auth.LoginActivity;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

public class AdminDashboardActivity extends AppCompatActivity {

    private TextView tvAdminName;
    private MaterialButton btnAdminEtudiants;
    private MaterialButton btnAdminCours;
    private MaterialButton btnAdminEnseignants;
    private MaterialButton btnAdminEmploi;
    private MaterialButton btnAdminNotes;
    private MaterialButton btnAdminAnnonces;
    private MaterialButton btnAdminLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sécurité : Vérifier si l'utilisateur est admin
        String role = SessionManager.getUserRole(this);
        if (!SessionManager.ROLE_ADMIN.equals(role)) {
            Toast.makeText(this, "Accès refusé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_dashboard);

        tvAdminName = findViewById(R.id.tvAdminName);
        btnAdminEtudiants = findViewById(R.id.btnAdminEtudiants);
        btnAdminCours = findViewById(R.id.btnAdminCours);
        btnAdminEnseignants = findViewById(R.id.btnAdminEnseignants);
        btnAdminEmploi = findViewById(R.id.btnAdminEmploi);
        btnAdminNotes = findViewById(R.id.btnAdminNotes);
        btnAdminAnnonces = findViewById(R.id.btnAdminAnnonces);
        btnAdminLogout = findViewById(R.id.btnAdminLogout);

        // Afficher le nom de l'admin
        int userId = SessionManager.getUserId(this);
        AppDatabase db = DatabaseProvider.getDatabase(this);
        new Thread(() -> {
            User user = db.userDao().getById(userId);
            if (user != null) {
                runOnUiThread(() -> tvAdminName.setText("Bienvenue, " + user.getPrenom() + " " + user.getNom()));
            }
        }).start();

        btnAdminEtudiants.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.educampus.ui.admin.etudiants.AdminEtudiantsActivity.class);
            startActivity(intent);
        });

        btnAdminCours.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.educampus.ui.admin.cours.AdminCoursActivity.class);
            startActivity(intent);
        });

        btnAdminEnseignants.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.educampus.ui.admin.enseignants.AdminEnseignantsActivity.class);
            startActivity(intent);
        });

        btnAdminEmploi.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.educampus.ui.admin.emploi.AdminEmploiActivity.class);
            startActivity(intent);
        });

        btnAdminNotes.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.educampus.ui.admin.notes.AdminNotesActivity.class);
            startActivity(intent);
        });

        btnAdminAnnonces.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.example.educampus.ui.admin.annonces.AdminAnnoncesActivity.class);
            startActivity(intent);
        });

        btnAdminLogout.setOnClickListener(v -> {
            SessionManager.clearSession(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}