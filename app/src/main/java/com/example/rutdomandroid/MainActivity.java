package com.example.rutdomandroid;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static RentDatabase rentDatabase;
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new InfoFragment());
        binding.buttonNavView.setVisibility(View.GONE);
        rentDatabase = Room.databaseBuilder(getApplicationContext(), RentDatabase.class, "bookings")
                .fallbackToDestructiveMigration()
                .build();



        binding.buttonNavView.setOnItemSelectedListener(item -> {
            if  (item.getItemId()==R.id.info_item) {
                 replaceFragment(new InfoFragment());
            }
            if  (item.getItemId()==R.id.profile_item) {
                replaceFragment(new ProfileFragment());
            }
            if  (item.getItemId()==R.id.rent_item) {
                replaceFragment(new RentFragment());
            }




            return true;




        });

    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout,fragment, null)
                .setReorderingAllowed(false)
                .addToBackStack("name") // Name can be null
                .commit();
    }
    public void setButtonNavViewVisibility(int visibility) {
        binding.buttonNavView.setVisibility(visibility);
    }
    public static RentDatabase getRentDatabase() {
        return rentDatabase;
    }
}





