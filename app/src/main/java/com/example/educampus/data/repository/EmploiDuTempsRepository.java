package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.EmploiDuTempsDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.EmploiDuTemps;

import java.util.List;

public class EmploiDuTempsRepository {

    private final EmploiDuTempsDao emploiDuTempsDao;

    public EmploiDuTempsRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        emploiDuTempsDao = database.emploiDuTempsDao();
    }

    public long insert(EmploiDuTemps emploiDuTemps) {
        return emploiDuTempsDao.insert(emploiDuTemps);
    }

    public void update(EmploiDuTemps emploiDuTemps) {
        emploiDuTempsDao.update(emploiDuTemps);
    }

    public void delete(EmploiDuTemps emploiDuTemps) {
        emploiDuTempsDao.delete(emploiDuTemps);
    }

    public List<EmploiDuTemps> getAll() {
        return emploiDuTempsDao.getAll();
    }

    public EmploiDuTemps getById(int id) {
        return emploiDuTempsDao.getById(id);
    }

    public List<EmploiDuTemps> getByCoursId(int coursId) {
        return emploiDuTempsDao.getByCoursId(coursId);
    }

    public List<EmploiDuTemps> getByDate(String date) {
        return emploiDuTempsDao.getByDate(date);
    }

    public void deleteAll() {
        emploiDuTempsDao.deleteAll();
    }
}