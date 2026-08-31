package com.example.educampus.ui.admin.notes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Note;
import com.example.educampus.data.entity.User;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminNoteFormActivity extends AppCompatActivity {

    private Spinner spinnerEtudiant, spinnerCours;
    private TextInputEditText etCC, etExamen, etMoyenne, etCoefficient, etSemestre;
    private TextView tvFormTitle;
    private MaterialButton btnSave, btnCancel;

    private AppDatabase db;
    private int noteId = -1;
    private Note noteToEdit;
    private List<Etudiant> etudiants = new ArrayList<>();
    private List<Cours> coursList = new ArrayList<>();
    private Map<Integer, String> etudiantNames = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.ROLE_ADMIN.equals(SessionManager.getUserRole(this))) {
            finish();
            return;
        }

        setContentView(R.layout.activity_admin_note_form);

        db = DatabaseProvider.getDatabase(this);
        noteId = getIntent().getIntExtra("note_id", -1);

        initViews();
        loadSpinners();

        if (noteId != -1) {
            loadNoteData();
        }

        setupCalculMoyenne();

        btnSave.setOnClickListener(v -> saveNote());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvFormTitle = findViewById(R.id.tvFormTitle);
        spinnerEtudiant = findViewById(R.id.spinnerEtudiant);
        spinnerCours = findViewById(R.id.spinnerCours);
        etCC = findViewById(R.id.etCC);
        etExamen = findViewById(R.id.etExamen);
        etMoyenne = findViewById(R.id.etMoyenne);
        etCoefficient = findViewById(R.id.etCoefficient);
        etSemestre = findViewById(R.id.etSemestre);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

    private void loadSpinners() {
        new Thread(() -> {
            etudiants = db.etudiantDao().getAll();
            coursList = db.coursDao().getAll();
            List<User> users = db.userDao().getAll();
            
            Map<Integer, User> userMap = new HashMap<>();
            for (User u : users) userMap.put(u.getId(), u);
            
            List<String> etuNames = new ArrayList<>();
            for (Etudiant e : etudiants) {
                User u = userMap.get(e.getUserId());
                String name = u != null ? u.getNom() + " " + u.getPrenom() : "ID: " + e.getId();
                etuNames.add(name);
                etudiantNames.put(e.getId(), name);
            }

            List<String> crsNames = new ArrayList<>();
            for (Cours c : coursList) crsNames.add(c.getNom());

            runOnUiThread(() -> {
                ArrayAdapter<String> etuAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, etuNames);
                etuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerEtudiant.setAdapter(etuAdapter);

                ArrayAdapter<String> crsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, crsNames);
                crsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCours.setAdapter(crsAdapter);

                if (noteToEdit != null) {
                    setSpinnerSelections();
                }
            });
        }).start();
    }

    private void loadNoteData() {
        tvFormTitle.setText("Modifier la Note");
        new Thread(() -> {
            noteToEdit = db.noteDao().getById(noteId);
            if (noteToEdit != null) {
                runOnUiThread(() -> {
                    etCC.setText(String.valueOf(noteToEdit.getCc()));
                    etExamen.setText(String.valueOf(noteToEdit.getExamen()));
                    etMoyenne.setText(String.valueOf(noteToEdit.getMoyenne()));
                    etCoefficient.setText(String.valueOf(noteToEdit.getCoefficient()));
                    etSemestre.setText(noteToEdit.getSemestre());
                    setSpinnerSelections();
                });
            }
        }).start();
    }

    private void setSpinnerSelections() {
        for (int i = 0; i < etudiants.size(); i++) {
            if (etudiants.get(i).getId() == noteToEdit.getEtudiantId()) {
                spinnerEtudiant.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < coursList.size(); i++) {
            if (coursList.get(i).getId() == noteToEdit.getCoursId()) {
                spinnerCours.setSelection(i);
                break;
            }
        }
    }

    private void setupCalculMoyenne() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                calculerMoyenne();
            }
        };
        etCC.addTextChangedListener(watcher);
        etExamen.addTextChangedListener(watcher);
    }

    private void calculerMoyenne() {
        try {
            String ccStr = etCC.getText().toString();
            String exStr = etExamen.getText().toString();
            if (!ccStr.isEmpty() && !exStr.isEmpty()) {
                double cc = Double.parseDouble(ccStr);
                double ex = Double.parseDouble(exStr);
                double moy = (cc + ex) / 2.0;
                etMoyenne.setText(String.valueOf(moy));
            }
        } catch (Exception ignored) {}
    }

    private void saveNote() {
        String ccStr = etCC.getText().toString().trim();
        String exStr = etExamen.getText().toString().trim();
        String moyStr = etMoyenne.getText().toString().trim();
        String coeffStr = etCoefficient.getText().toString().trim();
        String semestre = etSemestre.getText().toString().trim();

        if (ccStr.isEmpty() || exStr.isEmpty() || coeffStr.isEmpty() || semestre.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        int etuPos = spinnerEtudiant.getSelectedItemPosition();
        int crsPos = spinnerCours.getSelectedItemPosition();
        if (etuPos == -1 || crsPos == -1) {
            Toast.makeText(this, "Sélectionnez un étudiant et un cours", Toast.LENGTH_SHORT).show();
            return;
        }

        int etuId = etudiants.get(etuPos).getId();
        int crsId = coursList.get(crsPos).getId();
        double cc = Double.parseDouble(ccStr);
        double ex = Double.parseDouble(exStr);
        double moy = Double.parseDouble(moyStr);
        double coeff = Double.parseDouble(coeffStr);

        new Thread(() -> {
            if (noteId == -1) {
                Note newNote = new Note(etuId, crsId, cc, ex, moy, coeff, semestre);
                db.noteDao().insert(newNote);
            } else {
                noteToEdit.setEtudiantId(etuId);
                noteToEdit.setCoursId(crsId);
                noteToEdit.setCc(cc);
                noteToEdit.setExamen(ex);
                noteToEdit.setMoyenne(moy);
                noteToEdit.setCoefficient(coeff);
                noteToEdit.setSemestre(semestre);
                db.noteDao().update(noteToEdit);
            }
            runOnUiThread(() -> {
                Toast.makeText(this, "Note enregistrée", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}