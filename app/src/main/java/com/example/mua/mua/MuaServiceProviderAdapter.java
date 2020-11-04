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

public class MuaServiceProviderAdapter extends RecyclerView.Adapter<MuaServiceProviderAdapter.ViewHolder> {

    private List<MuaServiceProvider> serviceProviders;
    public Context context;

    public MuaServiceProviderAdapter(Context context, List<MuaServiceProvider> serviceProviders) {
        this.context = context;
        this.serviceProviders = serviceProviders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mua_service_provider, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MuaServiceProvider data = serviceProviders.get(position);
        holder.mService.setText(data.getService());
        holder.mPrice.setText("Harga : " + data.getPrice());
        holder.mInformation.setText(data.getInformation());
        holder.mDuration.setText(data.getDuration());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MuaDetailServiceActivity.class);
                intent.putExtra("service_id", data.getId());
                intent.putExtra("service_name", data.getId());
                intent.putExtra("service_price", data.getId());
                intent.putExtra("service_information", data.getId());
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

        public ViewHolder(View itemView) {
            super(itemView);
            mService = itemView.findViewById(R.id.tvServices);
            mPrice = itemView.findViewById(R.id.tvPrice);
            mInformation = itemView.findViewById(R.id.tvInformation);
            mDuration = itemView.findViewById(R.id.tvDuration);
        }
    }
}