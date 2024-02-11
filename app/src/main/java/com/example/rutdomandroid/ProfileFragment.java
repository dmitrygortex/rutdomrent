package com.example.rutdomandroid;

import static android.content.ContentValues.TAG;

import static com.example.rutdomandroid.RegisterFragment.isGroupValid;
import static com.example.rutdomandroid.RegisterFragment.isInstituteValid;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.FragmentProfileBinding;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;


public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;
    String email_first,password_first;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Button exitaccount = binding.exitAccount;
        Button updateaccount = binding.changeAccount;
        db.collection("users").document(user.getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                email_first=document.getString("email");
                                password_first=document.getString("password");

                                binding.editTextText2.setText(document.getString("email"));
                                binding.editTextText5.setText(document.getString("password"));
                                binding.editTextText4.setText(document.getString("fullName"));

                                binding.editTextText3.setText(document.getString("institute"));

                            } else {
                            }
                        } else {
                        }
                    }
                });


        updateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editTextText2.getText().toString();
                String password = binding.editTextText5.getText().toString();
                String insitute = binding.editTextText3.getText().toString();
                String fio = binding.editTextText4.getText().toString();
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
                if (insitute.isEmpty()) {
                    Toast.makeText(getContext(), "Поле Группа должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isInstituteValid(insitute)){
                    Toast.makeText(getContext(), "Введен несуществующий институт", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fio.isEmpty()) {
                    Toast.makeText(getContext(), "Поле ФИО должно быть заполнено", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> data = new HashMap<>();
                data.put("email", email);
                data.put("password", password);
                data.put("isnstitute", insitute);
                data.put("fullname", fio);
                db.collection("users").document(user.getUid())
                        .update(data);
                AuthCredential credential = EmailAuthProvider
                        .getCredential(email_first, password_first);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(password);
                                    user.updateEmail(email);
                                    Toast.makeText(getContext(), "Профиль обновлен.", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getContext(), "Что-то пошло не так.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });

        exitaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Вы уверены, что хотите выйти?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                MainActivity mainActivity = (MainActivity) requireActivity();
                                if (mainActivity != null) {
                                    mainActivity.setButtonNavViewVisibility(View.GONE);
                                }
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
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
        Button deleteАccount = binding.deleteAccount;
        deleteАccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Вы уверены, что хотите удалить аккаунт?")
                        .setCancelable(false)
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RentDatabase rentDatabase=MainActivity.getRentDatabase();
                                Executors.newSingleThreadExecutor().execute(new Runnable() {
                                    @Override    public void run() {
                                        rentDatabase.bookingDao().deleteBooking(user.getUid());
                                    }});
                                db.collection("users").document(auth.getCurrentUser().getUid()).delete();

                                auth.getCurrentUser().delete()
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
                                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
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
