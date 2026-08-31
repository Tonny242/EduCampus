package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.data.repository.EmploiDuTempsRepository;

import java.util.List;

public class EmploiDuTempsViewModel extends AndroidViewModel {

    private final EmploiDuTempsRepository repository;

    public EmploiDuTempsViewModel(@NonNull Application application) {
        super(application);
        repository = new EmploiDuTempsRepository(application);
    }

    public long insert(EmploiDuTemps emploiDuTemps) {
        return repository.insert(emploiDuTemps);
    }

    public void update(EmploiDuTemps emploiDuTemps) {
        repository.update(emploiDuTemps);
    }

    public void delete(EmploiDuTemps emploiDuTemps) {
        repository.delete(emploiDuTemps);
    }

    public List<EmploiDuTemps> getAll() {
        return repository.getAll();
    }

    public EmploiDuTemps getById(int id) {
        return repository.getById(id);
    }

    public List<EmploiDuTemps> getByCoursId(int coursId) {
        return repository.getByCoursId(coursId);
    }

    public List<EmploiDuTemps> getByDate(String date) {
        return repository.getByDate(date);
    }
}