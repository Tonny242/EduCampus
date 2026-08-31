package com.example.educampus.data.repository;

import android.app.Application;

import com.example.educampus.data.dao.UserDao;
import com.example.educampus.data.database.AppDatabase;
import com.example.educampus.data.database.DatabaseProvider;
import com.example.educampus.data.entity.User;

import java.util.List;

public class UserRepository {

    private final UserDao userDao;

    public UserRepository(Application application) {
        AppDatabase database = DatabaseProvider.getDatabase(application);
        userDao = database.userDao();
    }

    public long insert(User user) {
        return userDao.insert(user);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(User user) {
        userDao.delete(user);
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public User getById(int id) {
        return userDao.getById(id);
    }

    public User getByEmail(String email) {
        return userDao.getByEmail(email);
    }

    public void deleteAll() {
        userDao.deleteAll();
    }
}