package com.example.rutdomandroid;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.rutdomandroid.databinding.ActivityMainBinding;
import com.example.rutdomandroid.databinding.FragmentLoginBinding;
import com.example.rutdomandroid.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button exitaccount = binding.exitAccount;
        exitaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Вы уверены, что хотите выйти?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                auth.signOut();
                                MainActivity mainActivity = (MainActivity) requireActivity();
                                if (mainActivity != null) {
                                    mainActivity.setButtonNavViewVisibility(View.GONE);
                                }
                                FragmentManager fragmentManager =requireActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout, new LoginFragment(), null)
                                        .setReorderingAllowed(false)
                                        .addToBackStack("name")
                                        .commit();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Выход");
                alert.show();
            }
        });
        Button deleteaccount = binding.deleteAccount;
        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Вы уверены, что хотите удалить аккаунт?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User account deleted.");
                                                }
                                            }
                                        });
                                MainActivity mainActivity = (MainActivity) requireActivity();
                                if (mainActivity != null) {
                                    mainActivity.setButtonNavViewVisibility(View.GONE);
                                }
                                FragmentManager fragmentManager =requireActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame_layout, new LoginFragment(), null)
                                        .setReorderingAllowed(false)
                                        .addToBackStack("name")
                                        .commit();
                            }
                        })
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Выход");
                alert.show();

            }
        });
        return view;
    }
}
