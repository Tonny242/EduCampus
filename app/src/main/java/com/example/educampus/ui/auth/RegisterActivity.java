package com.example.educampus.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.educampus.R;
import com.example.educampus.data.entity.User;
import com.example.educampus.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etPrenom;
    private TextInputEditText etNom;
    private TextInputEditText etEmail;
    private TextInputEditText etTelephone;

    private MaterialAutoCompleteTextView etFormation;
    private MaterialAutoCompleteTextView etNiveau;

    private TextInputEditText etPassword;

    private MaterialButton btnRegister;
    private TextView tvLogin;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        etPrenom = findViewById(R.id.etPrenom);
        etNom = findViewById(R.id.etNom);
        etEmail = findViewById(R.id.etEmail);
        etTelephone = findViewById(R.id.etTelephone);

        etFormation = findViewById(R.id.etFormation);
        etNiveau = findViewById(R.id.etNiveau);

        etPassword = findViewById(R.id.etPassword);

        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        configurerMenus();

        btnRegister.setOnClickListener(v -> register());

        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(
                    RegisterActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();
        });
    }

    private void configurerMenus() {

        String[] formations = {
                "Informatique de Gestion",
                "Ressources Humaines (RH)",
                "Comptabilité & Gestion d'entreprise"
        };

        ArrayAdapter<String> formationAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        formations
                );

        etFormation.setAdapter(formationAdapter);

        String[] niveaux = {
                "L1",
                "L2",
                "L3",
                "M1",
                "M2"
        };

        ArrayAdapter<String> niveauAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_dropdown_item_1line,
                        niveaux
                );

        etNiveau.setAdapter(niveauAdapter);

        etFormation.setOnItemClickListener(
                (parent, view, position, id) -> {

                    String formation =
                            parent.getItemAtPosition(position).toString();

                    if (formation.equals("Informatique de Gestion")) {

                        String[] niveauxIG = {
                                "L1",
                                "L2",
                                "L3"
                        };

                        ArrayAdapter<String> adapterIG =
                                new ArrayAdapter<>(
                                        this,
                                        android.R.layout.simple_dropdown_item_1line,
                                        niveauxIG
                                );

                        etNiveau.setAdapter(adapterIG);

                    } else {

                        String[] niveauxAutres = {
                                "L1",
                                "L2",
                                "L3",
                                "M1",
                                "M2"
                        };

                        ArrayAdapter<String> adapterAutres =
                                new ArrayAdapter<>(
                                        this,
                                        android.R.layout.simple_dropdown_item_1line,
                                        niveauxAutres
                                );

                        etNiveau.setAdapter(adapterAutres);
                    }

                    etNiveau.setText("", false);
                }
        );
    }

    private void register() {

        String prenom = getText(etPrenom);
        String nom = getText(etNom);
        String email = getText(etEmail);
        String telephone = getText(etTelephone);

        String formation = etFormation.getText() != null
                ? etFormation.getText().toString().trim()
                : "";

        String niveau = etNiveau.getText() != null
                ? etNiveau.getText().toString().trim()
                : "";

        String password = getText(etPassword);

        if (prenom.isEmpty()) {
            etPrenom.setError("Veuillez saisir votre prénom");
            etPrenom.requestFocus();
            return;
        }

        if (nom.isEmpty()) {
            etNom.setError("Veuillez saisir votre nom");
            etNom.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Veuillez saisir votre email");
            etEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email invalide");
            etEmail.requestFocus();
            return;
        }

        if (telephone.isEmpty()) {
            etTelephone.setError("Veuillez saisir votre téléphone");
            etTelephone.requestFocus();
            return;
        }

        if (formation.isEmpty()) {
            etFormation.setError("Veuillez sélectionner une formation");
            etFormation.requestFocus();
            return;
        }

        if (niveau.isEmpty()) {
            etNiveau.setError("Veuillez sélectionner un niveau");
            etNiveau.requestFocus();
            return;
        }

        if (formation.equals("Informatique de Gestion")
                && (niveau.equals("M1") || niveau.equals("M2"))) {

            etNiveau.setError(
                    "Informatique de Gestion s'arrête à la L3"
            );
            etNiveau.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Veuillez saisir un mot de passe");
            etPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            etPassword.setError(
                    "Le mot de passe doit contenir au moins 6 caractères"
            );
            etPassword.requestFocus();
            return;
        }

        User existingUser = userViewModel.login(email);

        if (existingUser != null) {

            Toast.makeText(
                    this,
                    "Cette adresse email est déjà utilisée",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        User user = new User(
                prenom,
                nom,
                email,
                telephone,
                password,
                "ETUDIANT",
                formation,
                niveau
        );

        long userId = userViewModel.register(user);

        if (userId > 0) {

            Toast.makeText(
                    this,
                    "Compte créé avec succès",
                    Toast.LENGTH_SHORT
            ).show();

            Intent intent = new Intent(
                    RegisterActivity.this,
                    LoginActivity.class
            );

            startActivity(intent);
            finish();

        } else {

            Toast.makeText(
                    this,
                    "Erreur lors de la création du compte",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private String getText(TextInputEditText field) {

        if (field.getText() == null) {
            return "";
        }

        return field.getText().toString().trim();
    }
}