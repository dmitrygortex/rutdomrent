package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rutdomandroid.adapter.RentInitAdapter;
import com.example.rutdomandroid.comparator.RentDayComparator;
import com.example.rutdomandroid.comparator.RentMonthComparator;
import com.example.rutdomandroid.comparator.RentTimeComparator;
import com.example.rutdomandroid.comparator.RentYearComparator;
import com.example.rutdomandroid.databinding.FragmentCancelationBinding;
import com.example.rutdomandroid.model.RentInit;
import com.example.rutdomandroid.roomDatabase.RentDAO;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.roomDatabase.RentEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

public class CancelationFragment extends Fragment {
    FragmentCancelationBinding binding;
    RecyclerView recyclerView;
    Comparator<RentInit>  comparator = new RentYearComparator().thenComparing( new RentMonthComparator().thenComparing(new RentDayComparator().thenComparing(new RentTimeComparator())));
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
                        RentInit rentInit = new RentInit(user.getUid(), year, date, time, room, purpose);
                        bookings.add(rentInit);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bookings.sort(comparator);
                            RentInitAdapter bookingAdapter = new RentInitAdapter(inflater.getContext(), bookings);
                            recyclerView = binding.rentInitsRecycler;
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(bookingAdapter);
                        }
                    });
                }
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
}