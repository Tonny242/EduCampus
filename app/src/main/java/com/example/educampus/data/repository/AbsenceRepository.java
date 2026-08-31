package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.AbsenceDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Absence;

import java.util.List;

public class AbsenceRepository {

    private final AbsenceDao absenceDao;

    public AbsenceRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        absenceDao = database.absenceDao();
    }

    public long insert(Absence absence) {
        return absenceDao.insert(absence);
    }

    public void update(Absence absence) {
        absenceDao.update(absence);
    }

    public void delete(Absence absence) {
        absenceDao.delete(absence);
    }

    public List<Absence> getAll() {
        return absenceDao.getAll();
    }

    public Absence getById(int id) {
        return absenceDao.getById(id);
    }

    public List<Absence> getByEtudiantId(int etudiantId) {
        return absenceDao.getByEtudiantId(etudiantId);
    }

    public List<Absence> getByCoursId(int coursId) {
        return absenceDao.getByCoursId(coursId);
    }

    public List<Absence> getByStatut(String statut) {
        return absenceDao.getByStatut(statut);
    }

    public void deleteAll() {
        absenceDao.deleteAll();
    }
}