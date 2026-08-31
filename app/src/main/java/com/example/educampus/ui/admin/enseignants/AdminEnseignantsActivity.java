package com.example.educampus.ui.admin.enseignants;

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
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminEnseignantsActivity extends AppCompatActivity {

    private RecyclerView rvEnseignants;
    private AdminEnseignantAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_enseignants);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = DatabaseProvider.getDatabase(this);
        rvEnseignants = findViewById(R.id.rvEnseignants);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddEnseignant);

        rvEnseignants.setLayoutManager(new LinearLayoutManager(this));
        
        loadEnseignants();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminEnseignantFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadEnseignants() {
        new Thread(() -> {
            List<Enseignant> list = db.enseignantDao().getAll();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new AdminEnseignantAdapter(list, new AdminEnseignantAdapter.OnEnseignantActionListener() {
                        @Override
                        public void onEdit(Enseignant enseignant) {
                            Intent intent = new Intent(AdminEnseignantsActivity.this, AdminEnseignantFormActivity.class);
                            intent.putExtra("enseignant_id", enseignant.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(Enseignant enseignant) {
                            showDeleteConfirmation(enseignant);
                        }
                    });
                    rvEnseignants.setAdapter(adapter);
                } else {
                    adapter.updateList(list);
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(Enseignant enseignant) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer cet enseignant ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    new Thread(() -> {
                        db.enseignantDao().delete(enseignant);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Enseignant supprimé", Toast.LENGTH_SHORT).show();
                            loadEnseignants();
                        });
                    }).start();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEnseignants();
    }
}