package com.example.educampus.ui.admin.emploi;

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
import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminEmploiFormActivity extends AppCompatActivity {

    private Spinner spinnerCours, spinnerJour, spinnerType;
    private TextInputEditText etHeureDebut, etHeureFin, etSalle;
    private TextView tvFormTitle;
    private MaterialButton btnSave, btnCancel;

    private AppDatabase db;
    private int emploiId = -1;
    private EmploiDuTemps emploiToEdit;
    private List<Cours> coursList = new ArrayList<>();
    private final List<String> jours = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche");
    private final List<String> types = Arrays.asList("Théorique", "Pratique");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_emploi_form);

        db = DatabaseProvider.getDatabase(this);
        emploiId = getIntent().getIntExtra("emploi_id", -1);

        initViews();
        loadSpinners();

        if (emploiId != -1) {
            loadEmploiData();
        }

        btnSave.setOnClickListener(v -> saveEmploi());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle);
        spinnerCours = findViewById(R.id.spinnerCours);
        spinnerJour = findViewById(R.id.spinnerJour);
        spinnerType = findViewById(R.id.spinnerType);
        etHeureDebut = findViewById(R.id.etHeureDebut);
        etHeureFin = findViewById(R.id.etHeureFin);
        etSalle = findViewById(R.id.etSalle);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadSpinners() {
        new Thread(() -> {
            coursList = db.coursDao().getAll();
            List<String> coursNames = new ArrayList<>();
            for (Cours c : coursList) coursNames.add(c.getNom());

            runOnUiThread(() -> {
                ArrayAdapter<String> coursAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, coursNames);
                coursAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCours.setAdapter(coursAdapter);

                ArrayAdapter<String> jourAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jours);
                jourAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerJour.setAdapter(jourAdapter);

                ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
                typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(typeAdapter);

                if (emploiToEdit != null) {
                    setSpinnerSelections();
                }
            });
        }).start();
    }

    private void loadEmploiData() {
        tvFormTitle.setText("Modifier la Séance");
        new Thread(() -> {
            emploiToEdit = db.emploiDuTempsDao().getById(emploiId);
            if (emploiToEdit != null) {
                runOnUiThread(() -> {
                    etHeureDebut.setText(emploiToEdit.getHeureDebut());
                    etHeureFin.setText(emploiToEdit.getHeureFin());
                    etSalle.setText(emploiToEdit.getSalle());
                    setSpinnerSelections();
                });
            }
        }).start();
    }

    private void setSpinnerSelections() {
        for (int i = 0; i < coursList.size(); i++) {
            if (coursList.get(i).getId() == emploiToEdit.getCoursId()) {
                spinnerCours.setSelection(i);
                break;
            }
        }
        int jourIndex = jours.indexOf(emploiToEdit.getDate());
        if (jourIndex != -1) spinnerJour.setSelection(jourIndex);

        int typeIndex = types.indexOf(emploiToEdit.getTypeSeance());
        if (typeIndex != -1) spinnerType.setSelection(typeIndex);
    }

    private void saveEmploi() {
        String heureD = etHeureDebut.getText().toString().trim();
        String heureF = etHeureFin.getText().toString().trim();
        String salle = etSalle.getText().toString().trim();

        if (heureD.isEmpty() || heureF.isEmpty() || salle.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int coursPos = spinnerCours.getSelectedItemPosition();
        if (coursPos == -1) {
            Toast.makeText(this, "Veuillez sélectionner un cours", Toast.LENGTH_SHORT).show();
            return;
        }
        int coursId = coursList.get(coursPos).getId();
        String jour = jours.get(spinnerJour.getSelectedItemPosition());
        String type = types.get(spinnerType.getSelectedItemPosition());

        new Thread(() -> {
            if (emploiId == -1) {
                EmploiDuTemps newEmploi = new EmploiDuTemps(coursId, jour, heureD, heureF, salle, type);
                db.emploiDuTempsDao().insert(newEmploi);
            } else {
                emploiToEdit.setCoursId(coursId);
                emploiToEdit.setDate(jour);
                emploiToEdit.setHeureDebut(heureD);
                emploiToEdit.setHeureFin(heureF);
                emploiToEdit.setSalle(salle);
                emploiToEdit.setTypeSeance(type);
                db.emploiDuTempsDao().update(emploiToEdit);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Séance enregistrée", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}