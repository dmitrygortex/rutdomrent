package com.example.rutdomandroid;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.rutdomandroid.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new LoginFragment());
        binding.buttonNavView.setVisibility(View.GONE);



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
}





