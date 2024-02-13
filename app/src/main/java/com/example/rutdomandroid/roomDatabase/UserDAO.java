package com.example.rutdomandroid.roomDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {


    @Insert
    public void createUser(UserEntity userEntity);
    @Delete
    public void deleteUser(UserEntity UserEntity);
    @Query("SELECT * FROM users WHERE uid = :uid")
    UserEntity readUser(String uid);
    @Update()
    int updateUser(UserEntity userEntity);


//    @Query("SELECT * FROM users WHERE email = :email AND bookings = :bookings AND password = :password AND uid = :uid AND bookings = :bookings")
//    RentEntity getUser(String bookings, String email, String password, String uid, String institute, String fullName);

}
