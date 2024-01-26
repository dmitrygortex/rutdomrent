package com.example.rutdomandroid;

public class TimeSlot {
    private String time;
    private boolean isSelected;

    public TimeSlot(String time) {
        isSelected = false;
        this.time=time;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
