package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.FragmentLoginBinding;
import com.example.rutdomandroid.databinding.RentTimeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeFragment extends Fragment {
    RentTimeBinding binding;
    Bundle bundle;
    FirebaseAuth auth ;
    FirebaseFirestore db;
    String data,room,issue,time,date;

    List<String> time_values;
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
        }
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        List<String> timeValues = Arrays.asList(getResources().getStringArray(R.array.time_values));

        List<TimeSlot> timeSlots = new ArrayList<>();
        for (String timeValue : timeValues) {
            timeSlots.add(new TimeSlot(timeValue));
        }

        timeAdapter = new TimeAdapter(timeSlots, inflater.getContext());
        recyclerView = binding.timeRecycler;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timeAdapter);
        binding.bookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TimeSlot> timeValues=timeAdapter.getTimeSlots();
                for (TimeSlot timeSlot : timeValues) {

                    if (timeSlot.isSelected()) {
                        time = timeSlot.getTime();
                        break;
                    }
                }
                if (time.isEmpty()){
                    Toast.makeText(getContext(), "Выберите время мероприятия.", Toast.LENGTH_SHORT).show();
                    return;

                }
                String[] values = data.split("_");
                date = values[0];
                room = values[1];
                issue = values[2];
                Map<String, Object> map = new HashMap<>();
                Map<String, Object> data = new HashMap<>();
                data.put("0", auth.getCurrentUser().getUid());
                data.put("1", room);
                data.put("2", issue);
                map.put(time, data);
                db.collection("booking").
                        document(date).
                        set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Помещение забронировано.", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Попробуйте еще раз.", Toast.LENGTH_SHORT).show();

                            }
                        });


            }
        });
        return view;
    }


}