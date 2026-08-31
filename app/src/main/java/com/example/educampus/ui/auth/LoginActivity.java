package com.example.educampus.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.educampus.R;
import com.example.educampus.data.entity.User;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.example.educampus.utils.SessionManager;
import com.example.educampus.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;

    private MaterialButton btnLogin;
    private TextView tvRegister;

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        userViewModel = new ViewModelProvider(this)
                .get(UserViewModel.class);

        btnLogin.setOnClickListener(v -> login());

        tvRegister.setOnClickListener(v -> {

            Intent intent = new Intent(
                    LoginActivity.this,
                    RegisterActivity.class
            );

            startActivity(intent);
        });
    }

    private void login() {

        String email = etEmail.getText() != null
                ? etEmail.getText().toString().trim()
                : "";

        String password = etPassword.getText() != null
                ? etPassword.getText().toString()
                : "";

        if (email.isEmpty()) {
            etEmail.setError("Veuillez saisir votre email");
            etEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Veuillez saisir votre mot de passe");
            etPassword.requestFocus();
            return;
        }

        User user = userViewModel.login(email);

        if (user == null) {

            Toast.makeText(
                    this,
                    "Email ou mot de passe incorrect",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        if (!user.getMotDePasse().equals(password)) {

            Toast.makeText(
                    this,
                    "Email ou mot de passe incorrect",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        // Sauvegarde de la session : on retient qui est connecté et son rôle
        SessionManager.saveSession(this, user.getId(), user.getRole());

        Toast.makeText(
                this,
                "Connexion réussie",
                Toast.LENGTH_SHORT
        ).show();

        Intent intent;
        if (SessionManager.ROLE_ADMIN.equals(user.getRole())) {
            intent = new Intent(
                    LoginActivity.this,
                    com.example.educampus.ui.admin.AdminDashboardActivity.class
            );
        } else {
            intent = new Intent(
                    LoginActivity.this,
                    DashboardActivity.class
            );
        }

        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );

        startActivity(intent);
        finish();
    }
}