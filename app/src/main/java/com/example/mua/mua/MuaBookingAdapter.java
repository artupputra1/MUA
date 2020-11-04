package com.example.mua.mua;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;

import java.util.List;

public class MuaBookingAdapter extends RecyclerView.Adapter<MuaBookingAdapter.ViewHolder> {

    private List<MuaBooking> dataBooking;
    public Context context;

    public MuaBookingAdapter(Context context, List<MuaBooking> dataBooking) {
        this.context = context;
        this.dataBooking = dataBooking;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mua_booking, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String status;
        final MuaBooking data = dataBooking.get(position);
        holder.mMua.setText(data.getMua_name());
        holder.mTanggal.setText("Tanggal Booking : " + data.getDate() + " / " + data.getTime());
        holder.mStatus.setText(data.getStatus());
        holder.mService.setText(data.getService_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getStatus().equals("Menunggu Konfirmasi")){
                    Intent varIntent = new Intent(context, MuaDetailBookingActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("type", "1");
                    context.startActivity(varIntent);
                }
                else if (data.getStatus().equals("Menunggu Pembayaran")){
                    Intent varIntent = new Intent(context, MuaDetailBookingActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("type", "2");
                    context.startActivity(varIntent);
                }
                else if (data.getStatus().equals("Lunas")){
                    Intent varIntent = new Intent(context, MuaDetailBookingActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("type", "2");
                    context.startActivity(varIntent);
                }
                else if (data.getStatus().equals("Selesai")){
                    Intent varIntent = new Intent(context, MuaDetailBookingActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("type", "3");
                    context.startActivity(varIntent);
                }
                else {

                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataBooking.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMua, mTanggal, mStatus, mService;

        public ViewHolder(View itemView) {
            super(itemView);
            mMua = itemView.findViewById(R.id.tvMua);
            mTanggal = itemView.findViewById(R.id.tvDate);
            mStatus = itemView.findViewById(R.id.tvStatus);
            mService = itemView.findViewById(R.id.tvServices);

        }
    }
}