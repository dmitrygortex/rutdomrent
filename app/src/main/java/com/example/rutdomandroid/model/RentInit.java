package com.example.rutdomandroid.model;

public class RentInit {
    int id;
    String year;
    String date;
    String time;
    String room;
    String reasonOfRent;

    public RentInit(int id, String year, String date, String time, String room, String reasonOfRent) {
        this.id = id;
        this.year = year;
        this.date = date;
        this.time = time;
        this.room = room;
        this.reasonOfRent = reasonOfRent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
