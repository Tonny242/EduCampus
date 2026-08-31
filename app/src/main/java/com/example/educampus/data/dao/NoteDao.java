package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Note;
import com.example.educampus.data.model.NoteDetails;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("SELECT * FROM notes")
    List<Note> getAll();

    @androidx.room.Transaction
    @Query("SELECT * FROM notes")
    List<NoteDetails> getAllWithDetails();

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    Note getById(int id);

    @Query("SELECT * FROM notes WHERE etudiantId = :etudiantId")
    List<Note> getByEtudiantId(int etudiantId);

    @Query("SELECT * FROM notes WHERE coursId = :coursId")
    List<Note> getByCoursId(int coursId);

    @Query("SELECT * FROM notes WHERE etudiantId = :etudiantId AND semestre = :semestre")
    List<Note> getByEtudiantAndSemestre(int etudiantId, String semestre);

    @Query("DELETE FROM notes")
    void deleteAll();
}