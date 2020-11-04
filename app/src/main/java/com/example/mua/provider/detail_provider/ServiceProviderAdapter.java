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
import com.example.mua.provider.DetailProviderActivity;
import com.example.mua.provider.Provider;
import com.example.mua.provider.ProviderActivity;
import com.example.mua.review.ReviewActivity;

import java.util.List;

public class ServiceProviderAdapter extends RecyclerView.Adapter<ServiceProviderAdapter.ViewHolder> {

    private List<ServiceProvider> serviceProviders;
    public Context context;

    public ServiceProviderAdapter(Context context, List<ServiceProvider> serviceProviders) {
        this.context = context;
        this.serviceProviders = serviceProviders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_service_provider, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ServiceProvider data = serviceProviders.get(position);
        holder.mService.setText(data.getService());
        holder.mPrice.setText("Harga : " + data.getPrice());
        holder.mInformation.setText(data.getInformation());
        holder.mDuration.setText(data.getDuration());
        holder.mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderActivity.class);
                intent.putExtra("service_id", data.getId());
                context.startActivity(intent);
            }
        });
        holder.mService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReviewActivity.class);
                intent.putExtra("service_id", data.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceProviders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mService, mPrice, mInformation, mDuration;
        Button mOrder;

        public ViewHolder(View itemView) {
            super(itemView);
            mService = itemView.findViewById(R.id.tvServices);
            mPrice = itemView.findViewById(R.id.tvPrice);
            mInformation = itemView.findViewById(R.id.tvInformation);
            mOrder = itemView.findViewById(R.id.btBook);
            mDuration = itemView.findViewById(R.id.tvDuration);
        }
    }
}