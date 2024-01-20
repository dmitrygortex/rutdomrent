package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rutdomandroid.databinding.ActivityMainBinding;

import java.math.BigInteger;


public class InfoFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        MainActivity mainActivity = (MainActivity) requireActivity();
        if (mainActivity != null) {
            mainActivity.setButtonNavViewVisibility(View.VISIBLE);
        }

        return view;
    }
}

