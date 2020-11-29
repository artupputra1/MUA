package com.example.mua.review;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;
import com.example.mua.booking.Booking;
import com.example.mua.booking.DetailBookingActivity;
import com.example.mua.booking.UploadTransactionActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> dataReview;
    public Context context;

    public ReviewAdapter(Context context, List<Review> dataReview) {
        this.context = context;
        this.dataReview = dataReview;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String status;
        final Review data = dataReview.get(position);
        holder.mName.setText(data.getUser_name());
        holder.mReview.setText(data.getReview());
        holder.mRating.setRating(Float.parseFloat(data.getRating()));
        Picasso.get()
                .load("http://belajarkoding.xyz/mua/upload/review/"+data.image)
                .fit()
                .into(holder.mImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataReview.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mName, mReview;
        RatingBar mRating;
        ImageView mImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvUser);
            mReview = itemView.findViewById(R.id.tvReview);
            mRating = itemView.findViewById(R.id.rating);
            mImage = itemView.findViewById(R.id.ivReview);
        }
    }
}