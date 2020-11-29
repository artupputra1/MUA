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
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.MainActivity;
import com.example.mua.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MuaRegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    EditText ed_username, ed_password, ed_owner, ed_mua, ed_address;
    Button bt_register;
    TextView tv_login;
    ProgressDialog progressDialog ;
    String id_user;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mua_register);


        init_view();

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proses_register();
            }
        });

    }

    public void proses_register(){
        progressDialog = ProgressDialog.show(MuaRegisterActivity.this,"Proses Pendaftaran","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/provider/register.php")
                .addBodyParameter("owner",ed_owner.getText().toString())
                .addBodyParameter("name_mua",ed_mua.getText().toString())
                .addBodyParameter("address",ed_address.getText().toString())
                .addBodyParameter("user_id",id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.get("success").toString().equals("1")) {
                                Log.d(TAG, "onResponse: " + response);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("id_provider", response.get("last_id").toString());
                                editor.commit();

                                Intent intent = new Intent(MuaRegisterActivity.this, MenuMuaActivity.class);
                                intent.putExtra("id_mua",response.get("last_id").toString());
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

    public void init_view(){
        ed_owner = findViewById(R.id.edOwner);
        ed_mua = findViewById(R.id.edNameMua);
        ed_address = findViewById(R.id.edAddress);
        bt_register = findViewById(R.id.btRegister);
    }

}