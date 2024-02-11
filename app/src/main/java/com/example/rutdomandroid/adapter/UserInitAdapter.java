package com.example.rutdomandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.rutdomandroid.R;
import com.example.rutdomandroid.model.UserInit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserInitAdapter extends RecyclerView.Adapter<UserInitAdapter.UserInitViewHolder> {

    Context context;
    List<UserInit> users;

    public UserInitAdapter(Context context, List<UserInit> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserInitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // hourly triage to identify violators
        Collections.sort(users, new Comparator<UserInit>() {
            @Override
            public int compare(UserInit o1, UserInit o2) {
                return Integer.compare(Integer.parseInt(o1.getRentedHours()),
                        Integer.parseInt(o2.getRentedHours()));
            }
        });

        View userInits = LayoutInflater.from(context).inflate(R.layout.card_user_unit, parent, false);
        return new UserInitViewHolder(userInits);
    }

    @Override
    public void onBindViewHolder(@NonNull UserInitViewHolder holder, int position) {


        holder.userName.setText(users.get(position).getUserName());
        holder.timeOfRentedHours.setText(users.get(position).getRentedHours());
        holder.group.setText(users.get(position).getGroup());
        holder.email.setText(users.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static final class UserInitViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        TextView timeOfRentedHours;
        TextView group;
        TextView email;

        public UserInitViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.text_user_name);
            timeOfRentedHours = itemView.findViewById(R.id.text_time_of_rented_hours);
            group = itemView.findViewById(R.id.text_group);
            email = itemView.findViewById(R.id.text_email);

        }
    }
}
