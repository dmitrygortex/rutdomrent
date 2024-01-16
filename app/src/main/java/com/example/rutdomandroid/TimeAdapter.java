package com.example.rutdomandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    private List<String> timeSlots;
    private LayoutInflater layoutInflater;

    public TimeAdapter(List<String> timeSlots, Context context) {
        this.timeSlots = timeSlots;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TimeViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.list_time, parent, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TimeViewHolder holder, int position) {
        String string=timeSlots.get(position);
        holder.timeText.setText(string);

    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public class TimeViewHolder extends RecyclerView.ViewHolder {
        public TextView timeText;
        public TimeViewHolder( View itemView) {
            super(itemView);
            timeText=itemView.findViewById(R.id.time_text);
        }
    }
}
