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
import com.example.rutdomandroid.roomDatabase.RentDAO;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.roomDatabase.RentEntity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

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
        RentDatabase rentDatabase = MainActivity.getRentDatabase();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                RentDAO rentDAO = rentDatabase.bookingDao();
                List<RentEntity> rentEntities = rentDAO.getAllBookings(auth.getUid());
                if (!rentEntities.isEmpty()) {
                    for (RentEntity rent : rentEntities) {
                        String room = rent.getRoom();
                        String purpose = rent.getPurpose();
                        String date = rent.getDate();
                        String time = rent.getTime();
                        String year = date.split("\\.")[2];
                        //Toast.makeText(getContext(), String.format("%s %s %s %s", room, date, time, purpose), Toast.LENGTH_SHORT).show();
                        RentInit rentInit = new RentInit(user.getUid(), year, date, time, room, purpose);
                        bookings.add(rentInit);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RentInitAdapter bookingAdapter = new RentInitAdapter(inflater.getContext(), bookings);
                            recyclerView = binding.rentInitsRecycler;
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(bookingAdapter);
                        }
                    });
                }
            }
        });
        return view;
    }
}