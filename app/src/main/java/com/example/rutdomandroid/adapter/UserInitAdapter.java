
package com.example.rutdomandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public UserInitViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        // hourly triage to identify violators
        Collections.sort(users, new Comparator<UserInit>() {
            @Override
            public int compare(UserInit o1, UserInit o2) {
                return Integer.compare(Integer.parseInt(o1.getTime().split("\\.")[0]),
                        Integer.parseInt(o2.getTime().split("\\.")[0]));
            }
        });

        View userInits = LayoutInflater.from(context).inflate(R.layout.card_user_unit, parent, false);
        return new UserInitViewHolder(userInits);
    }

    @Override
    public void onBindViewHolder( UserInitViewHolder holder, int position) {


        holder.fio.setText(users.get(position).getFio());
        holder.institute.setText(users.get(position).getInstitute());
        holder.time.setText(users.get(position).getTime());
        holder.text_email.setText(users.get(position).getEmail());
        holder.reason.setText(users.get(position).getPurpose());
        holder.room.setText(users.get(position).getRoom());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static final class UserInitViewHolder extends RecyclerView.ViewHolder {

        TextView fio;
        TextView institute;
        TextView reason;
        TextView room;
        TextView time;
        Button text_email;






        public UserInitViewHolder( View itemView) {
            super(itemView);

            fio = itemView.findViewById(R.id.userFio);
            institute = itemView.findViewById(R.id.userInstitute);
            reason = itemView.findViewById(R.id.userReason);
            text_email = itemView.findViewById(R.id.cancelRentUser);
            time = itemView.findViewById(R.id.userTime);
            room = itemView.findViewById(R.id.userPlace);


        }
    }
}

