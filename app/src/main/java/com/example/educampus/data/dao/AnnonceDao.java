package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.Annonce;

import java.util.List;

@Dao
public interface AnnonceDao {

    @Insert
    long insert(Annonce annonce);

    @Update
    void update(Annonce annonce);

    @Delete
    void delete(Annonce annonce);

    @Query("SELECT * FROM annonces ORDER BY id DESC")
    List<Annonce> getAll();

    @Query("SELECT * FROM annonces WHERE id = :id LIMIT 1")
    Annonce getById(int id);

    @Query("SELECT * FROM annonces WHERE importante = 1")
    List<Annonce> getImportantes();

    @Query("SELECT * FROM annonces WHERE titre LIKE '%' || :requete || '%' OR contenu LIKE '%' || :requete || '%'")
    List<Annonce> search(String requete);

    @Query("DELETE FROM annonces")
    void deleteAll();
}