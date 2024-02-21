package com.example.rutdomandroid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;

import com.example.rutdomandroid.model.TimeSlot;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.databinding.ActivityMainBinding;
import com.example.rutdomandroid.roomDatabase.UserDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static RentDatabase rentDatabase;
    public static String email, fio, institute;
    FirebaseFirestore db;

    private static final String ADMIN_UID = "SSky4OICqFOaNUhNs4pfgqxBVaA3";
    private static UserDatabase userDatabase;
    FirebaseAuth auth;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String languageToLoad = "ru";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.fragment_rent);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.buttonNavView.setVisibility(View.GONE);
        rentDatabase = Room.databaseBuilder(getApplicationContext(), RentDatabase.class, "bookings")
                .fallbackToDestructiveMigration()
                .build();
        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "users")
                .fallbackToDestructiveMigration()
                .build();

        if (auth.getCurrentUser() == null) {
            replaceFragment(new LoginFragment());
        } else {
            replaceFragment(new InfoFragment());
            getCredentials();

        }


        binding.buttonNavView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.info_item) {
                replaceFragment(new InfoFragment());
            }
            if (item.getItemId() == R.id.profile_item) {
                replaceFragment(new ProfileFragment());
            }
            if (item.getItemId() == R.id.rent_item) {
                if (auth.getCurrentUser().getUid().equals(ADMIN_UID)) {
                    replaceFragment(new AdminPanelFragment());
                } else replaceFragment(new RentFragment());
            }


            return true;


        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, fragment, null)
                .setReorderingAllowed(false)
                .addToBackStack(null)
                .commit();
    }

    public void setButtonNavViewVisibility(int visibility) {
        binding.buttonNavView.setVisibility(visibility);
    }

    public static RentDatabase getRentDatabase() {
        return rentDatabase;
    }

    public static UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public void getCredentials() {
        db.collection("users").document(auth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                institute = document.getString("institute");
                                fio = document.getString("fullName");
                                email = document.getString("email");
                            }
                        }
                    }
                });
    }
}






