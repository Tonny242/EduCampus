package com.example.educampus.ui.admin.etudiants;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Formation;
import com.example.educampus.data.entity.User;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AdminEtudiantFormActivity extends AppCompatActivity {

    private TextInputEditText etPrenom, etNom, etEmail, etTelephone, etPassword, etNiveau;
    private TextInputLayout tilPassword;
    private Spinner spinnerFormation;
    private TextView tvFormTitle;
    private MaterialButton btnSave, btnCancel;

    private AppDatabase db;
    private int etudiantId = -1;
    private Etudiant etudiantToEdit;
    private User userToEdit;
    private List<Formation> formations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Sécurité
        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_etudiant_form);

        db = DatabaseProvider.getDatabase(this);
        etudiantId = getIntent().getIntExtra("etudiant_id", -1);

        initViews();
        loadFormations();

        if (etudiantId != -1) {
            loadEtudiantData();
        }

        btnSave.setOnClickListener(v -> saveEtudiant());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle);
        etPrenom = findViewById(R.id.etPrenom);
        etNom = findViewById(R.id.etNom);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);
        etPassword = findViewById(R.id.etPassword);
        tilPassword = findViewById(R.id.tilPassword);
        spinnerFormation = findViewById(R.id.spinnerFormation);
        etNiveau = findViewById(R.id.etNiveau);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadFormations() {
        new Thread(() -> {
            formations = db.formationDao().getAll();
            List<String> formationNames = new ArrayList<>();
            for (Formation f : formations) {
                formationNames.add(f.getNom());
            }

            runOnUiThread(() -> {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, formationNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFormation.setAdapter(adapter);
                
                // Si on édite, on positionne le spinner après le chargement des données
                if (etudiantToEdit != null) {
                    setSpinnerToFormation(etudiantToEdit.getFormationId());
                }
            });
        }).start();
    }

    private void loadEtudiantData() {
        tvFormTitle.setText("Modifier l'Étudiant");
        // Le mot de passe n'est pas obligatoire en modification
        tilPassword.setHint("Mot de passe (laisser vide pour ne pas changer)");

        new Thread(() -> {
            etudiantToEdit = db.etudiantDao().getById(etudiantId);
            if (etudiantToEdit != null) {
                userToEdit = db.userDao().getById(etudiantToEdit.getUserId());
                runOnUiThread(() -> {
                    if (userToEdit != null) {
                        etPrenom.setText(userToEdit.getPrenom());
                        etNom.setText(userToEdit.getNom());
                        etEmail.setText(userToEdit.getEmail());
                        etTelephone.setText(userToEdit.getTelephone());
                        etNiveau.setText(etudiantToEdit.getNiveau());
                        setSpinnerToFormation(etudiantToEdit.getFormationId());
                    }
                });
            }
        }).start();
    }

    private void setSpinnerToFormation(int formationId) {
        for (int i = 0; i < formations.size(); i++) {
            if (formations.get(i).getId() == formationId) {
                spinnerFormation.setSelection(i);
                break;
            }
        }
    }

    private void saveEtudiant() {
        String prenom = etPrenom.getText().toString().trim();
        String nom = etNom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String tel = etTelephone.getText().toString().trim();
        String pass = etPassword.getText().toString();
        String niveau = etNiveau.getText().toString().trim();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || niveau.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etudiantId == -1 && pass.isEmpty()) {
            Toast.makeText(this, "Le mot de passe est obligatoire pour un nouvel étudiant", Toast.LENGTH_SHORT).show();
            return;
        }

        int formationPos = spinnerFormation.getSelectedItemPosition();
        if (formationPos == -1) {
            Toast.makeText(this, "Veuillez sélectionner une formation", Toast.LENGTH_SHORT).show();
            return;
        }
        int formationId = formations.get(formationPos).getId();

        new Thread(() -> {
            if (etudiantId == -1) {
                // Création
                User newUser = new User(prenom, nom, email, tel, pass, SessionManager.ROLE_ETUDIANT, "", niveau);
                long userId = db.userDao().insert(newUser);
                Etudiant newEtudiant = new Etudiant((int) userId, formationId, niveau);
                db.etudiantDao().insert(newEtudiant);
            } else {
                // Mise à jour
                userToEdit.setPrenom(prenom);
                userToEdit.setNom(nom);
                userToEdit.setEmail(email);
                userToEdit.setTelephone(tel);
                userToEdit.setNiveau(niveau);
                if (!pass.isEmpty()) {
                    userToEdit.setMotDePasse(pass);
                }
                db.userDao().update(userToEdit);

                etudiantToEdit.setFormationId(formationId);
                etudiantToEdit.setNiveau(niveau);
                db.etudiantDao().update(etudiantToEdit);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Étudiant enregistré avec succès", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}