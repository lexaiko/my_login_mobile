package com.example.lesson3.data;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao
{
    @Query("Select * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username IN (:usernameList)")
    List<User> loadAllByIds(String[] usernameList);

    @Query("SELECT * FROM user LIMIT 1")
    User selectOne();

    @Query("UPDATE user SET password = :newPassword WHERE username = :username")
    void updatePassword(String username, String newPassword);


    @Query ("SELECT * FROM user WHERE username = :username AND password = :password LIMIT 1")
    User findByUsernameAndPassword(String username, String password);

    @Query("SELECT * FROM user WHERE username = :username AND resetCode = '0000' LIMIT 1")
    User verifyResetCode(String username);


    @Insert
    void insertAll(User... user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

}