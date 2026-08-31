package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.FormationDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Formation;

import java.util.List;

public class FormationRepository {

    private final FormationDao formationDao;

    public FormationRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        formationDao = database.formationDao();
    }

    public long insert(Formation formation) {
        return formationDao.insert(formation);
    }

    public void update(Formation formation) {
        formationDao.update(formation);
    }

    public void delete(Formation formation) {
        formationDao.delete(formation);
    }

    public List<Formation> getAll() {
        return formationDao.getAll();
    }

    public Formation getById(int id) {
        return formationDao.getById(id);
    }

    public void deleteAll() {
        formationDao.deleteAll();
    }
}