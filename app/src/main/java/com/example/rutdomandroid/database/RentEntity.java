package com.example.rutdomandroid.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookings")
public class RentEntity {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name ="room")
    private String room;
    @ColumnInfo(name ="time")
    private String time;
    @ColumnInfo(name="purpose")
    private String purpose;
    @ColumnInfo(name ="date")
    private String date;
    @ColumnInfo(name ="uid")
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
