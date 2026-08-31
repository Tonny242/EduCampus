package com.example.educampus.ui.admin.emploi;

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
import com.example.educampus.data.model.EmploiDetails;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminEmploiActivity extends AppCompatActivity {

    private RecyclerView rvEmploi;
    private AdminEmploiAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_emploi);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = DatabaseProvider.getDatabase(this);
        rvEmploi = findViewById(R.id.rvEmploi);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddEmploi);

        rvEmploi.setLayoutManager(new LinearLayoutManager(this));
        
        loadEmploi();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminEmploiFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadEmploi() {
        new Thread(() -> {
            List<EmploiDetails> list = db.emploiDuTempsDao().getAllWithDetails();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new AdminEmploiAdapter(list, new AdminEmploiAdapter.OnEmploiActionListener() {
                        @Override
                        public void onEdit(EmploiDetails details) {
                            Intent intent = new Intent(AdminEmploiActivity.this, AdminEmploiFormActivity.class);
                            intent.putExtra("emploi_id", details.emploi.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(EmploiDetails details) {
                            showDeleteConfirmation(details);
                        }
                    });
                    rvEmploi.setAdapter(adapter);
                } else {
                    adapter.updateList(list);
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(EmploiDetails details) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer cette séance ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    new Thread(() -> {
                        db.emploiDuTempsDao().delete(details.emploi);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Séance supprimée", Toast.LENGTH_SHORT).show();
                            loadEmploi();
                        });
                    }).start();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadEmploi();
    }
}