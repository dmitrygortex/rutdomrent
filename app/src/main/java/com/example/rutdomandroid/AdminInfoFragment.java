package com.example.rutdomandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.adapter.TimeAdapter;
import com.example.rutdomandroid.adapter.UserInitAdapter;
import com.example.rutdomandroid.databinding.FragmentAdminCalendar1Binding;
import com.example.rutdomandroid.databinding.FragmentAdminCalendar2Binding;
import com.example.rutdomandroid.databinding.FragmentAdminCancelationBinding;
import com.example.rutdomandroid.model.RentInit;
import com.example.rutdomandroid.model.UserInit;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdminInfoFragment extends Fragment {
    FragmentAdminCalendar2Binding binding;
    FirebaseAuth auth;
    FirebaseFirestore db;
    List<UserInit> bookings;
    Button ban_button;
    Button unban_button;
    TextView textView;
    RecyclerView recyclerView;
    String date, room, data,day,month, year,reason,fio,email,institute;

    public AdminInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminCalendar2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        Bundle bundle;
        bundle = getArguments();
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        if (bundle != null) {
            data = bundle.getString("info");
            String[] values = data.split("_");
            date = values[0];
            room = values[1];
        }
        textView = binding.textUsername;
        day = date.split("\\.")[0];
        month = date.split("\\.")[1];
        year = date.split("\\.")[2];

        switch (month) {
            case "01":
                month = "Января";
                break;
            case "02":
                month = "Февраля";
                break;
            case "03":
                month = "Марта";
                break;
            case "04":
                month = "Апреля";
                break;
            case "05":
                month = "Мая";
                break;
            case "06":
                month = "Июня";
                break;
            case "07":
                month = "Июля";
                break;
            case "08":
                month = "Авгуса";
                break;

            case "09":
                month = "Сентября";
                break;

            case "10":
                month = "Октября";
                break;

            case "11":
                month = "Ноября";
                break;

            case "12":
                month = "Декабря";
                break;
        }
        textView.setText(String.format("%s %s %s г.",day,month,year));
        String[] values = getResources().getStringArray(R.array.rooms);


        if (room.equals("Все комнаты")){
            getRent("Лекторий");
            getRent("Фотостудия");
            getRent("Переговорная");
        }
        else getRent(room);
        Toast.makeText(getContext(), String.format("%s %s",room,data), Toast.LENGTH_SHORT).show();



        UserInitAdapter userInitAdapter = new UserInitAdapter(inflater.getContext(), bookings);
        recyclerView = binding.adminCalendarInitsRecycler;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userInitAdapter);

     /*   db.collection(room).document(date).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String rentInit;
                    Map<String, Object> data = documentSnapshot.getData();
                    if (data != null) {
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            String timeFromDb = entry.getKey();
                            Map<String, String> time_map = (Map<String, String>) data.get(timeFromDb);
                            reason=  time_map.get("purpose");
                            fio=  time_map.get("fio");
                            email=  time_map.get("email");
                            institute=  time_map.get("institute");
                            RentInit rentInit1=new RentInit(auth.getUid(),)
                            UserInit userInit=new UserInit(fio,institute,email,)
                        }
            }
     */

        return view;
    }
    public void getRent(String room) {
        db.collection(room).document(date).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String rentInit;
                    Map<String, Object> data = documentSnapshot.getData();
                    if (data != null) {
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            String timeFromDb = entry.getKey();
                            Map<String, String> time_map = (Map<String, String>) data.get(timeFromDb);
                            String reason = time_map.get("purpose");
                            String fio = time_map.get("fio");
                            String email = time_map.get("email");
                            String institute = time_map.get("institute");
                            Toast.makeText(getContext(), String.format("%s %s %s",fio,email), Toast.LENGTH_SHORT).show();

                            UserInit userInit = new UserInit(institute, email, fio, timeFromDb, room, reason);
                            bookings.add(userInit);
                        }
                    }
                }
            }
        });
    }

}