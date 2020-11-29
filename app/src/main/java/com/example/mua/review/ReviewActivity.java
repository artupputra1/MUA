package com.example.mua.review;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;
import com.example.mua.provider.Provider;
import com.example.mua.provider.ProviderActivity;
import com.example.mua.provider.ProviderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";
    private List<Review> dataReview;
    private RecyclerView recyclerView;
    ProgressDialog progressDialog;
    String service_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Review");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        service_id = getIntent().getStringExtra("service_id");
        recyclerView = findViewById(R.id.rvReview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataReview = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());
        getData();

    }

    public void getData(){
        progressDialog = ProgressDialog.show(ReviewActivity.this,"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/list_review.php")
                .addQueryParameter("service_id", service_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressDialog.dismiss();
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

                                ReviewAdapter adapter = new ReviewAdapter(ReviewActivity.this, dataReview);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onError: " + error);
                    }
                });
    }

}