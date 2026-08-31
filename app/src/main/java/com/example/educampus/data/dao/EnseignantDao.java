package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Enseignant;

import java.util.List;

@Dao
public interface EnseignantDao {

    @Insert
    long insert(Enseignant enseignant);

    @Update
    void update(Enseignant enseignant);

    @Delete
    void delete(Enseignant enseignant);

    @Query("SELECT * FROM enseignants")
    List<Enseignant> getAll();

    @Query("SELECT * FROM enseignants WHERE id = :id LIMIT 1")
    Enseignant getById(int id);

    @Query("SELECT * FROM enseignants WHERE nom LIKE '%' || :requete || '%' OR prenom LIKE '%' || :requete || '%' OR specialite LIKE '%' || :requete || '%'")
    List<Enseignant> search(String requete);

    @Query("DELETE FROM enseignants")
    void deleteAll();
}