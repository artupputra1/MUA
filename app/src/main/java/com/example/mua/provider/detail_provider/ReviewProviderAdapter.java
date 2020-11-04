package com.example.mua.provider.detail_provider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.OrderActivity;
import com.example.mua.R;
import com.example.mua.review.ReviewActivity;

import java.util.List;

public class ReviewProviderAdapter extends RecyclerView.Adapter<ReviewProviderAdapter.ViewHolder> {

    private List<ReviewProvider> reviewProviders;
    public Context context;

    public ReviewProviderAdapter(Context context, List<ReviewProvider> reviewProviders) {
        this.context = context;
        this.reviewProviders = reviewProviders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_review_provider, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ReviewProvider data = reviewProviders.get(position);
        holder.mService.setText(data.getName_service());
        holder.mNameUser.setText(data.getName_user());
        holder.mReview.setText(data.getReview());
        holder.mSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra("service_id", data.getService_id());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewProviders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mService, mNameUser, mReview;
        Button mSee;

        public ViewHolder(View itemView) {
            super(itemView);
            mService = itemView.findViewById(R.id.tvServices);
            mNameUser = itemView.findViewById(R.id.tvNameUser);
            mReview = itemView.findViewById(R.id.tvReview);
            mSee = itemView.findViewById(R.id.btSeeAll);
        }
    }
}