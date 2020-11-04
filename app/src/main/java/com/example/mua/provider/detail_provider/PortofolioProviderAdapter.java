package com.example.mua.provider.detail_provider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;
import com.example.mua.provider.DetailProviderActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PortofolioProviderAdapter extends RecyclerView.Adapter<PortofolioProviderAdapter.ViewHolder> {

    private List<PortofolioProvider> portofolioProviders;


    public PortofolioProviderAdapter(DetailProviderActivity detailProviderActivity, List<PortofolioProvider> portofolioProviders) {
        this.portofolioProviders = portofolioProviders;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_portofolio_provider, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PortofolioProvider data = portofolioProviders.get(position);
        Picasso.get()
                .load(data.link)
                .fit()
                .into(holder.mPortofolio);
    }

    @Override
    public int getItemCount() {
        return portofolioProviders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mPortofolio;

        public ViewHolder(View itemView) {
            super(itemView);
            mPortofolio = itemView.findViewById(R.id.ivPortofolio);
        }
    }
}