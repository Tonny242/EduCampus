package com.example.educampus.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.educampus.data.entity.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM utilisateurs")
    List<User> getAll();

    @Query("SELECT * FROM utilisateurs WHERE id = :id LIMIT 1")
    User getById(int id);

    @Query("SELECT * FROM utilisateurs WHERE email = :email LIMIT 1")
    User getByEmail(String email);

    @Query("DELETE FROM utilisateurs")
    void deleteAll();
}