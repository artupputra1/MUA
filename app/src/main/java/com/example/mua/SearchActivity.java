package com.example.mua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    // Deklarasi Variabel
    private static final String TAG = "SearchActivity";
    String keyword;
    ProgressDialog progressDialog;
    // deklarasi variabel dSearch dengan class Search
    private List<Search> dSearch;
    private RecyclerView recyclerView;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Mengambil data yang dikirimkan activity sebelumnya
        keyword = getIntent().getStringExtra("keyword");

        // Init Recyclerview
        no_data = findViewById(R.id.tvNoData);
        recyclerView = findViewById(R.id.rvSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Membuat arraylist
        dSearch = new ArrayList<>();

        // Memanggil fungsi getData
        getData();
    }

    // fungsi getData
    public void getData(){
        // Menampilkan Progress Dialoog
        progressDialog = ProgressDialog.show(this,"Proses","Tunggu Sebentar. . .",false,false);
        // Mengakses link yang ditentukan, dengan parameter keyword
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
                                // Jika ada data
                                // Looping mengambil data
                                for (int i = 0; i < response.length(); i++) {
                                    // memasukkan json object ke variabel data
                                    JSONObject data = response.getJSONObject(i);

                                    // memasukkan data ke arraylist dSearch (getstring digunakan untuk mengambil data dengan parameter name, name harus samadengan web_service
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
                                // Jika tidak ada data
                                if (response.toString().equals("[]")) {
                                    no_data.setVisibility(View.VISIBLE);
                                    no_data.setText(keyword + " Tidak Ditemukan");
                                } else {
                                    no_data.setVisibility(View.GONE);
                                }

                                // Inisialisai adapter
                                SearchAdapter adapter = new SearchAdapter(SearchActivity.this, dSearch);
                                // memasukkan adapter ke recyclervier
                                recyclerView.setAdapter(adapter);
                            }

                            catch (JSONException e) {
                                no_data.setVisibility(View.VISIBLE);
                                no_data.setText(keyword + " Tidak Ditemukan");
                                e.printStackTrace();
                            }
                        }
                    } @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        no_data.setVisibility(View.VISIBLE);
                        no_data.setText(keyword + " Tidak Ditemukan");
                        Log.d(TAG, "onError: " + error);
                    }
                });
    }


}