package com.example.rutdomandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rutdomandroid.R;
import com.example.rutdomandroid.model.TimeSlot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    private boolean isClickable;
    private List<TimeSlot> timeSlots;
    private LayoutInflater layoutInflater;
    FirebaseFirestore db;
    private List<String> booked_values;

    public TimeAdapter(List<TimeSlot> timeSlots, Context context, List<String> booked_time) {
        this.timeSlots = timeSlots;
        this.layoutInflater = LayoutInflater.from(context);
        this.isClickable = true;
        this.booked_values = booked_time;
    }

    public boolean isClickable() {
        return isClickable;
    }

    public void setClickable() {
        this.isClickable = !isClickable;
    }


    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    @Override
    public TimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeViewHolder holder, int position) {
        TimeSlot value = timeSlots.get(position);
        holder.timeText.setText(value.getTime());
        if (booked_values.contains(value.getTime())) {
            holder.layout.setBackground(
                    ContextCompat.getDrawable(layoutInflater.getContext(), R.drawable.button_gray_shape));
            holder.timeText.setTextColor(ContextCompat.getColor(layoutInflater.getContext(), R.color.grey));
            value.setEnable();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value.isEnable()) {
                    if (value.isSelected()){

                        holder.itemView.setBackground(
                                ContextCompat.getDrawable(v.getContext(), R.drawable.button_green_shape));
                        holder.timeText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.green));
                        value.setSelected();
                        setClickable();
                    } else if (!value.isSelected() && isClickable) {
                        holder.itemView.setBackground(
                                ContextCompat.getDrawable(v.getContext(), R.drawable.button_blue_shape));
                        holder.timeText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.blue));
                        value.setSelected();
                        setClickable();
                    }
                }

            }


        });

    }


    @Override
    public int getItemCount() {
        return timeSlots.size();
    }


    public class TimeViewHolder extends RecyclerView.ViewHolder {
        public TextView timeText;
        public LinearLayout layout;


        public TimeViewHolder(View itemView) {
            super(itemView);
            timeText = itemView.findViewById(R.id.time_text);
            layout = itemView.findViewById(R.id.background);
        }
    }

}
