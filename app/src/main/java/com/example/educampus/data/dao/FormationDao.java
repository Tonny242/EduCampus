package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Formation;

import java.util.List;

@Dao
public interface FormationDao {

    @Insert
    long insert(Formation formation);

    @Update
    void update(Formation formation);

    @Delete
    void delete(Formation formation);

    @Query("SELECT * FROM formations")
    List<Formation> getAll();

    @Query("SELECT * FROM formations WHERE id = :id LIMIT 1")
    Formation getById(int id);

    @Query("DELETE FROM formations")
    void deleteAll();
}