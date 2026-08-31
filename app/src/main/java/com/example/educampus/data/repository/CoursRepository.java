package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.CoursDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Cours;

import java.util.List;

public class CoursRepository {

    private final CoursDao coursDao;

    public CoursRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        coursDao = database.coursDao();
    }

    public long insert(Cours cours) {
        return coursDao.insert(cours);
    }

    public void update(Cours cours) {
        coursDao.update(cours);
    }

    public void delete(Cours cours) {
        coursDao.delete(cours);
    }

    public List<Cours> getAll() {
        return coursDao.getAll();
    }

    public Cours getById(int id) {
        return coursDao.getById(id);
    }

    public List<Cours> getByFormationId(int formationId) {
        return coursDao.getByFormationId(formationId);
    }

    public List<Cours> getByEnseignantId(int enseignantId) {
        return coursDao.getByEnseignantId(enseignantId);
    }

    public List<Cours> getBySemestre(String semestre) {
        return coursDao.getBySemestre(semestre);
    }

    public void deleteAll() {
        coursDao.deleteAll();
    }
}