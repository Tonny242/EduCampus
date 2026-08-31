package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Absence;
import com.example.educampus.data.repository.AbsenceRepository;

import java.util.List;

public class AbsenceViewModel extends AndroidViewModel {

    private final AbsenceRepository repository;

    public AbsenceViewModel(@NonNull Application application) {
        super(application);
        repository = new AbsenceRepository(application);
    }

    public long insert(Absence absence) {
        return repository.insert(absence);
    }

    public void update(Absence absence) {
        repository.update(absence);
    }

    public void delete(Absence absence) {
        repository.delete(absence);
    }

    public List<Absence> getAll() {
        return repository.getAll();
    }

    public Absence getById(int id) {
        return repository.getById(id);
    }

    public List<Absence> getByEtudiantId(int etudiantId) {
        return repository.getByEtudiantId(etudiantId);
    }

    public List<Absence> getByCoursId(int coursId) {
        return repository.getByCoursId(coursId);
    }

    public List<Absence> getByStatut(String statut) {
        return repository.getByStatut(statut);
    }
}