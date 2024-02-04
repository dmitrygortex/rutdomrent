package com.example.rutdomandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.MainActivity;
import com.example.rutdomandroid.R;
import com.example.rutdomandroid.roomDatabase.RentDatabase;
import com.example.rutdomandroid.roomDatabase.RentEntity;
import com.example.rutdomandroid.roomDatabase.RentDAO;
import com.example.rutdomandroid.model.RentInit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class RentInitAdapter extends RecyclerView.Adapter<RentInitAdapter.RentInitViewHolder> {

    Context context;
    List<RentInit> rentInits;

    public RentInitAdapter(Context context, List<RentInit> rentInits) {
        this.context = context;
        this.rentInits = rentInits;
    }

    @NonNull
    @Override
    public RentInitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rentInitItems = LayoutInflater.from(context).inflate(R.layout.card_reservation_unit, parent, false);
        return new RentInitViewHolder(rentInitItems);
    }

    @Override
    public void onBindViewHolder(@NonNull RentInitViewHolder holder, int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        RentInit rent = rentInits.get(position);
        holder.yearOfRentInit.setText(rent.getYear());
        holder.timeOfRentInit.setText(rent.getTime());
        holder.reasonOfRentInit.setText(rent.getReasonOfRent());
        holder.typeOfRoom.setText(rent.getRoom());
        String[] date=rent.getDate().split("\\.");
        String month = date[1];
        switch (date[1]) {
            case "01":
                month = "Января";
                break;
            case "02":
                month = "Февраля";
                break;
            case "03":
                month = "Марта";
                break;
            case "04":
                month = "Апреля";
                break;
            case "05":
                month = "Мая";
                break;
            case "06":
                month = "Июня";
                break;
            case "07":
                month = "Июля";
                break;
            case "08":
                month = "Авгуса";
                break;

            case "09":
                month = "Сентября";
                break;

            case "10":
                month = "Октября";
                break;

            case "11":
                month = "Ноября";
                break;

            case "12":
                month = "Декабря";
                break;
        }
        holder.dateOfRentInit.setText(String.format("%s %s",date[0],month));

        holder.cancelRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                RentInit rent = rentInits.get(position);
                rentInits.remove(position);
                String date = (String) rent.getDate();
                String time = (String) rent.getTime();
                String purpose=rent.getReasonOfRent();
                String room = (String) rent.getRoom();
                db.collection("users").document(user.getUid()).update("bookings", rentInits);
                Map<String, Object> data = new HashMap<>();
                data.put(time,FieldValue.delete());
                Map<String, Object> updates = new HashMap<>();
                updates.put(rent.getTime(), FieldValue.delete());

                db.collection(room).document(date).set(updates, SetOptions.merge());
              cancelRent(date,room,time,user.getUid());
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, rentInits.size());
            }
        });
    }
    public void cancelRent(String date, String room, String time, String uid){
        RentDatabase rentDatabase = MainActivity.getRentDatabase();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                RentDAO rentDAO = rentDatabase.bookingDao();

                RentEntity rentEntity= rentDAO.getBooking(date,room,time,uid);
                if (rentEntity!=null) rentDAO.deleteRent(rentEntity);
            }
        });

    }


    @Override
    public int getItemCount() {
        return rentInits.size();
    }

    public static final class RentInitViewHolder extends RecyclerView.ViewHolder {

        TextView yearOfRentInit;
        TextView dateOfRentInit;
        TextView timeOfRentInit;
        TextView typeOfRoom;
        TextView reasonOfRentInit;
        Button cancelRent;

        public RentInitViewHolder(@NonNull View itemView) {
            super(itemView);

            yearOfRentInit = itemView.findViewById(R.id.yearOfRentInit);
            dateOfRentInit = itemView.findViewById(R.id.dateOfRentInit);
            timeOfRentInit = itemView.findViewById(R.id.timeOfRentInit);
            typeOfRoom = itemView.findViewById(R.id.typeOfRoom);
            reasonOfRentInit = itemView.findViewById(R.id.themeOfRent);
            cancelRent = itemView.findViewById(R.id.cancelRent);
        }
    }
}
