package com.example.educampus.ui.admin.cours;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.data.entity.Formation;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AdminCoursFormActivity extends AppCompatActivity {

    private TextInputEditText etNom, etCode, etVolume, etSemestre, etCoefficient, etDescription;
    private Spinner spinnerEnseignant, spinnerFormation;
    private TextView tvFormTitle;
    private MaterialButton btnSave, btnCancel;

    private AppDatabase db;
    private int coursId = -1;
    private Cours coursToEdit;
    private List<Enseignant> enseignants = new ArrayList<>();
    private List<Formation> formations = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_cours_form);

        db = DatabaseProvider.getDatabase(this);
        coursId = getIntent().getIntExtra("cours_id", -1);

        initViews();
        loadSpinners();

        if (coursId != -1) {
            loadCoursData();
        }

        btnSave.setOnClickListener(v -> saveCours());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle);
        etNom = findViewById(R.id.etNomCours);
        etCode = findViewById(R.id.etCode);
        etVolume = findViewById(R.id.etVolumeHoraire);
        etSemestre = findViewById(R.id.etSemestre);
        etCoefficient = findViewById(R.id.etCoefficient);
        etDescription = findViewById(R.id.etDescription);
        spinnerEnseignant = findViewById(R.id.spinnerEnseignant);
        spinnerFormation = findViewById(R.id.spinnerFormation);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadSpinners() {
        new Thread(() -> {
            enseignants = db.enseignantDao().getAll();
            formations = db.formationDao().getAll();

            List<String> ensNames = new ArrayList<>();
            for (Enseignant e : enseignants) ensNames.add(e.getPrenom() + " " + e.getNom());

            List<String> formNames = new ArrayList<>();
            for (Formation f : formations) formNames.add(f.getNom());

            runOnUiThread(() -> {
                ArrayAdapter<String> ensAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ensNames);
                ensAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEnseignant.setAdapter(ensAdapter);

                ArrayAdapter<String> formAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, formNames);
                formAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFormation.setAdapter(formAdapter);

                if (coursToEdit != null) {
                    setSpinnerSelections();
                }
            });
        }).start();
    }

    private void loadCoursData() {
        tvFormTitle.setText("Modifier le Cours");
        new Thread(() -> {
            coursToEdit = db.coursDao().getById(coursId);
            if (coursToEdit != null) {
                runOnUiThread(() -> {
                    etNom.setText(coursToEdit.getNom());
                    etCode.setText(coursToEdit.getCode());
                    etVolume.setText(String.valueOf(coursToEdit.getVolumeHoraire()));
                    etSemestre.setText(coursToEdit.getSemestre());
                    etCoefficient.setText(String.valueOf(coursToEdit.getCoefficient()));
                    etDescription.setText(coursToEdit.getDescription());
                    setSpinnerSelections();
                });
            }
        }).start();
    }

    private void setSpinnerSelections() {
        for (int i = 0; i < enseignants.size(); i++) {
            if (enseignants.get(i).getId() == coursToEdit.getEnseignantId()) {
                spinnerEnseignant.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < formations.size(); i++) {
            if (formations.get(i).getId() == coursToEdit.getFormationId()) {
                spinnerFormation.setSelection(i);
                break;
            }
        }
    }

    private void saveCours() {
        String nom = etNom.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        String volumeStr = etVolume.getText().toString().trim();
        String semestre = etSemestre.getText().toString().trim();
        String coeffStr = etCoefficient.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();

        if (nom.isEmpty() || code.isEmpty() || volumeStr.isEmpty() || semestre.isEmpty() || coeffStr.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs obligatoires", Toast.LENGTH_SHORT).show();
            return;
        }

        int ensPos = spinnerEnseignant.getSelectedItemPosition();
        int formPos = spinnerFormation.getSelectedItemPosition();
        if (ensPos == -1 || formPos == -1) {
            Toast.makeText(this, "Veuillez sélectionner un enseignant et une formation", Toast.LENGTH_SHORT).show();
            return;
        }

        int ensId = enseignants.get(ensPos).getId();
        int formId = formations.get(formPos).getId();
        int volume = Integer.parseInt(volumeStr);
        double coeff = Double.parseDouble(coeffStr);

        new Thread(() -> {
            if (coursId == -1) {
                Cours newCours = new Cours(nom, code, ensId, formId, volume, desc, semestre, coeff);
                db.coursDao().insert(newCours);
            } else {
                coursToEdit.setNom(nom);
                coursToEdit.setCode(code);
                coursToEdit.setEnseignantId(ensId);
                coursToEdit.setFormationId(formId);
                coursToEdit.setVolumeHoraire(volume);
                coursToEdit.setSemestre(semestre);
                coursToEdit.setCoefficient(coeff);
                coursToEdit.setDescription(desc);
                db.coursDao().update(coursToEdit);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Cours enregistré avec succès", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}