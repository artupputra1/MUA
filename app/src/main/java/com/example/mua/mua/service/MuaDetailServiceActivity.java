package com.example.mua.mua.service;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.R;
import com.example.mua.mua.MenuMuaActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MuaDetailServiceActivity extends AppCompatActivity {

    String TAG = "MuaDetailServiceActivity";
    Button bt_save, bt_delete;
    TextView tv_review;
    EditText tv_price, tv_name, tv_information, tv_duration;
    ProgressDialog progressDialog;
    String service_id;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_detail_service);

        init_view();

        service_id = getIntent().getStringExtra("service_id");
        get_services();

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    public void get_services(){
        progressDialog = ProgressDialog.show(MuaDetailServiceActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/service.php")
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
                                    tv_name.setText(data.getString("service"));
                                    tv_price.setText(data.getString("price"));
                                    tv_information.setText(data.getString("information"));
                                    tv_duration.setText(data.getString("duration"));
                                }
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

    private void update() {
        progressDialog = ProgressDialog.show(MuaDetailServiceActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/provider/update_service.php")
                .addBodyParameter("service_id",service_id)
                .addBodyParameter("service",tv_name.getText().toString())
                .addBodyParameter("price",tv_price.getText().toString())
                .addBodyParameter("information",tv_information.getText().toString())
                .addBodyParameter("duration",tv_duration.getText().toString())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.get("success").toString().equals("1")) {
                                Log.d(TAG, "onResponse: " + response);
                                Toast.makeText(getApplicationContext() ,"Proses Update Service Berhasil", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MuaDetailServiceActivity.this, MenuMuaActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext() ,"Proses Update Service Gagal", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onError: " + error);
                    }
                });

    }

    private void delete() {
        progressDialog = ProgressDialog.show(MuaDetailServiceActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/provider/delete_service.php")
                .addBodyParameter("service_id",service_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.get("success").toString().equals("1")) {
                                Log.d(TAG, "onResponse: " + response);
                                Toast.makeText(getApplicationContext() ,"Delete Service Berhasil", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MuaDetailServiceActivity.this, MenuMuaActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext() ,"Delete Service Gagal", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onError: " + error);
                    }
                });

    }

    private void init_view() {
        tv_price = findViewById(R.id.edPrice);
        tv_name = findViewById(R.id.edService);
        tv_information = findViewById(R.id.edInformation);
        tv_duration = findViewById(R.id.edDuration);
        bt_save = findViewById(R.id.btSave);
        bt_delete = findViewById(R.id.btDelete);
        tv_review = findViewById(R.id.tvReview);
    }

}