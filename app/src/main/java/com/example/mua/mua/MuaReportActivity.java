package com.example.mua.mua;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;
import com.example.mua.review.Review;
import com.example.mua.review.ReviewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MuaReportActivity extends AppCompatActivity {
    private static final String TAG = "MuaReportActivity";
    private List<Report> dataReport;
    private RecyclerView recyclerView;
    private List<Review> dataReview;
    private RecyclerView rvReview;
    ProgressDialog progressDialog;
    String provider_id;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    Button bt_makeup, bt_hairdo, bt_hena, bt_nail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_report);

        bt_makeup = findViewById(R.id.btMakeup);
        bt_hairdo = findViewById(R.id.btHairdo);
        bt_hena = findViewById(R.id.btHena);
        bt_nail = findViewById(R.id.btNail);
        rvReview = findViewById(R.id.rvReview);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        provider_id = sharedpreferences.getString("id_provider", "");
        Log.d(TAG, "Provider ID: " + provider_id);
        recyclerView = findViewById(R.id.rvReport);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvReview.setHasFixedSize(true);
        rvReview.setLayoutManager(new LinearLayoutManager(this));
        dataReport = new ArrayList<>();
        dataReview = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());


        bt_makeup.setBackgroundResource(R.drawable.button_primary);
        bt_makeup.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        getData("1");
        getReview("1");

        bt_makeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataReview.clear();
                resetButton();
                bt_makeup.setBackgroundResource(R.drawable.button_primary);
                bt_makeup.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                progressDialog = ProgressDialog.show(MuaReportActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
                getData("1");
                getReview("1");
                progressDialog.dismiss();
            }
        });

        bt_hairdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataReview.clear();
                resetButton();
                bt_hairdo.setBackgroundResource(R.drawable.button_primary);
                bt_hairdo.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                progressDialog = ProgressDialog.show(MuaReportActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
                getData("2");
                getReview("2");
                progressDialog.dismiss();
            }
        });

        bt_hena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataReview.clear();
                resetButton();
                bt_hena.setBackgroundResource(R.drawable.button_primary);
                bt_hena.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                progressDialog = ProgressDialog.show(MuaReportActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
                getData("4");
                getReview("4");
                progressDialog.dismiss();
            }
        });

        bt_nail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataReview.clear();
                resetButton();
                bt_nail.setBackgroundResource(R.drawable.button_primary);
                bt_nail.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                progressDialog = ProgressDialog.show(MuaReportActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
                getData("3");
                getReview("3");
                progressDialog.dismiss();
            }
        });
    }

    public void getData(String category){
        dataReport.clear();
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_report.php")
                .addQueryParameter("provider_id", provider_id)
                .addQueryParameter("category_id", category)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + response);
                        {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject data = response.getJSONObject(i);
                                    dataReport.add(new Report(
                                            data.getString("month"),
                                            data.getString("total")
                                    ));
                                }

                                MuaReportAdapter adapter = new MuaReportAdapter(MuaReportActivity.this, dataReport);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error);
                    }
                });
    }

    public void getReview(String category){
        dataReview.clear();
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_report_review.php")
                .addQueryParameter("provider_id", provider_id)
                .addQueryParameter("category_id", category)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + response);
                        {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject data = response.getJSONObject(i);
                                    dataReview.add(new Review(
                                            data.getString("id"),
                                            data.getString("service_id"),
                                            data.getString("user_id"),
                                            data.getString("name"),
                                            data.getString("review"),
                                            data.getString("rating"),
                                            data.getString("image")
                                    ));
                                }

                                ReviewAdapter adapter = new ReviewAdapter(MuaReportActivity.this, dataReview);
                                rvReview.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        Log.d(TAG, "onError: " + error);
                    }
                });
    }

    public void resetButton() {
        bt_makeup.setBackgroundResource(R.drawable.button_secondary);
        bt_makeup.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bt_nail.setBackgroundResource(R.drawable.button_secondary);
        bt_nail.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bt_hairdo.setBackgroundResource(R.drawable.button_secondary);
        bt_hairdo.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        bt_hena.setBackgroundResource(R.drawable.button_secondary);
        bt_hena.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

}