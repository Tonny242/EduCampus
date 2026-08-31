package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Etudiant;
import com.example.educampus.data.model.EtudiantDetails;

import java.util.List;

@Dao
public interface EtudiantDao {

    @Insert
    long insert(Etudiant etudiant);

    @Update
    void update(Etudiant etudiant);

    @Delete
    void delete(Etudiant etudiant);

    @Query("SELECT * FROM etudiants")
    List<Etudiant> getAll();

    @androidx.room.Transaction
    @Query("SELECT * FROM etudiants")
    List<EtudiantDetails> getAllWithDetails();

    @Query("SELECT * FROM etudiants WHERE id = :id LIMIT 1")
    Etudiant getById(int id);

    @Query("SELECT * FROM etudiants WHERE userId = :userId LIMIT 1")
    Etudiant getByUserId(int userId);

    @Query("SELECT * FROM etudiants WHERE formationId = :formationId")
    List<Etudiant> getByFormationId(int formationId);

    @Query("DELETE FROM etudiants")
    void deleteAll();
}