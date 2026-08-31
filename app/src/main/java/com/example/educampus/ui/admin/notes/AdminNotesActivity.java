package com.example.educampus.ui.admin.notes;

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
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.User;
import com.example.educampus.data.model.NoteDetails;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminNotesActivity extends AppCompatActivity {

    private RecyclerView rvNotes;
    private AdminNoteAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_notes);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = DatabaseProvider.getDatabase(this);
        rvNotes = findViewById(R.id.rvNotes);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddNote);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        
        loadNotes();

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminNoteFormActivity.class);
            startActivity(intent);
        });
    }

    private void loadNotes() {
        new Thread(() -> {
            List<NoteDetails> list = db.noteDao().getAllWithDetails();
            List<Etudiant> etudiants = db.etudiantDao().getAll();
            List<User> users = db.userDao().getAll();
            
            Map<Integer, String> namesMap = new HashMap<>();
            Map<Integer, User> userMap = new HashMap<>();
            for (User u : users) userMap.put(u.getId(), u);
            for (Etudiant e : etudiants) {
                User u = userMap.get(e.getUserId());
                if (u != null) namesMap.put(e.getId(), u.getNom() + " " + u.getPrenom());
            }

            runOnUiThread(() -> {
                if (adapter == null) {
                    adapter = new AdminNoteAdapter(list, namesMap, new AdminNoteAdapter.OnNoteActionListener() {
                        @Override
                        public void onEdit(NoteDetails note) {
                            Intent intent = new Intent(AdminNotesActivity.this, AdminNoteFormActivity.class);
                            intent.putExtra("note_id", note.note.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onDelete(NoteDetails note) {
                            showDeleteConfirmation(note);
                        }
                    });
                    rvNotes.setAdapter(adapter);
                } else {
                    adapter.updateList(list, namesMap);
                }
            });
        }).start();
    }

    private void showDeleteConfirmation(NoteDetails note) {
        new AlertDialog.Builder(this)
                .setTitle("Suppression")
                .setMessage("Voulez-vous vraiment supprimer cette note ?")
                .setPositiveButton("Supprimer", (dialog, which) -> {
                    new Thread(() -> {
                        db.noteDao().delete(note.note);
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Note supprimée", Toast.LENGTH_SHORT).show();
                            loadNotes();
                        });
                    }).start();
                })
                .setNegativeButton("Annuler", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}