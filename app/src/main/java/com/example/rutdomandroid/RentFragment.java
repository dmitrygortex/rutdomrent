package com.example.rutdomandroid;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.ActivityMainBinding;
import com.example.rutdomandroid.databinding.FragmentRegisterBinding;
import com.example.rutdomandroid.databinding.FragmentRentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class RentFragment extends Fragment {
    FragmentRentBinding binding;
    Button next_button;
    String date, room, issue;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] values = getResources().getStringArray(R.array.rooms);
        Spinner spinner = binding.spinner;
        ArrayAdapter<String> adapter = new ArrayAdapter(inflater.getContext(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        next_button = binding.bookingButton;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                issue = binding.issueText.getText().toString();

                if (issue.isEmpty()) {
                    Toast.makeText(getContext(), "Поле цель посещения должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                } else if (room.isEmpty()) {
                    Toast.makeText(getContext(), "Поле тип помещение должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("date", date + "_" + room + "_" + issue);

                TimeFragment fragment = new TimeFragment();
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, fragment)
                        .setReorderingAllowed(false)
                        .addToBackStack("name")
                        .commit();
            }
        });
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        date = formatter.format(binding.calendarView.getDate());
        binding.booked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new CancelationFragment())
                        .setReorderingAllowed(false)
                        .addToBackStack("name")
                        .commit();
            }
        });
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