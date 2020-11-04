package com.example.mua.provider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.LoginActivity;
import com.example.mua.R;
import com.example.mua.RegisterActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {

    private List<Provider> dataProvider;
    public Context context;

    public ProviderAdapter(Context context, List<Provider> dataProvider) {
        this.context = context;
        this.dataProvider = dataProvider;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_provider, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Provider data = dataProvider.get(position);
        Picasso.get()
                .load("http://belajarkoding.xyz/mua/upload/profile/"+data.getImage())
                .fit()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(holder.mImage);
        holder.mName.setText(data.getName_business());
        holder.mAddress.setText("Alamat : " + data.getAddress());
        holder.mPrice.setText("Harga Mulai Dari : " + data.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent varIntent = new Intent(context, DetailProviderActivity.class);
                varIntent.putExtra("category_id", data.getCategory_id().toString());
                varIntent.putExtra("provider_id", data.getId().toString());
                context.startActivity(varIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataProvider.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mName, mAddress, mPrice;
        ImageView mImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tvName);
            mAddress = itemView.findViewById(R.id.tvAddress);
            mPrice = itemView.findViewById(R.id.tvPrice);
            mImage = itemView.findViewById(R.id.ivImage);
        }
    }
}