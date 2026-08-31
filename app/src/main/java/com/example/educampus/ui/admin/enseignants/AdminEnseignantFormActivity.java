package com.example.educampus.ui.admin.enseignants;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AdminEnseignantFormActivity extends AppCompatActivity {

    private TextInputEditText etPrenom, etNom, etEmail, etTelephone, etSpecialite;
    private TextView tvFormTitle;
    private MaterialButton btnSave, btnCancel;

    private AppDatabase db;
    private int enseignantId = -1;
    private Enseignant enseignantToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_enseignant_form);

        db = DatabaseProvider.getDatabase(this);
        enseignantId = getIntent().getIntExtra("enseignant_id", -1);

        initViews();

        if (enseignantId != -1) {
            loadEnseignantData();
        }

        btnSave.setOnClickListener(v -> saveEnseignant());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle);
        etPrenom = findViewById(R.id.etPrenom);
        etNom = findViewById(R.id.etNom);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);
        etSpecialite = findViewById(R.id.etSpecialite);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadEnseignantData() {
        tvFormTitle.setText("Modifier l'Enseignant");
        new Thread(() -> {
            enseignantToEdit = db.enseignantDao().getById(enseignantId);
            if (enseignantToEdit != null) {
                runOnUiThread(() -> {
                    etPrenom.setText(enseignantToEdit.getPrenom());
                    etNom.setText(enseignantToEdit.getNom());
                    etEmail.setText(enseignantToEdit.getEmail());
                    etTelephone.setText(enseignantToEdit.getTelephone());
                    etSpecialite.setText(enseignantToEdit.getSpecialite());
                });
            }
        }).start();
    }

    private void saveEnseignant() {
        String prenom = etPrenom.getText().toString().trim();
        String nom = etNom.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String tel = etTelephone.getText().toString().trim();
        String spec = etSpecialite.getText().toString().trim();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            if (enseignantId == -1) {
                Enseignant newEns = new Enseignant(prenom, nom, email, tel, spec);
                db.enseignantDao().insert(newEns);
            } else {
                enseignantToEdit.setPrenom(prenom);
                enseignantToEdit.setNom(nom);
                enseignantToEdit.setEmail(email);
                enseignantToEdit.setTelephone(tel);
                enseignantToEdit.setSpecialite(spec);
                db.enseignantDao().update(enseignantToEdit);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Enseignant enregistré avec succès", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}