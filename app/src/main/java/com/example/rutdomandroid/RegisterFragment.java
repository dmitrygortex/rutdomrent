package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rutdomandroid.databinding.FragmentLoginBinding;
import com.example.rutdomandroid.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    TextView loginLabel;
    EditText editTextLogin;
    EditText editTextFullName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextInstitute;
    EditText editTextPhoneNumber;
    Button buttonRegistration;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        loginLabel = binding.loginLabel;
        editTextLogin = binding.editTextLogin;
        editTextFullName = binding.editTextFullName;
        editTextInstitute = binding.editTextInstitute;
        editTextPassword = binding.editTextPassword;
        editTextPhoneNumber = binding.editTextPhoneNumber;
        buttonRegistration = binding.button;

        loginLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager =requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, new LoginFragment(), null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name") // Name can be null
                        .commit();
            }
        });
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}