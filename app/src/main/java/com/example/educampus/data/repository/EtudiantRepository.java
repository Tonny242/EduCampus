package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.EtudiantDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Etudiant;

import java.util.List;

public class EtudiantRepository {

    private final EtudiantDao etudiantDao;

    public EtudiantRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        etudiantDao = database.etudiantDao();
    }

    public long insert(Etudiant etudiant) {
        return etudiantDao.insert(etudiant);
    }

    public void update(Etudiant etudiant) {
        etudiantDao.update(etudiant);
    }

    public void delete(Etudiant etudiant) {
        etudiantDao.delete(etudiant);
    }

    public List<Etudiant> getAll() {
        return etudiantDao.getAll();
    }

    public Etudiant getById(int id) {
        return etudiantDao.getById(id);
    }

    public Etudiant getByUserId(int userId) {
        return etudiantDao.getByUserId(userId);
    }

    public List<Etudiant> getByFormationId(int formationId) {
        return etudiantDao.getByFormationId(formationId);
    }

    public void deleteAll() {
        etudiantDao.deleteAll();
    }
}