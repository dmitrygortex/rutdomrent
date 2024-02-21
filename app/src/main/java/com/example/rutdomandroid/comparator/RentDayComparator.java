package com.example.rutdomandroid.comparator;

import com.example.rutdomandroid.model.RentInit;
import com.example.rutdomandroid.roomDatabase.RentEntity;

import java.util.Comparator;

public class RentDayComparator implements Comparator<RentInit> {

    @Override
    public int compare(RentInit o1, RentInit o2) {
        return o1.getDate().split("\\.")[0].compareTo(o2.getDate().split("\\.")[0]);
    }
}
