package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.EmploiDuTemps;
import com.example.educampus.data.model.EmploiDetails;

import java.util.List;

@Dao
public interface EmploiDuTempsDao {

    @Insert
    long insert(EmploiDuTemps emploiDuTemps);

    @Update
    void update(EmploiDuTemps emploiDuTemps);

    @Delete
    void delete(EmploiDuTemps emploiDuTemps);

    @Query("SELECT * FROM emploi_du_temps")
    List<EmploiDuTemps> getAll();

    @androidx.room.Transaction
    @Query("SELECT * FROM emploi_du_temps ORDER BY CASE date " +
            "WHEN 'Lundi' THEN 1 " +
            "WHEN 'Mardi' THEN 2 " +
            "WHEN 'Mercredi' THEN 3 " +
            "WHEN 'Jeudi' THEN 4 " +
            "WHEN 'Vendredi' THEN 5 " +
            "WHEN 'Samedi' THEN 6 " +
            "WHEN 'Dimanche' THEN 7 END, heureDebut")
    List<EmploiDetails> getAllWithDetails();

    @Query("SELECT * FROM emploi_du_temps WHERE id = :id LIMIT 1")
    EmploiDuTemps getById(int id);

    @Query("SELECT * FROM emploi_du_temps WHERE coursId = :coursId")
    List<EmploiDuTemps> getByCoursId(int coursId);

    @Query("SELECT * FROM emploi_du_temps WHERE date = :date")
    List<EmploiDuTemps> getByDate(String date);

    @Query("DELETE FROM emploi_du_temps")
    void deleteAll();
}