package com.example.educampus.ui.profil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.educampus.R;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Absence;
import com.example.educampus.data.entity.Annonce;
import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.entity.Note;
import com.example.educampus.data.entity.User;
import com.example.educampus.ui.annonces.AnnonceAdapter;
import com.example.educampus.ui.annonces.AnnonceDisplay;
import com.example.educampus.ui.dashboard.DashboardActivity;
import com.example.educampus.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProfilActivity extends AppCompatActivity {

    private final SimpleDateFormat formatDateHeure =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);

        AppDatabase db = DatabaseProvider.getDatabase(this);

        MaterialButton btnRetour = findViewById(R.id.btnRetourProfil);
        TextView tvNom = findViewById(R.id.tvNomEtudiant);
        TextView tvFormationNiveau = findViewById(R.id.tvFormationNiveau);
        TextView tvMoyenne = findViewById(R.id.tvMoyenneGenerale);
        TextView tvAbsences = findViewById(R.id.tvNombreAbsences);
        TextView tvProchainCours = findViewById(R.id.tvProchainCours);
        TextView tvProchainEvenement = findViewById(R.id.tvProchainEvenement);
        RecyclerView recyclerViewAnnonces = findViewById(R.id.recyclerViewDernieresAnnonces);
        recyclerViewAnnonces.setLayoutManager(new LinearLayoutManager(this));

        int userId = SessionManager.getUserId(this);
        User user = db.userDao().getById(userId);
        Etudiant etudiant = db.etudiantDao().getByUserId(userId);

        if (user != null) {
            tvNom.setText(user.getPrenom() + " " + user.getNom());
            tvFormationNiveau.setText(user.getFormation() + " · " + user.getNiveau());
        }

        if (etudiant != null) {
            afficherMoyenneGenerale(db, etudiant.getId(), tvMoyenne);
            afficherNombreAbsences(db, etudiant.getId(), tvAbsences);
            afficherProchainCours(db, etudiant.getFormationId(), tvProchainCours);
        }

        afficherProchainEvenement(db, tvProchainEvenement);
        afficherDernieresAnnonces(db, recyclerViewAnnonces);

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(ProfilActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void afficherMoyenneGenerale(AppDatabase db, int etudiantId, TextView tvMoyenne) {

        List<Note> notes = db.noteDao().getByEtudiantId(etudiantId);

        if (notes.isEmpty()) {
            tvMoyenne.setText("—");
            return;
        }

        double sommePonderee = 0;
        double sommeCoefficients = 0;

        for (Note note : notes) {
            sommePonderee += note.getMoyenne() * note.getCoefficient();
            sommeCoefficients += note.getCoefficient();
        }

        double moyenneGenerale = sommeCoefficients > 0
                ? sommePonderee / sommeCoefficients
                : 0;

        tvMoyenne.setText(String.format(Locale.FRANCE, "%.2f/20", moyenneGenerale));
    }

    private void afficherNombreAbsences(AppDatabase db, int etudiantId, TextView tvAbsences) {
        List<Absence> absences = db.absenceDao().getByEtudiantId(etudiantId);
        tvAbsences.setText(String.valueOf(absences.size()));
    }

    private void afficherProchainCours(AppDatabase db, int formationId, TextView tvProchainCours) {

        List<Cours> coursFormation = db.coursDao().getByFormationId(formationId);
        Date maintenant = new Date();

        Date meilleureDate = null;
        EmploiDuTemps meilleureSeance = null;
        Cours meilleurCours = null;

        for (Cours cours : coursFormation) {

            List<EmploiDuTemps> seances = db.emploiDuTempsDao().getByCoursId(cours.getId());

            for (EmploiDuTemps seance : seances) {

                try {
                    Date dateSeance = formatDateHeure.parse(
                            seance.getDate() + " " + seance.getHeureDebut()
                    );

                    if (dateSeance != null && dateSeance.after(maintenant)) {
                        if (meilleureDate == null || dateSeance.before(meilleureDate)) {
                            meilleureDate = dateSeance;
                            meilleureSeance = seance;
                            meilleurCours = cours;
                        }
                    }

                } catch (ParseException e) {
                    // Date mal formée, on ignore cette séance
                }
            }
        }

        if (meilleurCours != null) {
            tvProchainCours.setText(
                    meilleurCours.getNom() + " — Salle " + meilleureSeance.getSalle()
                            + "\n" + meilleureSeance.getDate() + " · "
                            + meilleureSeance.getHeureDebut() + " - " + meilleureSeance.getHeureFin()
            );
        } else {
            tvProchainCours.setText("Aucun cours à venir");
        }
    }

    private void afficherProchainEvenement(AppDatabase db, TextView tvProchainEvenement) {

        List<Annonce> annonces = db.annonceDao().getAll(); // déjà trié DESC par id

        for (Annonce annonce : annonces) {
            if ("Événement".equals(annonce.getCategorie())) {
                tvProchainEvenement.setText(annonce.getTitre() + "\n" + annonce.getDate());
                return;
            }
        }

        tvProchainEvenement.setText("Aucun événement à venir");
    }

    private void afficherDernieresAnnonces(AppDatabase db, RecyclerView recyclerView) {

        List<Annonce> annonces = db.annonceDao().getAll(); // déjà trié DESC par id
        List<AnnonceDisplay> displayList = new ArrayList<>();

        int limite = Math.min(3, annonces.size());

        for (int i = 0; i < limite; i++) {
            Annonce annonce = annonces.get(i);
            displayList.add(new AnnonceDisplay(
                    annonce.getTitre(),
                    annonce.getContenu(),
                    annonce.getDate(),
                    annonce.getAuteur(),
                    annonce.getCategorie()
            ));
        }

        recyclerView.setAdapter(new AnnonceAdapter(displayList));
    }
}