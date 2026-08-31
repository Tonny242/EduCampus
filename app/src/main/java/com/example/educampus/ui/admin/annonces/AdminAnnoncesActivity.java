package com.example.educampus.ui.admin.annonces;

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
import com.example.educampus.data.entity.Annonce;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminAnnoncesActivity extends AppCompatActivity {

    private RecyclerView rvAnnonces;
    private AdminAnnonceAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_annonces);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = DatabaseProvider.getDatabase(this);
        rvAnnonces = findViewById(R.id.rvAnnonces);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddAnnonce);

        rvAnnonces.setLayoutManager(new LinearLayoutManager(this));
        
        loadAnnonces();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminAnnonceFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadAnnonces() {
        new Thread(() -> {
            List<Annonce> list = db.annonceDao().getAll();
            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new AdminAnnonceAdapter(list, new AdminAnnonceAdapter.OnAnnonceActionListener() {
                        @Override
                        public void onEdit(Annonce annonce) {
                            Intent intent = new Intent(AdminAnnoncesActivity.this, AdminAnnonceFormActivity.class);
                            intent.putExtra("annonce_id", annonce.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(Annonce annonce) {
                            showDeleteConfirmation(annonce);
                        }
                    });
                    rvAnnonces.setAdapter(adapter);
                } else {
                    adapter.updateList(list);
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(Annonce annonce) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer cette annonce ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    new Thread(() -> {
                        db.annonceDao().delete(annonce);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Annonce supprimée", Toast.LENGTH_SHORT).show();
                            loadAnnonces();
                        });
                    }).start();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAnnonces();
    }
}