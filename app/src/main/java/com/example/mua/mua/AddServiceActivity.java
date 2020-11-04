package com.example.mua.mua;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddServiceActivity extends AppCompatActivity {

    String category_id;
    Button bt_save;
    EditText tv_price, tv_name, tv_information, tv_duration;
    String TAG = "AddServiceActivity";
    SharedPreferences sharedpreferences;
    ProgressDialog progressDialog;
    public static final String my_shared_preferences = "mua";
    String provider_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        init_view();
        sharedpreferences = this.getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        provider_id = sharedpreferences.getString("id_provider", "");
        category_id = getIntent().getStringExtra("category_id");

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_service();
            }
        });

    }

    private void init_view() {
        tv_price = findViewById(R.id.edPrice);
        tv_name = findViewById(R.id.edService);
        tv_information = findViewById(R.id.edInformation);
        tv_duration = findViewById(R.id.edDuration);
        bt_save = findViewById(R.id.btSave);
    }

    private void add_service() {
        progressDialog = ProgressDialog.show(AddServiceActivity.this,"Proses Upload","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/provider/add_service.php")
                .addBodyParameter("category_id",category_id)
                .addBodyParameter("provider_id",provider_id)
                .addBodyParameter("service",tv_name.getText().toString())
                .addBodyParameter("price",tv_price.getText().toString())
                .addBodyParameter("information",tv_information.getText().toString())
                .addBodyParameter("duration",tv_information.getText().toString())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.get("success").toString().equals("1")) {
                                Log.d(TAG, "onResponse: " + response);
                                Toast.makeText(getApplicationContext() ,"Proses Tambah Service Berhasil", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddServiceActivity.this, MenuMuaActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext() ,"Proses Tambah Service Gagal", Toast.LENGTH_LONG).show();
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

}