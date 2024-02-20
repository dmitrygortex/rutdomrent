package com.example.rutdomandroid;


import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rutdomandroid.roomDatabase.RentEntity;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.roomDatabase.RentDAO;
import com.example.rutdomandroid.adapter.TimeAdapter;
import com.example.rutdomandroid.databinding.RentTimeBinding;
import com.example.rutdomandroid.model.TimeSlot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class TimeFragment extends Fragment {
    RentTimeBinding binding;
    Bundle bundle;
    FirebaseAuth auth;
    FirebaseFirestore db;
    String data, room, purpose, time, date;
    String[] values;

    RecyclerView recyclerView;
    TimeAdapter timeAdapter;
    Button bookingButton;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent2;

    long selectedTime;


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
            purpose = values[2];

        }

        // Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        List<String> timeValues = Arrays.asList(getResources().getStringArray(R.array.time_values));

        List<TimeSlot> timeSlots = new ArrayList<>();
        for (String timeValue : timeValues) {
            timeSlots.add(new TimeSlot(timeValue));
        }
        List<String> booked_time = new ArrayList<>();
        timeAdapter = new TimeAdapter(timeSlots, inflater.getContext(), booked_time);
        recyclerView = binding.timeRecycler;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timeAdapter);
        db.collection(room).document(date).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Map<String, Object> data = snapshot.getData();
                    if (data != null) {
                        booked_time.clear();
                        for (Map.Entry<String, Object> entry : data.entrySet()) {
                            String timeFromDb = entry.getKey();
                            booked_time.add(timeFromDb);
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (TimeSlot timeSlot : timeSlots) {
                                    timeSlot.setEnable(!booked_time.contains(timeSlot.getTime()));
                                }
                                timeAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } else {

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
                        timeSlot.setSelected();
                        timeAdapter.setClickable();
                        break;
                    }
                }
                if (time.isEmpty()) {
                    Toast.makeText(getContext(), "Выберите время мероприятия.", Toast.LENGTH_SHORT).show();
                    return;

                }
                String hour = time.substring(0, 1);
                String currentDate = String.format("%s %s:00:00", date, hour);
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                Date dateAlarm = null;
                try {
                    dateAlarm = sdf.parse(currentDate);
                    long millis = dateAlarm.getTime();
                    setAlarm(millis);

                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                createRent();

            }
        });
        binding.backLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new RentFragment(), null)
                        .setReorderingAllowed(true)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void setAlarm(long selectedTime) {
        alarmManager = (AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), AlarmReceiver.class);
        pendingIntent2 = PendingIntent.getBroadcast(this.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, selectedTime - AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_DAY, pendingIntent2);
       /* getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Напоминание запущено", Toast.LENGTH_LONG).show();
            }
        });
*/
    }

    private void cancelAlarm() {
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent2 = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        if (alarmManager == null) {
            alarmManager = (AlarmManager) this.getContext().getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent2);
        // Toast.makeText(getContext(), "Напоминание закрыто", Toast.LENGTH_LONG).show();

    }

    public void createRent() {
        Map<String, Object> time_map = new HashMap<>();

        Map<String, Object> newBooking = new HashMap<>();
        newBooking.put("uid", auth.getCurrentUser().getUid());
        newBooking.put("purpose", purpose);
        newBooking.put("email", MainActivity.email);
        newBooking.put("fio", MainActivity.fio);
        newBooking.put("institute", MainActivity.institute);

        time_map.put(time, newBooking);


        db.collection(room).document(date)
                .set(time_map, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    Map<String, Object> bookings = new HashMap<>();
                    bookings.put("date", date);
                    bookings.put("time", time);
                    bookings.put("room", room);
                    bookings.put("purpose", purpose);

                    db.collection("users").document(auth.getCurrentUser().getUid()).update("bookings", FieldValue.arrayUnion(bookings)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            RentDatabase rentDatabase = MainActivity.getRentDatabase();
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    RentDAO rentDAO = rentDatabase.bookingDao();
                                    RentEntity existingBooking = rentDAO.getBooking(date, room, time, auth.getUid());

                                    if (existingBooking != null) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(), "Упс. Время уже забронировано", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    } else {
                                        RentEntity rent = new RentEntity();
                                        rent.setDate(date);
                                        rent.setRoom(room);
                                        rent.setPurpose(purpose);
                                        rent.setTime(time);
                                        rent.setUid(auth.getUid());
                                        rentDatabase.bookingDao().createRent(rent);
                                    }

                                }
                            });

                        }
                    });
                    Toast.makeText(getContext(), "Помещение забронировано!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Что-то пошло не так.", Toast.LENGTH_SHORT).show();

                });
    }

}