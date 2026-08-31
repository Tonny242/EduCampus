package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.repository.CoursRepository;

import java.util.List;

public class CoursViewModel extends AndroidViewModel {

    private final CoursRepository repository;

    public CoursViewModel(@NonNull Application application) {
        super(application);
        repository = new CoursRepository(application);
    }

    public long insert(Cours cours) {
        return repository.insert(cours);
    }

    public void update(Cours cours) {
        repository.update(cours);
    }

    public void delete(Cours cours) {
        repository.delete(cours);
    }

    public List<Cours> getAll() {
        return repository.getAll();
    }

    public Cours getById(int id) {
        return repository.getById(id);
    }

    public List<Cours> getByFormationId(int formationId) {
        return repository.getByFormationId(formationId);
    }

    public List<Cours> getByEnseignantId(int enseignantId) {
        return repository.getByEnseignantId(enseignantId);
    }

    public List<Cours> getBySemestre(String semestre) {
        return repository.getBySemestre(semestre);
    }
}