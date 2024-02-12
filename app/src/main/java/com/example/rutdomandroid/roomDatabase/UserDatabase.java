package com.example.rutdomandroid.roomDatabase;
import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {UserEntity.class}, version = 1, exportSchema = true)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDao();
}