package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rutdomandroid.adapter.RentInitAdapter;
import com.example.rutdomandroid.databinding.FragmentCancelationBinding;
import com.example.rutdomandroid.model.RentInit;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CancelationFragment extends Fragment {
    FragmentCancelationBinding binding;
    Button cancel_button;
    RecyclerView recyclerView;

    public CancelationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        List<RentInit> bookings = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        binding = FragmentCancelationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
        userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Map<String, String>> bookingsData = (List<Map<String, String>>) documentSnapshot.get("bookings");
                        if (bookingsData != null) {
                            for (Map<String, String> rent : bookingsData) {
                                String room = rent.get("room");
                                String purpose = rent.get("purpose");
                                String date = rent.get("date");
                                String time = rent.get("time");
                                String year = date.split("\\.")[2];
                                Toast.makeText(getContext(), String.format("%s %s %s %s", room, date, time, purpose), Toast.LENGTH_SHORT).show();
                                RentInit rentInit = new RentInit(user.getUid(), year, date, time, room, purpose);
                                bookings.add(rentInit);
                            }
                            RentInitAdapter bookingAdapter = new RentInitAdapter(inflater.getContext(),bookings);
                            recyclerView = binding.rentInitsRecycler;
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(bookingAdapter);
                        }
                        else{
                            RentInitAdapter bookingAdapter = new RentInitAdapter(inflater.getContext(),bookings);
                            recyclerView = binding.rentInitsRecycler;
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(bookingAdapter);
                        }
                    }
                });







        return view;
    }
}