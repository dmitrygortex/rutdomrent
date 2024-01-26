package com.example.rutdomandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    private static final String VIEW_TYPE_1 = "GREEN";
    private static final String VIEW_TYPE_2 = "BLUE";

    private List<TimeSlot> timeSlots;

    private LayoutInflater layoutInflater;

    public TimeAdapter(List<TimeSlot> timeSlots, Context context) {
        this.timeSlots = timeSlots;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public TimeSlot getItem(int position) {
        return timeSlots.get(position);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (value.isSelected()) {
                    holder.itemView.setBackground(
                            ContextCompat.getDrawable(v.getContext(), R.drawable.button_green_shape));
                    holder.timeText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.green));
                    value.setSelected(false);
                } else {
                    holder.itemView.setBackground(
                            ContextCompat.getDrawable(v.getContext(), R.drawable.button_blue_shape));
                    holder.timeText.setTextColor(ContextCompat.getColor(v.getContext(), R.color.blue));
                    value.setSelected(true);


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
