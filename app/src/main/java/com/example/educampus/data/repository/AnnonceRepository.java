package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.AnnonceDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Annonce;

import java.util.List;

public class AnnonceRepository {

    private final AnnonceDao annonceDao;

    public AnnonceRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        annonceDao = database.annonceDao();
    }

    public long insert(Annonce annonce) {
        return annonceDao.insert(annonce);
    }

    public void update(Annonce annonce) {
        annonceDao.update(annonce);
    }

    public void delete(Annonce annonce) {
        annonceDao.delete(annonce);
    }

    public List<Annonce> getAll() {
        return annonceDao.getAll();
    }

    public Annonce getById(int id) {
        return annonceDao.getById(id);
    }

    public List<Annonce> getImportantes() {
        return annonceDao.getImportantes();
    }

    public void deleteAll() {
        annonceDao.deleteAll();
    }
}