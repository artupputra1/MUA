package com.example.mua.provider;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.OrderSummaryActivity;
import com.example.mua.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProviderActivity extends AppCompatActivity {

    private static final String TAG = "ProviderActivity";
    private List<Provider> dataProvider;
    private RecyclerView recyclerView;
    private String category_id;
    ProgressDialog progressDialog;
    Button bt_min, bt_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Pilih MUA");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bt_min = findViewById(R.id.btMin);
        bt_max = findViewById(R.id.btMax);

        category_id = getIntent().getStringExtra("category_id");

        recyclerView = findViewById(R.id.rvProvider);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataProvider = new ArrayList<>();
        AndroidNetworking.initialize(getApplicationContext());
        getData();

        bt_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilter("min");
            }
        });

        bt_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilter("max");
            }
        });

    }

    public void getFilter(String filter){
        dataProvider.clear();
        progressDialog = ProgressDialog.show(ProviderActivity.this,"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/list_provider_filter.php")
                .addQueryParameter("filter", filter)
                .addQueryParameter("category_id", category_id)
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
                                    dataProvider.add(new Provider(
                                            data.getString("id_mua"),
                                            data.getString("name_business"),
                                            data.getString("image"),
                                            data.getString("address"),
                                            data.getString("price"),
                                            category_id
                                    ));
                                }

                                ProviderAdapter adapter = new ProviderAdapter(ProviderActivity.this, dataProvider);
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

    public void getData(){
        dataProvider.clear();
        progressDialog = ProgressDialog.show(ProviderActivity.this,"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/list_provider.php")
                .addQueryParameter("category_id", category_id)
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
                                    dataProvider.add(new Provider(
                                            data.getString("id_mua"),
                                            data.getString("name_business"),
                                            data.getString("image"),
                                            data.getString("address"),
                                            data.getString("price"),
                                            category_id
                                    ));
                                }

                                ProviderAdapter adapter = new ProviderAdapter(ProviderActivity.this, dataProvider);
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