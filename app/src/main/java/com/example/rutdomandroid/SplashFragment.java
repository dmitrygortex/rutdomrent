package com.example.rutdomandroid;

import static java.lang.System.in;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class SplashFragment extends AppCompatActivity {
    Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_display);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashFragment.this, MainActivity.class);
                startActivity(intent);

            }
        }, 3000);

    }
}
