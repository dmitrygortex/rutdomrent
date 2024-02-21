package com.example.rutdomandroid.model;

public class UserInit {
    String institute;
    String email;
    String fio;
    String time;
    String room;
    String purpose;

    public UserInit(String institute, String email, String fio, String time, String room, String purpose) {
        this.institute = institute;
        this.email = email;
        this.fio = fio;
        this.time = time;
        this.room = room;
        this.purpose = purpose;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTime() {
        return time;
    }

    public String getRoom() {
        return room;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
