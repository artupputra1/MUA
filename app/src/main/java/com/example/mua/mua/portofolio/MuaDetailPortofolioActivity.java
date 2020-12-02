package com.example.mua.mua.portofolio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.R;
import com.example.mua.mua.MenuMuaActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MuaDetailPortofolioActivity extends AppCompatActivity {

    String TAG = "MuaDetailPortofolioActivity";
    String provider_id;
    String portofolio;
    ImageView iv_portofolio;
    Button bt_delete;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_detail_portofolio);

        iv_portofolio = findViewById(R.id.ivPortofolio);
        bt_delete = findViewById(R.id.btDelete);
        provider_id = getIntent().getStringExtra("id_portofolio");
        portofolio = getIntent().getStringExtra("portofolio");

        Picasso.get()
                .load(portofolio)
                .fit()
                .into(iv_portofolio);

        bt_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });

    }

    void delete() {
            progressDialog = ProgressDialog.show(MuaDetailPortofolioActivity.this,"Proses Pendaftaran","Tunggu Sebentar. . .",false,false);
            AndroidNetworking.post("http://belajarkoding.xyz/mua/provider/delete_portofolio.php")
                    .addBodyParameter("id_provider",provider_id)
                    .setPriority(Priority.LOW)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            try {
                                if (response.get("success").toString().equals("1")) {
                                    Log.d(TAG, "onResponse: " + response);
                                    Toast.makeText(getApplicationContext() ,"Proses Register Berhasil", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MuaDetailPortofolioActivity.this, MenuMuaActivity.class);
                                    finish();
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext() ,"Proses Register Gagal", Toast.LENGTH_LONG).show();
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