package com.example.rutdomandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.adapter.RentInitAdapter;
import com.example.rutdomandroid.databinding.FragmentAdminCalendar1Binding;
import com.example.rutdomandroid.databinding.FragmentAdminpanelBinding;
import com.example.rutdomandroid.databinding.FragmentCancelationBinding;
import com.example.rutdomandroid.model.RentInit;
import com.example.rutdomandroid.roomDatabase.RentDAO;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.roomDatabase.RentEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class AdminPanelFragment extends Fragment {
    FragmentAdminCalendar1Binding binding;
    Button cancel_button;
    RecyclerView recyclerView;
    String date,room;

    public AdminPanelFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        List<RentInit> bookings = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        binding = FragmentAdminCalendar1Binding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] values = new String[4];
        values[0]="Все комнаты";
        values[1]="Лекторий";

        values[2]="Переговорная";
        values[3]="Фотостудия";

        Spinner spinner = binding.spinner;
        CalendarView calendar=binding.calendarView;
        calendar.setMinDate(System.currentTimeMillis());
        ArrayAdapter<String> adapter = new ArrayAdapter(inflater.getContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                room = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button next_button = binding.booked;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (room.isEmpty()) {
                    Toast.makeText(getContext(), "Поле тип помещение должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("info", date + "_" + room);

                AdminInfoFragment fragment = new AdminInfoFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .setReorderingAllowed(false)
                        .addToBackStack(null)
                        .commit();

            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = formatter.format(binding.calendarView.getDate());
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                date = formatter.format(calendar.getTime());

            }
        });
        return view;
    }
}