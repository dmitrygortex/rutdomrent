package com.example.rutdomandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.R;
import com.example.rutdomandroid.model.RentInit;

import java.util.List;

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
        holder.yearOfRentInit.setText(rentInits.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return rentInits.size();
    }

    public static final class RentInitViewHolder extends RecyclerView.ViewHolder {

        TextView yearOfRentInit;
        TextView dateOfRentInit;
        TextView timeOfRentInit;
        TextView typeOfRoom
;
        TextView reasonOfRentInit;

        public RentInitViewHolder(@NonNull View itemView) {
            super(itemView);

            yearOfRentInit = itemView.findViewById(R.id.yearOfRentInit);
            dateOfRentInit = itemView.findViewById(R.id.dateOfRentInit);
            timeOfRentInit = itemView.findViewById(R.id.timeOfRentInit);
            typeOfRoom = itemView.findViewById(R.id.typeOfRoom);
            reasonOfRentInit = itemView.findViewById(R.id.themeOfRent);
        }
    }
}
