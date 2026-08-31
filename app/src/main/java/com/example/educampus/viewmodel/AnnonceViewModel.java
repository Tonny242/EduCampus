package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Annonce;
import com.example.educampus.data.repository.AnnonceRepository;

import java.util.List;

public class AnnonceViewModel extends AndroidViewModel {

    private final AnnonceRepository repository;

    public AnnonceViewModel(@NonNull Application application) {
        super(application);
        repository = new AnnonceRepository(application);
    }

    public long insert(Annonce annonce) {
        return repository.insert(annonce);
    }

    public void update(Annonce annonce) {
        repository.update(annonce);
    }

    public void delete(Annonce annonce) {
        repository.delete(annonce);
    }

    public List<Annonce> getAll() {
        return repository.getAll();
    }

    public Annonce getById(int id) {
        return repository.getById(id);
    }

    public List<Annonce> getImportantes() {
        return repository.getImportantes();
    }
}