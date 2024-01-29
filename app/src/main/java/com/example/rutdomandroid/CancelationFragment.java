package com.example.rutdomandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.rutdomandroid.databinding.FragmentCancelationBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.List;

public class CancelationFragment extends Fragment {
    FragmentCancelationBinding binding;
    Button cancel_button;

    public CancelationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        binding = FragmentCancelationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        cancel_button = binding.button2;
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference userRef = db.collection("users").document(auth.getCurrentUser().getUid());
                userRef.get().addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Object> bookings = (List<Object>) documentSnapshot.get("bookings");
                        if (bookings != null && !bookings.isEmpty()) {
                            bookings.remove(bookings.size()-1);
                            userRef.update("bookings", bookings).addOnSuccessListener(aVoid -> {
                                db.collection("bookings").whereEqualTo("uid", auth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            db.collection("bookings").document(document.getId()).delete().addOnSuccessListener(mew -> {
                                                Toast.makeText(getContext(), "Бронь успешно удалена", Toast.LENGTH_SHORT).show();
                                            }).addOnFailureListener(e -> {
                                                Log.e("RemoveBooking", "Error removing booking from bookings collection", e);
                                            });
                                        }
                                    } else {
                                        Log.e("RemoveBooking", "Error getting documents", task.getException());
                                    }
                                });
                            }).addOnFailureListener(e -> {
                                Log.e("RemoveFirstBooking", "Error removing first booking", e);
                            });
                        } else {
                            Log.d("RemoveFirstBooking", "No bookings to remove");
                        }
                    }
                });
            }
        });
        return view;
    }
}