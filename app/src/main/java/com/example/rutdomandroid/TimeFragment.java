package com.example.rutdomandroid;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.FragmentLoginBinding;
import com.example.rutdomandroid.databinding.RentTimeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

public class TimeFragment extends Fragment {
    RentTimeBinding binding;
    List<String> time_values;
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(inflater.getContext(), RecyclerView.VERTICAL, false);
        List<String> timeValues = Arrays.asList(getResources().getStringArray(R.array.time_values));

        Button bookingButton = binding.bookingButton;
// Сюда нужно добавить функцию которая берет выбранное время и вставляет + обработать фором при выборе нескольких времен
        long date = System.currentTimeMillis();
        selectedTime = date;
        setAlarm(selectedTime);




        List<TimeSlot> timeSlots = new ArrayList<>();
        for (String timeValue : timeValues) {
            timeSlots.add(new TimeSlot(timeValue));
        }

        timeAdapter = new TimeAdapter(timeSlots, inflater.getContext());
        recyclerView = binding.timeRecycler;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(timeAdapter);
        return view;
    }
    private void setAlarm(long selectedTime) {
        alarmManager = (AlarmManager) this.getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this.getContext(), AlarmReceiver.class);
        pendingIntent2 = PendingIntent.getBroadcast(this.getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE );
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, selectedTime - AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_DAY, pendingIntent2);
        Toast.makeText(getContext(),"Напоминание запущено", Toast.LENGTH_LONG).show();

    }

    private void cancelAlarm(){
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        pendingIntent2 = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);
        if (alarmManager==null){
            alarmManager=(AlarmManager) this.getContext().getSystemService(ALARM_SERVICE);
        }
        alarmManager.cancel(pendingIntent2);
        Toast.makeText(getContext(),"Напоминание закрыто", Toast.LENGTH_LONG).show();

    }

}