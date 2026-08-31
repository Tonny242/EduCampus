package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.NoteDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.Note;

import java.util.List;

public class NoteRepository {

    private final NoteDao noteDao;

    public NoteRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        noteDao = database.noteDao();
    }

    public long insert(Note note) {
        return noteDao.insert(note);
    }

    public void update(Note note) {
        noteDao.update(note);
    }

    public void delete(Note note) {
        noteDao.delete(note);
    }

    public List<Note> getAll() {
        return noteDao.getAll();
    }

    public Note getById(int id) {
        return noteDao.getById(id);
    }

    public List<Note> getByEtudiantId(int etudiantId) {
        return noteDao.getByEtudiantId(etudiantId);
    }

    public List<Note> getByCoursId(int coursId) {
        return noteDao.getByCoursId(coursId);
    }

    public List<Note> getByEtudiantAndSemestre(
            int etudiantId, String semestre) {
        return noteDao.getByEtudiantAndSemestre(etudiantId, semestre);
    }

    public void deleteAll() {
        noteDao.deleteAll();
    }
}