package com.example.mua.booking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;
import com.example.mua.review.AddReviewActivity;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    private List<Booking> dataBooking;
    public Context context;

    public BookingAdapter(Context context, List<Booking> dataBooking) {
        this.context = context;
        this.dataBooking = dataBooking;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_booking, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String status;
        final Booking data = dataBooking.get(position);
        holder.mMua.setText(data.getMua_name());
        holder.mTanggal.setText("Tanggal Booking : " + data.getDate() + " / " + data.getTime());
        holder.mStatus.setText(data.getStatus());
        holder.mService.setText(data.getService_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getStatus().equals("Menunggu Pembayaran")){
                    Intent varIntent = new Intent(context, UploadTransactionActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("total", data.getTotal_price());
                    context.startActivity(varIntent);
                }
                else if (data.getStatus().equals("Selesai")){
                    Intent varIntent = new Intent(context, AddReviewActivity.class);
                    varIntent.putExtra("service_id", data.getService_id());
                    varIntent.putExtra("order_id", data.getId());
                    context.startActivity(varIntent);
                }
                else if (data.getStatus().equals("Menunggu Konfirmasi")) {
                    Intent varIntent = new Intent(context, DetailBookingActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("type", "1");
                    context.startActivity(varIntent);
                }
                else if (data.getStatus().equals("Lunas")) {
                    Intent varIntent = new Intent(context, DetailBookingActivity.class);
                    varIntent.putExtra("booking_id", data.getId());
                    varIntent.putExtra("type", "2");
                    context.startActivity(varIntent);
                }
//                else if (data.getStatus().equals("Sedang Diproses")) {
//                    Intent varIntent = new Intent(context, DetailBookingActivity.class);
//                    varIntent.putExtra("booking_id", data.getId());
//                    varIntent.putExtra("type", "2");
//                    context.startActivity(varIntent);
//                }

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