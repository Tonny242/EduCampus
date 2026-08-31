package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.EnseignantDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Enseignant;

import java.util.List;

public class EnseignantRepository {

    private final EnseignantDao enseignantDao;

    public EnseignantRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        enseignantDao = database.enseignantDao();
    }

    public long insert(Enseignant enseignant) {
        return enseignantDao.insert(enseignant);
    }

    public void update(Enseignant enseignant) {
        enseignantDao.update(enseignant);
    }

    public void delete(Enseignant enseignant) {
        enseignantDao.delete(enseignant);
    }

    public List<Enseignant> getAll() {
        return enseignantDao.getAll();
    }

    public Enseignant getById(int id) {
        return enseignantDao.getById(id);
    }

    public void deleteAll() {
        enseignantDao.deleteAll();
    }
}