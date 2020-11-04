package com.example.mua.provider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.LoginActivity;
import com.example.mua.OrderActivity;
import com.example.mua.OrderSummaryActivity;
import com.example.mua.R;
import com.example.mua.RegisterActivity;
import com.example.mua.chat.MessageActivity;
import com.example.mua.provider.detail_provider.PortofolioProvider;
import com.example.mua.provider.detail_provider.PortofolioProviderAdapter;
import com.example.mua.provider.detail_provider.ReviewProvider;
import com.example.mua.provider.detail_provider.ReviewProviderAdapter;
import com.example.mua.provider.detail_provider.ServiceProvider;
import com.example.mua.provider.detail_provider.ServiceProviderAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProviderActivity extends AppCompatActivity {

    private static final String TAG = "DetailProviderActivity";
    private List<ServiceProvider> serviceProviders;
    private List<PortofolioProvider> portofolioProviders;
    private List<ReviewProvider> reviewProviders;
    private RecyclerView recyclerViewServices, recyclerViewPortofolio, recyclerViewReview;
    private String category_id, provider_id;
    Button bt_chat;
    ImageView image;
    TextView mua_name, address, job, member_since, phone, note, mua_since;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String id_user;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_provider);

        db = FirebaseFirestore.getInstance();

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");

        bt_chat = findViewById(R.id.btChat);
        category_id = getIntent().getStringExtra("category_id");
        provider_id = getIntent().getStringExtra("provider_id");

        recyclerViewServices = findViewById(R.id.rvServices);
        recyclerViewPortofolio = findViewById(R.id.rvPortofolio);
        recyclerViewReview = findViewById(R.id.rvReview);
        mua_name = findViewById(R.id.tvMuaName);
        address = findViewById(R.id.tvAddress);
        job = findViewById(R.id.tvJob);
        member_since = findViewById(R.id.tvMemberSince);
        mua_since = findViewById(R.id.tvMuaSince);
        phone = findViewById(R.id.tvPhone);
        image = findViewById(R.id.ivImage);
        note = findViewById(R.id.tvNote);

        recyclerViewServices.setHasFixedSize(true);
        recyclerViewServices.setLayoutManager(new LinearLayoutManager(this));
        serviceProviders = new ArrayList<>();

        recyclerViewPortofolio.setHasFixedSize(true);
        recyclerViewPortofolio.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        portofolioProviders = new ArrayList<>();

        recyclerViewReview.setHasFixedSize(true);
        recyclerViewReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reviewProviders = new ArrayList<>();

        AndroidNetworking.initialize(getApplicationContext());

        getData();
        getDataPortofolio();
        getDataReview();
        getProvider();

        bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_room_chat(id_user,provider_id);
                Intent varIntent = new Intent(DetailProviderActivity.this, MessageActivity.class);
                varIntent.putExtra("chat_id", id_user+"_"+provider_id);
                startActivity(varIntent);
            }
        });

    }

    public void getProvider(){
        progressDialog = ProgressDialog.show(this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/detail_provider.php")
                .addQueryParameter("provider_id", provider_id)
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
                                    Picasso.get()
                                            .load("http://belajarkoding.xyz/mua/upload/profile/"+data.getString("image"))
                                            .fit()
                                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                                            .into(image);
                                    mua_name.setText(data.getString("name_business"));
                                    address.setText(data.getString("address"));
                                    job.setText(data.getString("Job_done"));
                                    member_since.setText("No Telepon : "+data.getString("member_since"));
                                    mua_since.setText("Mulai MUA Dari : "+data.getString("mua_since"));
                                    phone.setText(data.getString("phone"));
                                    note.setText(data.getString("info"));
                                }

                                ServiceProviderAdapter adapter = new ServiceProviderAdapter(DetailProviderActivity.this, serviceProviders);
                                recyclerViewServices.setAdapter(adapter);
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
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/detail_provider_service.php")
                .addQueryParameter("category_id", category_id)
                .addQueryParameter("provider_id", provider_id)
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
                                    serviceProviders.add(new ServiceProvider(
                                            data.getString("id"),
                                            data.getString("service"),
                                            data.getString("price"),
                                            data.getString("duration"),
                                            data.getString("information")
                                    ));
                                }

                                ServiceProviderAdapter adapter = new ServiceProviderAdapter(DetailProviderActivity.this, serviceProviders);
                                recyclerViewServices.setAdapter(adapter);
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

    public void getDataPortofolio(){
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/detail_provider_portofolio.php")
                .addQueryParameter("provider_id", provider_id)
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
                                    portofolioProviders.add(new PortofolioProvider(
                                            data.getString("id"),
                                            data.getString("provider_id"),
                                            data.getString("link")
                                    ));
                                }

                                PortofolioProviderAdapter adapter = new PortofolioProviderAdapter(DetailProviderActivity.this, portofolioProviders);
                                recyclerViewPortofolio.setAdapter(adapter);
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

    public void getDataReview(){
            AndroidNetworking.get("http://belajarkoding.xyz/mua/user/detail_provider_review.php")
                .addQueryParameter("provider_id", provider_id)
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
                                    if (data.getString("review") != null ){
                                        reviewProviders.add(new ReviewProvider(
                                                data.getString("id"),
                                                data.getString("id"),
                                                data.getString("review"),
                                                data.getString("name_user"),
                                                data.getString("service")
                                        ));
                                    }
                                }

                                ReviewProviderAdapter adapter = new ReviewProviderAdapter(DetailProviderActivity.this, reviewProviders);
                                recyclerViewReview.setAdapter(adapter);
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

    public void add_room_chat(String id_user, String id_mua) {
        Map<String, Object> city = new HashMap<>();
        city.put("id_user", id_user);
        city.put("id_mua", id_mua);
        city.put("id_room", id_user+"_"+id_mua);

        db.collection("room_chat").document(id_user+"_"+id_mua)
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }


}