package com.example.rutdomandroid.model;

public class TimeSlot {
    private String time;
    private boolean isEnable;
    private boolean isSelected;

    public TimeSlot(String time) {
        isEnable = true;
        isSelected=false;
        this.time=time;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean q) {
        this.isEnable = q;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected() {
        this.isSelected = !isSelected;
    }
}
