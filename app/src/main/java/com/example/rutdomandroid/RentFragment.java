package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rutdomandroid.databinding.ActivityMainBinding;
import com.example.rutdomandroid.databinding.FragmentRegisterBinding;
import com.example.rutdomandroid.databinding.FragmentRentBinding;

public class RentFragment extends Fragment {
    FragmentRentBinding binding;
    Button next_button;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        String[] values=getResources().getStringArray(R.array.rooms);
        Spinner spinner=binding.spinner;
        ArrayAdapter<String> adapter=new ArrayAdapter(inflater.getContext(),android.R.layout.simple_spinner_item,values);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        next_button=binding.bookingButton;
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager =requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new TimeFragment(), null)
                        .setReorderingAllowed(false)
                        .addToBackStack("name") // Name can be null
                        .commit();
            }
        });
        return view;
    }
}