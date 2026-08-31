package com.example.educampus.ui.admin.cours;

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
import com.example.educampus.data.model.CoursDetails;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminCoursActivity extends AppCompatActivity {

    private RecyclerView rvCours;
    private AdminCoursAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_cours);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = DatabaseProvider.getDatabase(this);
        rvCours = findViewById(R.id.rvCours);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddCours);

        rvCours.setLayoutManager(new LinearLayoutManager(this));
        
        loadCours();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminCoursFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadCours() {
        new Thread(() -> {
            List<CoursDetails> list = db.coursDao().getAllWithDetails();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new AdminCoursAdapter(list, new AdminCoursAdapter.OnCoursActionListener() {
                        @Override
                        public void onEdit(CoursDetails cours) {
                            Intent intent = new Intent(AdminCoursActivity.this, AdminCoursFormActivity.class);
                            intent.putExtra("cours_id", cours.cours.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(CoursDetails cours) {
                            showDeleteConfirmation(cours);
                        }
                    });
                    rvCours.setAdapter(adapter);
                } else {
                    adapter.updateList(list);
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(CoursDetails cours) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer ce cours ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    new Thread(() -> {
                        db.coursDao().delete(cours.cours);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Cours supprimé", Toast.LENGTH_SHORT).show();
                            loadCours();
                        });
                    }).start();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCours();
    }
}