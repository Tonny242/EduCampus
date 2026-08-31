package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Absence;

import java.util.List;

@Dao
public interface AbsenceDao {

    @Insert
    long insert(Absence absence);

    @Update
    void update(Absence absence);

    @Delete
    void delete(Absence absence);

    @Query("SELECT * FROM absences")
    List<Absence> getAll();

    @Query("SELECT * FROM absences WHERE id = :id LIMIT 1")
    Absence getById(int id);

    @Query("SELECT * FROM absences WHERE etudiantId = :etudiantId")
    List<Absence> getByEtudiantId(int etudiantId);

    @Query("SELECT * FROM absences WHERE coursId = :coursId")
    List<Absence> getByCoursId(int coursId);

    @Query("SELECT * FROM absences WHERE statut = :statut")
    List<Absence> getByStatut(String statut);

    @Query("DELETE FROM absences")
    void deleteAll();
}