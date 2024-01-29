package com.example.rutdomandroid;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.RentTimeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeFragment extends Fragment {
    RentTimeBinding binding;
    Bundle bundle;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String data, room, issue, time, date;
    String[] values;

    RecyclerView recyclerView;
    TimeAdapter timeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RentTimeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle bundle;
        bundle = getArguments();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        if (bundle != null) {
            data = bundle.getString("date");
            values = data.split("_");
            date = values[0];
            room = values[1];
            issue = values[2];

        }

        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        List<String> timeValues = Arrays.asList(getResources().getStringArray(R.array.time_values));

        List<TimeSlot> timeSlots = new ArrayList<>();
        for (String timeValue : timeValues) {
            timeSlots.add(new TimeSlot(timeValue));
        }
        List<String> booked_time = new ArrayList<>();
        db.collection("bookings").whereEqualTo("room", room)
                .whereEqualTo("date", date)
                .get() .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult().isEmpty()) {

                        Toast.makeText(getContext(), "1", Toast.LENGTH_SHORT).show();

                        timeAdapter = new TimeAdapter(timeSlots, inflater.getContext(), booked_time);
                        recyclerView = binding.timeRecycler;
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(timeAdapter);
                        } else {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String time_db = (String) document.get("time");
                                booked_time.add(time_db);

                            }
                            Toast.makeText(getContext(), booked_time.toString(), Toast.LENGTH_SHORT).show();

                            timeAdapter = new TimeAdapter(timeSlots, inflater.getContext(), booked_time);
                            recyclerView = binding.timeRecycler;
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(timeAdapter);
                        }
                    }
                });



        binding.bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TimeSlot> timeValues = timeAdapter.getTimeSlots();
                for (TimeSlot timeSlot : timeValues) {

                    if (timeSlot.isSelected()) {
                        time = timeSlot.getTime();
                        break;
                    }
                }
                if (time.isEmpty()) {
                    Toast.makeText(getContext(), "Выберите время мероприятия.", Toast.LENGTH_SHORT).show();
                    return;

                }


                String bookingId = db.collection("bookings").document().getId();

                Map<String, Object> newBooking = new HashMap<>();
                newBooking.put("uid", auth.getCurrentUser().getUid());
                newBooking.put("room", room);
                newBooking.put("date", date);
                newBooking.put("time", time);

                db.collection("bookings").document(bookingId)
                        .set(newBooking)
                        .addOnSuccessListener(aVoid -> {
                            Map<String, Object> bookings = new HashMap<>();
                            bookings.put("date", date);
                            bookings.put("time", time);
                            bookings.put("room", room);
                            bookings.put("purpose", issue);

                            db.collection("users").document(auth.getCurrentUser().getUid()).update("bookings", FieldValue.arrayUnion(bookings)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            });
                            Toast.makeText(getContext(), "Помещение забронировано!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Что-то пошло не так.", Toast.LENGTH_SHORT).show();

                        });
                Toast.makeText(getContext(), time, Toast.LENGTH_SHORT).show();


            }
        });
        binding.backLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new RentFragment(), null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        return view;
    }


}