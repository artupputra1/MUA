package com.example.mua;

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
import com.example.mua.provider.DetailProviderActivity;
import com.example.mua.provider.detail_provider.PortofolioProvider;
import com.example.mua.provider.detail_provider.ServiceProvider;
import com.example.mua.provider.detail_provider.ServiceProviderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    String keyword;
    ProgressDialog progressDialog;
    private List<Search> dSearch;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        keyword = getIntent().getStringExtra("keyword");

        recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dSearch = new ArrayList<>();
        getData();
    }

    public void getData(){
        progressDialog = ProgressDialog.show(this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/search.php")
                .addQueryParameter("keyword", keyword)
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
                                    dSearch.add(new Search(
                                            data.getString("id"),
                                            data.getString("provider_id"),
                                            data.getString("name_business"),
                                            data.getString("service"),
                                            data.getString("price"),
                                            data.getString("information"),
                                            data.getString("category_id")
                                    ));
                                }

                                SearchAdapter adapter = new SearchAdapter(SearchActivity.this, dSearch);
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


}