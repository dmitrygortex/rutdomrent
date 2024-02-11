package com.example.rutdomandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.adapter.RentInitAdapter;
import com.example.rutdomandroid.databinding.FragmentAdminCancelationBinding;
import com.example.rutdomandroid.databinding.FragmentAdminpanelBinding;
import com.example.rutdomandroid.model.RentInit;
import com.example.rutdomandroid.roomDatabase.RentDAO;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.roomDatabase.RentEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;


public class AdminCancelationFragment extends Fragment {
    FragmentAdminCancelationBinding binding;
    Button ban_button;
    Button unban_button;
    RecyclerView recyclerView;

    public AdminCancelationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminCancelationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);

        return view;
    }
}