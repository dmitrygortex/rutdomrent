package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    TextView loginLabel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        loginLabel = binding.button;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        loginLabel.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getContext(), "Поле Институт должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.isEmpty()) {
                    Toast.makeText(getContext(), "Поле ФИО должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }z
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
                                    userMap.put("ФИО", name);
                                    userMap.put("Институт", institute);

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
                                    // If sign in fails, display a message to the user.
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