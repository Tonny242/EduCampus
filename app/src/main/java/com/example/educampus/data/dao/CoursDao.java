package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Cours;
import com.example.educampus.data.model.CoursDetails;

import java.util.List;

@Dao
public interface CoursDao {

    @Insert
    long insert(Cours cours);

    @Update
    void update(Cours cours);

    @Delete
    void delete(Cours cours);

    @Query("SELECT * FROM cours")
    List<Cours> getAll();

    @androidx.room.Transaction
    @Query("SELECT * FROM cours")
    List<CoursDetails> getAllWithDetails();

    @Query("SELECT * FROM cours WHERE id = :id LIMIT 1")
    Cours getById(int id);

    @Query("SELECT * FROM cours WHERE formationId = :formationId")
    List<Cours> getByFormationId(int formationId);

    @Query("SELECT * FROM cours WHERE enseignantId = :enseignantId")
    List<Cours> getByEnseignantId(int enseignantId);

    @Query("SELECT * FROM cours WHERE semestre = :semestre")
    List<Cours> getBySemestre(String semestre);

    @Query("SELECT * FROM cours WHERE nom LIKE '%' || :requete || '%' OR code LIKE '%' || :requete || '%'")
    List<Cours> search(String requete);

    @Query("DELETE FROM cours")
    void deleteAll();
}