package com.example.mua.mua;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MuaPortofolioProviderAdapter extends RecyclerView.Adapter<MuaPortofolioProviderAdapter.ViewHolder> {

    private List<MuaPortofolioProvider> portofolioProviders;
    public Context context;

    public MuaPortofolioProviderAdapter(Context context, List<MuaPortofolioProvider> portofolioProviders) {
        this.portofolioProviders = portofolioProviders;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_mua_portofolio, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final MuaPortofolioProvider data = portofolioProviders.get(position);
        Picasso.get()
                .load(data.link)
                .fit()
                .into(holder.mPortofolio);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent varIntent = new Intent(context, MuaDetailPortofolioActivity.class);
                varIntent.putExtra("id_portofolio", data.getId());
                varIntent.putExtra("portofolio", data.getLink());
                context.startActivity(varIntent);
            }
        });
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