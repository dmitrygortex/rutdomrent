package com.example.rutdomandroid.model;

public class UserInit {
    String rentedHours;
    String userName;
    String group;
    String email;

    public UserInit(String rentedHours, String userName, String group, String email) throws Throwable {
        setRentedHours(rentedHours);
        setUserName(userName);
        setGroup(group);
        setEmail(email);
    }

    public String getRentedHours() {
        return rentedHours;
    }

    public void setRentedHours(String rentedHours) throws Throwable {
        if (Integer.parseInt(rentedHours) >= 0){
        this.rentedHours = rentedHours;}
        else{
            throw new IllegalArgumentException("Rented hours >= 0");
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
