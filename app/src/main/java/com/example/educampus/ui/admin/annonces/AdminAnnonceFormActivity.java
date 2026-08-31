package com.example.educampus.ui.admin.annonces;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Annonce;
import com.example.educampus.data.entity.User;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdminAnnonceFormActivity extends AppCompatActivity {

    private TextInputEditText etTitre, etContenu;
    private Spinner spinnerCategorie;
    private SwitchMaterial switchImportante;
    private TextView tvFormTitle;
    private MaterialButton btnSave, btnCancel;

    private AppDatabase db;
    private int annonceId = -1;
    private Annonce annonceToEdit;
    private final List<String> categories = Arrays.asList(
            "Actualité", "Examen", "Événement", "Réunion",
            "Changement de salle", "Report de cours", "Fermeture exceptionnelle"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_annonce_form);

        db = DatabaseProvider.getDatabase(this);
        annonceId = getIntent().getIntExtra("annonce_id", -1);

        initViews();
        loadSpinner();

        if (annonceId != -1) {
            loadAnnonceData();
        }

        btnSave.setOnClickListener(v -> saveAnnonce());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle);
        etTitre = findViewById(R.id.etTitre);
        etContenu = findViewById(R.id.etContenu);
        spinnerCategorie = findViewById(R.id.spinnerCategorie);
        switchImportante = findViewById(R.id.switchImportante);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategorie.setAdapter(adapter);
    }

    private void loadAnnonceData() {
        tvFormTitle.setText("Modifier l'Annonce");
        new Thread(() -> {
            annonceToEdit = db.annonceDao().getById(annonceId);
            if (annonceToEdit != null) {
                runOnUiThread(() -> {
                    etTitre.setText(annonceToEdit.getTitre());
                    etContenu.setText(annonceToEdit.getContenu());
                    switchImportante.setChecked(annonceToEdit.isImportante());
                    int index = categories.indexOf(annonceToEdit.getCategorie());
                    if (index != -1) spinnerCategorie.setSelection(index);
                });
            }
        }).start();
    }

    private void saveAnnonce() {
        String titre = etTitre.getText().toString().trim();
        String contenu = etContenu.getText().toString().trim();
        String categorie = categories.get(spinnerCategorie.getSelectedItemPosition());
        boolean importante = switchImportante.isChecked();

        if (titre.isEmpty() || contenu.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = SessionManager.getUserId(this);
        
        new Thread(() -> {
            User admin = db.userDao().getById(userId);
            String auteur = (admin != null) ? admin.getPrenom() + " " + admin.getNom() : "Administrateur";
            String date = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

            if (annonceId == -1) {
                Annonce newAnnonce = new Annonce(titre, contenu, date, auteur, "", importante, categorie);
                db.annonceDao().insert(newAnnonce);
            } else {
                annonceToEdit.setTitre(titre);
                annonceToEdit.setContenu(contenu);
                annonceToEdit.setCategorie(categorie);
                annonceToEdit.setImportante(importante);
                // On peut garder la date et l'auteur d'origine ou les mettre à jour
                annonceToEdit.setDate(date + " (modifié)");
                db.annonceDao().update(annonceToEdit);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Annonce enregistrée", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}