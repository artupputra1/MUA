package com.example.mua;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.provider.DetailProviderActivity;
import com.example.mua.provider.Provider;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<Search> dataSearch;
    public Context context;

    public SearchAdapter(Context context, List<Search> dataSearch) {
        this.context = context;
        this.dataSearch = dataSearch;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Deklarasi layout yang digunakan
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    // Untuk menampilkan data ke layout
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Search data = dataSearch.get(position);
        holder.mMua.setText(data.getName_mua());
        holder.mService.setText(data.getService());
        holder.mPrice.setText(data.getPrice());
        holder.mInformation.setText(data.getInformation());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent varIntent = new Intent(context, DetailProviderActivity.class);
                varIntent.putExtra("category_id", data.getCategory_id().toString());
                varIntent.putExtra("provider_id", data.getProvider_id().toString());
                context.startActivity(varIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSearch.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        // Koneksi ke layout
        TextView mService, mPrice, mInformation, mMua;

        public ViewHolder(View itemView) {
            super(itemView);
            mMua = itemView.findViewById(R.id.tvMua);
            mService = itemView.findViewById(R.id.tvServices);
            mPrice = itemView.findViewById(R.id.tvPrice);
            mInformation = itemView.findViewById(R.id.tvInformation);
        }
    }
}