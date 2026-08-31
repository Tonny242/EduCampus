package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Enseignant;
import com.example.educampus.data.repository.EnseignantRepository;

import java.util.List;

public class EnseignantViewModel extends AndroidViewModel {

    private final EnseignantRepository repository;

    public EnseignantViewModel(@NonNull Application application) {
        super(application);
        repository = new EnseignantRepository(application);
    }

    public long insert(Enseignant enseignant) {
        return repository.insert(enseignant);
    }

    public void update(Enseignant enseignant) {
        repository.update(enseignant);
    }

    public void delete(Enseignant enseignant) {
        repository.delete(enseignant);
    }

    public List<Enseignant> getAll() {
        return repository.getAll();
    }

    public Enseignant getById(int id) {
        return repository.getById(id);
    }
}