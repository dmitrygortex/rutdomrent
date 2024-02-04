package com.example.rutdomandroid.roomDatabase;

import androidx.room.*;

import java.util.List;

@Dao
public interface RentDAO {
    @Insert
    public void createRent(RentEntity rentEntity);

    @Delete
    public void deleteRent(RentEntity rentEntity);
    @Query("SELECT * FROM bookings WHERE date = :date AND room = :room AND time = :time AND uid = :uid")
    RentEntity getBooking(String date, String room, String time, String uid);

    @Query("SELECT * FROM bookings WHERE uid = :uid")
    List<RentEntity> getAllBookings(String uid);




}
