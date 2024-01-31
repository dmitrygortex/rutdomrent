package com.example.rutdomandroid;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;


public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    FirebaseAuth auth;
    TextView registerLabel;
    Button loginButton;
    EditText email_text,password_text;
    String email,password;

    private Boolean isValidEmail(String email){
        return (email.contains("@") && email.contains("."));
    }
    private Boolean isValidPassword(String password){
        return (password.length() >= 8);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        auth = FirebaseAuth.getInstance();
        registerLabel=(TextView) binding.createAccoutLabel;
        loginButton=  binding.loginButton;
        email_text=binding.emailSignIn;
        password_text=binding.passwordSignIn;
        registerLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager =requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new RegisterFragment(), null)
                        .setReorderingAllowed(false)
                        .addToBackStack("name")
                        .commit();
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailSignIn.getText().toString();
                String password = binding.passwordSignIn.getText().toString();


                if (!isValidEmail(email)) {
                    Toast.makeText(getContext(), "Невозможная почта. Пример: example@domen.com", Toast.LENGTH_SHORT).show();

                    return;
                }
                if (!isValidPassword(password)) {
                    Toast.makeText(getContext(), "Минимальная длина пароля 8 символов", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (password.isEmpty()) {
//                    Toast.makeText(getContext(), "Поле Пароль должно быть заполнено", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (auth.getCurrentUser() != null) {
                                    Toast.makeText(getContext(), "Вы вошли в аккаунт.",
                                            Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction()
                                            .replace(R.id.frame_layout, new InfoFragment(), null)
                                            .setReorderingAllowed(true)
                                            .addToBackStack("name")
                                            .commit();
                                }
                                else Toast.makeText(getContext(), "Аккаунт не существует.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Что-то пошло не так.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        return view;
    }


}