package com.example.rutdomandroid;

import static androidx.core.content.ContextCompat.startActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.FragmentLoginBinding;
import com.example.rutdomandroid.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth mAuth;
    TextView loginLabel;

    FirebaseFirestore db;

    Button loginButton;

    public static Boolean isInstituteValid(String group){
        List<String> instituteList = Arrays.asList("ИУЦТ ИЭФ ИТТСУ ИПСС ИМТК ЮИ АВТ ВИШ АДХ АГА IUCT".split(" "));
        return instituteList.contains(group);
    }


    public static Boolean isGroupValid(String group){
        List<String> groupList = Arrays.asList("ШАД-111,ШМС-111,ШМС-112,ШТА-171,ШТД-111,ШТС-111,ШЦТ-111,ШЭТ-111,ШАД-211,ШАД-212,ШМС-211,ШТА-271,ШТД-211,ШТС-211,ШЭТ-211,ШАД-311,ШАД-312,ШМС-311,ШТД-311,АВП-111,АГТ-111,АСВ-141,АСВ-142,АСМ-141,АУТ-111,АЭО-141,АВП-211,АМО-211,АСС-241,АУТ-211,АГС-311,АМО-311,АТС-341,АКС-411,АСМ-441,АЭМ-411,АЭО-441,АСМ-541,АЭО-541,ЛМБ-111,ДДА-111,ДДС-141,ДДУ-171,ОМКк-111,ОМКк-112,ОММ-111,ОМН-111,ОМО-111,ОМО-112,ОМО-171,ОМКк-211,ОММ-211,ОМН-211,ОМО-211,ОМО-212,ОМБ-311,ОМКк-311,ОММ-311,ОМН-311,ОМН-312,ОМП-311,ОМТ-311,ОМБ-411,ОМБ-412,ОММу-421,ОМН-411,ОМН-412,ОМП-411,ОММ-521,САП-111,САП-171,СГС-111,СГС-112,СГС-171,СГТ-141,СЖД-141,СЖД-142,СЖД-143,СКУ-181,СКХ-171,СММ-141,СММ-142,СММ-143,СМС-171,СМТ-141,СПС-141,СРП-111,ССД-171,СТП-141,СТП-142,СТП-143,СТП-144,ВЖД-251,САД-211,САП-211,СГС-211,СГС-212,СЖД-241,СЖД-242,СЖД-243,СЗК-211,СЗК-212,СКУ-211,СММ-241,СММ-242,СММ-243,СМТ-241,СРП-211,СТП-241,СТП-242,СТП-243,СТП-244,СТП-245,ВЖД-351,САД-311,САП-311,СГСк-314,СГС-311,СГС-312,СГС-313,СЖД-341,СЖД-342,СЖД-343,СЗК-311,СКУ-311,СММ-341,СММ-342,СММ-343,СМН-311,СМТ-341,СТП-341,СТП-342,СТП-343,СТП-344,СТП-345,СЭН-311,ВЖД-451,САД-411,САП-411,СГСк-414,СГС-411,СГС-412,СГС-413,СЖД-441,СЖД-442,СЖД-443,СЗК-411,СКУ-411,СММ-441,СММ-442,СММ-443,СМН-411,СМТ-441,СТП-441,СТП-442,СТП-443,СТП-444,СЭН-411,ВЖД-551,ВЖД-651,ТБЖ-111,ТИА-171,ТКИ-141,ТКИ-142,ТМР-111,ТМС-111,ТНД-141,ТНК-171,ТНС-171,ТПВг-141,ТПВп-141,ТПЛ-141,ТПЛ-142,ТПР-141,ТПТ-141,ТПЭ-141,ТПЭ-142,ТПЭ-171,ТСА-141,ТСА-142,ТСА-143,ТСМ-171,ТСТ-141,ТСТ-142,ТСЭ-141,ТСЭ-142,ТСЭ-143,ТТБ-171,ТТП-111,ТТП-112,ТТП-171,ТУП-111,ТУП-171,ТУУ-111,ТУУ-171,ТЭМ-171,ТЭМ-172,ВПЛ-251,ВУП-221,ТБЖ-211,ТИА-271,ТИУ-211,ТКИ-241,ТМР-211,ТМС-211,ТНД-241,ТНК-271,ТНС-271,ТПВг-241,ТПВп-241,ТПЛ-241,ТПЛ-242,ТПР-241,ТПТ-241,ТПЭ-241,ТПЭ-242,ТРС-271,ТСА-241,ТСА-242,ТСА-243,ТСМ-271,ТСТ-241,ТСТ-242,ТСЭ-241,ТСЭ-242,ТСЭ-243,ТТБ-271,ТТП-211,ТТП-212,ТУП-211,ТУУ-211,ТЦТ-211,ТЭМ-271,ТЭМ-272,ТЭТ-221,ВПЛ-351,ВУП-321,ВУП-323,ТБЖ-311,ТИУ-311,ТКИ-341,ТКИ-342,ТМС-311,ТНД-341,ТПВг-341,ТПВп-341,ТПЛ-341,ТПЛ-342,ТПР-341,ТПТ-341,ТПЭ-341,ТПЭ-342,ТСА-341,ТСА-342,ТСА-343,ТСС-311,ТСТ-341,ТСТ-342,ТСЭ-341,ТСЭ-342,ТСЭ-343,ТТП-311,ТУП-311,ТУСу-312,ТУСу-313,ТУУ-311,ТУЭк-311,ТУЭк-312,ТЦТ-311,ВПЛ-451,ВТБ-421,ВТП-421,ВУП-421,ВУЦ-421,ТБЖ-411,ТИУ-411,ТИУ-412,ТКИ-441,ТКИ-442,ТМР-411,ТМС-411,ТНД-441,ТПВг-441,ТПВп-441,ТПЛ-441,ТПР-441,ТПТ-441,ТПЭ-441,ТСА-441,ТСА-442,ТСС-411,ТСТ-441,ТСЭ-441,ТСЭ-442,ТТП-411,ТУП-411,ТУСу-412,ТУСу-413,ТУУ-411,ТУЭк-411,ТУЭк-412,ВПЛ-551,ВТП-521,ВУП-521,ТКИ-541,ТПСу-651,УВВ-111,УВВ-171,УВП-111,УВП-112,УВП-171,УЗС-111,УИБ-111,УИБ-112,УИС-111,УМЛ-111,УМЛ-112,УМЛ-171,УМН-111,УНК-171,УНК-172,УНТ-171,УПМ-111,УПМ-112,УТА-111,УТБ-171,УТИ-111,УТН-111,УТЦ-111,УТЦ-112,УТЦ-171,УТЦ-172,УЭБ-141,УЭГ-141,УЭИ-141,УЭЛ-141,УЭЛ-142,УЭЛ-143,УЭМ-141,УЭМ-142,УЭМ-143,УЭМ-144,УЭЦ-141,УВВ-211,УВВ-271,УВП-211,УВП-271,УВП-272,УВП-212,УЗС-211,УЗС-212,УИБ-211,УИБ-271,УИС-211,УМЛв-221,УМЛ-211,УМЛ-212,УМН-211,УМЦ-211,УПМ-211,УПМ-212,УТА-211,УТБ-271,УТН-211,УТЦ-211,УТЦ-212,УТЦ-213,УЭБ-241,УЭГ-241,УЭИ-241,УЭЛ-241,УЭЛ-242,УЭМв-251,УЭМ-241,УЭМ-242,УЭМ-243,УЭМ-244,УЭЦ-241,УВА-311,УВВ-311,УВПв-321,УВП-311,УВП-312,УВП-313,УЗС-311,УИБ-311,УИБ-312,УИС-311,УИС-312,УМЛв-321,УМЛ-311,УМЛ-312,УМЛ-313,УМН-311,УМН-312,УПМ-311,УТА-311,УТЕ-311,УТН-311,УТН-312,УТЦ-311,УТЦ-312,УЭБ-341,УЭГ-341,УЭИ-341,УЭЛ-341,УЭЛ-342,УЭМв-351,УЭМ-341,УЭМ-342,УЭМ-343,УЭМ-344,УЭЦ-341,УВА-411,УВВ-411,УВПв-421,УВП-411,УЗС-411,УИБ-411,УИС-411,УИС-412,УМЛв-421,УМН-411,УМН-412,УПМ-411,УТА-411,УТЕ-411,УТН-411,УТН-412,УТЦ-411,УТЦ-412,УЭГ-441,УЭИ-441,УЭЛ-441,УЭЛ-442,УЭМв-451,УЭМ-441,УЭМ-442,УЭМ-443,УЭМ-444,УЭП-441,УЭЦ-441,УЭМв-551,ЭБП-111,ЭБР-111,ЭБР-112,ЭБЦ-111,ЭГП-111,ЭГС-111,ЭГУ-111,ЭГФ-111,ЭЛМ-111,ЭММ-171,ЭМС-171,ЭМУ-171,ЭМЭ-171,ЭПИ-111,ЭПИ-112,ЭСБ-141,ЭУМ-111,ЭУЦ-111,ЭЭБ-111,ЭЭЛ-111,ЭЭП-111,ЭЭС-111,ЭЭТ-111,ЭЭУ-111,ЭЭФ-111,ЭБР-211,ЭБР-212,ЭБС-211,ЭБЦ-211,ЭВБ-221,ЭГП-211,ЭГС-211,ЭГС-212,ЭГУ-211,ЭЛМ-211,ЭМД-281,ЭПИ-211,ЭПИ-212,ЭСБ-241,ЭСБ-242,ЭТМ-211,ЭУМ-211,ЭУЦ-211,ЭЭБу-221,ЭЭБ-211,ЭЭЛ-211,ЭЭП-211,ЭЭС-211,ЭЭТ-211,ЭЭУ-211,ЭЭФу-221,ЭЭФ-211,ЭББд-311,ЭББ-311,ЭББ-312,ЭББ-312,ЭБР-311,ЭБР-312,ЭБР-313,ЭБТ-311,ЭБЦ-311,ЭВБ-321,ЭГД-311,ЭГД-331,ЭГП-311,ЭГС-311,ЭГУ-311,ЭЛК-311,ЭЛП-311,ЭПБ-311,ЭПБ-312,ЭПЭ-311,ЭСБ-341,ЭСБ-342,ЭТК-311,ЭТМ-311,ЭУМд-321,ЭУМ-311,ЭУП-311,ЭУС-311,ЭУЦ-311,ЭУЧ-311,ЭЭБ-311,ЭЭЛ-311,ЭЭМ-311,ЭЭН-311,ЭЭП-311,ЭЭР-311,ЭЭС-311,ЭЭТ-311,ЭЭУ-311,ЭЭФу-321,ЭЭФ-311,ЭББд-411,ЭББ-411,ЭБТ-411,ЭБЦ-411,ЭВБ-421,ЭГД-411,ЭГД-431,ЭГС-411,ЭГС-412,ЭГУ-411,ЭЛК-411,ЭЛК-412,ЭЛП-411,ЭПБ-411,ЭПС-411,ЭПЭ-411,ЭСБ-441,ЭСБ-442,ЭСБ-443,ЭТМ-411,ЭУГ-411,ЭУМд-421,ЭУП-411,ЭУС-411,ЭУЦ-411,ЭУЧ-411,ЭЭБ-411,ЭЭЛ-411,ЭЭМ-411,ЭЭПд-411,ЭЭП-411,ЭЭР-411,ЭЭС-411,ЭЭТ-411,ЭЭУ-411,ЭЭФ-411,ЭСБ-541,ЭСБ-542,ЭСБ-543,ЮПБ-141,ЮПБ-142,ЮПБ-143,ЮСИ-141,ЮСИ-142,ЮТМ-141,ЮТМ-142,ЮТМ-143,ЮТТ-141,ЮЮГ-111,ЮЮГ-112,ЮЮГ-113,ЮЮМ-111,ЮЮУ-111,ЮЮЦ-111,ЮЮЦ-112,ЮМП-271,ЮМТ-271,ЮПБ-241,ЮПБ-242,ЮСИ-241,ЮТМ-241,ЮТМ-242,ЮТМ-243,ЮТТ-241,ЮЮГ-211,ЮЮГ-212,ЮЮГ-213,ЮЮУ-211,ЮЮЦ-211,ЮГП-311,ЮСИ-341,ЮТИ-341,ЮТЛ-341,ЮТМ-341,ЮТМ-342,ЮЮГ-311,ЮЮГ-312,ЮЮГ-313,ЮЮУ-311,ЮЮЦ-311,ЮПБу-441,ЮПБ-443,ЮПДу-461,ЮСИ-441,ЮСИ-442,ЮТЛ-441,ЮТМ-441,ЮТМ-442,ЮЮГ-411,ЮЮГ-412,ЮЮУ-411,ЮЮЦ-411,ЮПБ-541,ЮПБ-542,ЮПБ-561,ЮПБ-562,ЮПД-541,ЮПД-561,ЮСИ-541,ЮПБ-651,ЮПБ-661,ЮПБ-662".split(","));
        return groupList.contains(group);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        loginLabel=binding.loginLabel;
        loginButton = binding.registerButton;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        loginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new LoginFragment(), null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });

        //goToSetting();

        //NotificationHelper.createChannel(this.getContext());
        //NotificationHelper.sendNotification(this.getContext(), "AFTER 2 DAYS I DO IT!!!!!", "LESSGOO");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailText.getText().toString();
                String password = binding.passwordText.getText().toString();
                String name = binding.FIOText.getText().toString();
                String institute = binding.institute.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(getContext(), "Поле email  должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 8) {
                    Toast.makeText(getContext(), "Минимальная длина пароля 8 символов", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(getContext(), "Поле Пароль должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (institute.isEmpty()) {
                    Toast.makeText(getContext(), "Поле институт должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isInstituteValid(institute)){
                    Toast.makeText(getContext(), "Введен несуществующий институт", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Поле ФИО должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid = user.getUid();

                                    Map<String, Object> userMap = new HashMap<>();
                                    userMap.put("email", email);
                                    userMap.put("password", password);
                                    userMap.put("fullName", name);
                                    userMap.put("institute", institute);

                                    userMap.put("uid", uid);

                                    db.collection("users").document(uid)
                                            .set(userMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(inflater.getContext(), "Аккаунт создан.",
                                                            Toast.LENGTH_SHORT).show();
                                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                                    fragmentManager.beginTransaction()
                                                            .replace(R.id.frame_layout, new InfoFragment(), null)
                                                            .setReorderingAllowed(true)
                                                            .addToBackStack("name")
                                                            .commit();
                                                }

                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(getContext(), "Попробуйте еще раз",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(getContext(), "Попробуйте еще раз.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

        });
        return view;
    }
}