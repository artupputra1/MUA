package com.example.mua.notification;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;
import com.example.mua.booking.Booking;
import com.example.mua.booking.DetailBookingActivity;
import com.example.mua.booking.UploadTransactionActivity;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<Notification> dataNotification;
    public Context context;

    public NotificationAdapter(Context context, List<Notification> dataNotification) {
        this.context = context;
        this.dataNotification = dataNotification;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_notification, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Notification data = dataNotification.get(position);
        holder.mTitle.setText(data.getTitle());
        holder.mContent.setText(data.getContent());
        holder.mDate.setText(data.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataNotification.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTitle, mContent, mDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tvTitle);
            mContent = itemView.findViewById(R.id.tvContent);
            mDate = itemView.findViewById(R.id.tvDate);
        }
    }
}