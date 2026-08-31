package com.example.educampus.ui.cours;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CoursActivity extends AppCompatActivity {

    public static final String EXTRA_COURS_ID = "extra_cours_id";

    private RecyclerView recyclerView;
    private CoursAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cours);

        db = DatabaseProvider.getDatabase(this);

        MaterialButton btnRetour = findViewById(R.id.btnRetourDashboard);
        recyclerView = findViewById(R.id.recyclerViewCours);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Cours> coursList = db.coursDao().getAll();

        adapter = new CoursAdapter(coursList, cours -> {
            Intent intent = new Intent(CoursActivity.this, CoursDetailActivity.class);
            intent.putExtra(EXTRA_COURS_ID, cours.getId());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(
                    CoursActivity.this,
                    DashboardActivity.class
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recharge la liste au cas où un cours aurait été modifié ailleurs
        if (adapter != null) {
            adapter.updateList(db.coursDao().getAll());
        }
    }
}