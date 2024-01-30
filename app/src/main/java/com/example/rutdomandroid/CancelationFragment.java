package com.example.rutdomandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.databinding.FragmentCancelationBinding;
import com.example.rutdomandroid.databinding.RentTimeBinding;
import com.example.rutdomandroid.model.RentInit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CancelationFragment extends Fragment {
    RecyclerView rentInitRecycler;
    FragmentCancelationBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCancelationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        List<RentInit> rentInitList = new ArrayList<>();
        rentInitList.add(new RentInit(1, "2024", "2 june", "12:00 - 14:00", "Photostudio", "idk"));
        rentInitList.add(new RentInit(2, "2024", "8 june", "12:00 - 14:00", "Photostudio", "geeking"));
        setRentInitRecycler(rentInitList);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setRentInitRecycler(List<RentInit> rentInitList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
    }
}


















