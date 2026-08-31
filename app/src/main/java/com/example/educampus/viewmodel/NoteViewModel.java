package com.example.educampus.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.educampus.data.entity.Note;
import com.example.educampus.data.repository.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private final NoteRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    public long insert(Note note) {
        return repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public List<Note> getAll() {
        return repository.getAll();
    }

    public Note getById(int id) {
        return repository.getById(id);
    }

    public List<Note> getByEtudiantId(int etudiantId) {
        return repository.getByEtudiantId(etudiantId);
    }

    public List<Note> getByCoursId(int coursId) {
        return repository.getByCoursId(coursId);
    }

    public List<Note> getByEtudiantAndSemestre(
            int etudiantId, String semestre) {
        return repository.getByEtudiantAndSemestre(
                etudiantId, semestre);
    }
}