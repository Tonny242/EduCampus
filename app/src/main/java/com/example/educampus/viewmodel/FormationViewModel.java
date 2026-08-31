package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Formation;
import com.example.educampus.data.repository.FormationRepository;

import java.util.List;

public class FormationViewModel extends AndroidViewModel {

    private final FormationRepository repository;

    public FormationViewModel(@NonNull Application application) {
        super(application);
        repository = new FormationRepository(application);
    }

    public long insert(Formation formation) {
        return repository.insert(formation);
    }

    public void update(Formation formation) {
        repository.update(formation);
    }

    public void delete(Formation formation) {
        repository.delete(formation);
    }

    public List<Formation> getAll() {
        return repository.getAll();
    }

    public Formation getById(int id) {
        return repository.getById(id);
    }
}