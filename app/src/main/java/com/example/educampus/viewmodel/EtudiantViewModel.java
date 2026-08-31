package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.repository.EtudiantRepository;

import java.util.List;

public class EtudiantViewModel extends AndroidViewModel {

    private final EtudiantRepository repository;

    public EtudiantViewModel(@NonNull Application application) {
        super(application);
        repository = new EtudiantRepository(application);
    }

    public long insert(Etudiant etudiant) {
        return repository.insert(etudiant);
    }

    public void update(Etudiant etudiant) {
        repository.update(etudiant);
    }

    public void delete(Etudiant etudiant) {
        repository.delete(etudiant);
    }

    public List<Etudiant> getAll() {
        return repository.getAll();
    }

    public Etudiant getById(int id) {
        return repository.getById(id);
    }

    public Etudiant getByUserId(int userId) {
        return repository.getByUserId(userId);
    }

    public List<Etudiant> getByFormationId(int formationId) {
        return repository.getByFormationId(formationId);
    }
}