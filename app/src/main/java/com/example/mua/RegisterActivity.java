package com.example.mua;

import androidx.appcompat.app.ActionBar;
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
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {


    private static final String TAG = "RegisterActivity";
    EditText ed_username, ed_password, ed_name, ed_phone, ed_email;
    Button bt_register;
    TextView tv_login;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Register");

        init_view();

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proses_register();
            }
        });

    }

    public void proses_register(){
        progressDialog = ProgressDialog.show(RegisterActivity.this,"Proses Pendaftaran","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/register.php")
                .addBodyParameter("username",ed_username.getText().toString())
                .addBodyParameter("password",ed_password.getText().toString())
                .addBodyParameter("name",ed_name.getText().toString())
                .addBodyParameter("email",ed_email.getText().toString())
                .addBodyParameter("phone",ed_phone.getText().toString())
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
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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

    public void init_view(){
        ed_username = findViewById(R.id.edUsername);
        ed_password = findViewById(R.id.edPassword);
        ed_name = findViewById(R.id.edName);
        ed_email = findViewById(R.id.edEmail);
        ed_phone = findViewById(R.id.edPhone);
        tv_login = findViewById(R.id.tvLogin);
        bt_register = findViewById(R.id.btRegister);
    }

}