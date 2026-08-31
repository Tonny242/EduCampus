package com.example.educampus.ui.admin.etudiants;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.model.EtudiantDetails;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminEtudiantsActivity extends AppCompatActivity {

    private RecyclerView rvEtudiants;
    private AdminEtudiantAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sécurité
        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_etudiants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = DatabaseProvider.getDatabase(this);
        rvEtudiants = findViewById(R.id.rvEtudiants);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddEtudiant);

        rvEtudiants.setLayoutManager(new LinearLayoutManager(this));
        
        loadEtudiants();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminEtudiantFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadEtudiants() {
        new Thread(() -> {
            List<EtudiantDetails> list = db.etudiantDao().getAllWithDetails();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new AdminEtudiantAdapter(list, new AdminEtudiantAdapter.OnEtudiantActionListener() {
                        @Override
                        public void onEdit(EtudiantDetails etudiant) {
                            Intent intent = new Intent(AdminEtudiantsActivity.this, AdminEtudiantFormActivity.class);
                            intent.putExtra("etudiant_id", etudiant.etudiant.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(EtudiantDetails etudiant) {
                            showDeleteConfirmation(etudiant);
                        }
                    });
                    rvEtudiants.setAdapter(adapter);
                } else {
                    adapter.updateList(list);
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(EtudiantDetails etudiant) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer cet étudiant ?\nCela supprimera également son compte utilisateur.")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    new Thread(() -> {
                        // Supprimer l'utilisateur supprimera l'étudiant par CASCADE
                        db.userDao().delete(etudiant.user);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Étudiant supprimé", Toast.LENGTH_SHORT).show();
                            loadEtudiants();
                        });
                    }).start();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEtudiants();
    }
}