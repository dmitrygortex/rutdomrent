package com.example.rutdomandroid.model;

public class RentInit {
    String uid;
    String year;
    String date;
    String time;
    String room;
    String reasonOfRent;

    public RentInit(String uid, String year, String date, String time, String room, String reasonOfRent) {
        this.uid=uid;
        this.year = year;
        this.date = date;
        this.time = time;
        this.room = room;
        this.reasonOfRent = reasonOfRent;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getReasonOfRent() {
        return reasonOfRent;
    }

    public void setReasonOfRent(String reasonOfRent) {
        this.reasonOfRent = reasonOfRent;
    }
}
