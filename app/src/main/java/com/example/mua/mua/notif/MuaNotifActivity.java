package com.example.mua.mua.notif;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;
import com.example.mua.notification.Notification;
import com.example.mua.notification.NotificationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MuaNotifActivity extends AppCompatActivity {

    private static final String TAG = "NotificationFragment";
    private List<Notification> dataNotification;
    private RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String id_provider;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_notif);
        sharedpreferences = this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_provider = sharedpreferences.getString("id_provider", "");
        recyclerView = findViewById(R.id.rvNotification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataNotification = new ArrayList<>();
        getData();
    }

    public void getData(){
        progressDialog = ProgressDialog.show(this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_notif_mua.php")
                .addQueryParameter("provider_id", id_provider)
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
                                    dataNotification.add(new Notification(
                                            data.getString("id"),
                                            data.getString("provider_id"),
                                            data.getString("provider_id"),
                                            data.getString("title"),
                                            data.getString("content"),
                                            data.getString("date")
                                    ));
                                }

                                MuaNotifAdapter adapter = new MuaNotifAdapter(MuaNotifActivity.this, dataNotification);
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