package com.example.mua.mua;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mua.R;
import com.example.mua.review.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MuaReportAdapter extends RecyclerView.Adapter<MuaReportAdapter.ViewHolder> {

    private List<Report> dataReport;
    public Context context;

    public MuaReportAdapter(Context context, List<Report> dataReport) {
        this.context = context;
        this.dataReport = dataReport;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_report, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String status;
        final Report data = dataReport.get(position);
        String valmonth;
        switch (data.getMonth())
        {
            case "1":
                valmonth = "Januari";
                break;
            case "2": valmonth = "Februari"; break;
            case "3": valmonth = "Maret"; break;
            case "4": valmonth = "April"; break;
            case "5": valmonth = "Mei"; break;
            case "6": valmonth = "Juni"; break;
            case "7": valmonth = "Juli"; break;
            case "8": valmonth = "Agustus"; break;
            case "9": valmonth = "September"; break;
            case "10": valmonth = "Oktober"; break;
            case "11": valmonth = "November"; break;
            case "12": valmonth = "Desember"; break;
            default:
                throw new IllegalStateException("Unexpected value: " + data.getMonth());
        }
        holder.mMonth.setText(valmonth);
        holder.mTotal.setText(data.getTotal());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataReport.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMonth, mTotal;

        public ViewHolder(View itemView) {
            super(itemView);
            mMonth = itemView.findViewById(R.id.tvMonth);
            mTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}