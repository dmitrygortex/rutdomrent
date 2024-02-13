package com.example.rutdomandroid.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {RentEntity.class}, version = 2, exportSchema = true)
public abstract class RentDatabase extends RoomDatabase {
    public abstract RentDAO bookingDao();



}
