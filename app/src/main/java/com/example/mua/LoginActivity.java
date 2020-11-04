package com.example.mua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.provider.Provider;
import com.example.mua.provider.ProviderActivity;
import com.example.mua.provider.ProviderAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    DataStorage dataStorage;
    private static final String TAG = "LoginActivity";
    EditText ed_username, ed_password;
    Button bt_login;
    TextView tv_register, tv_guest;
    ProgressDialog progressDialog ;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Login");

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        ed_username = findViewById(R.id.edUsername);
        ed_password = findViewById(R.id.edPassword);
        bt_login = findViewById(R.id.btLogin);
        tv_register = findViewById(R.id.tvRegister);
        tv_guest = findViewById(R.id.tvGuest);

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proses_login();
            }
        });

        db = FirebaseFirestore.getInstance();

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tv_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("id", "0");
                editor.putString("username", "Tamu");
                editor.putString("name", "Tamu");
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });


    }

    public void proses_login(){
        String token = sharedpreferences.getString("fcm_id", "");
        progressDialog = ProgressDialog.show(LoginActivity.this,"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/login.php")
                .addBodyParameter("username",ed_username.getText().toString())
                .addBodyParameter("password",ed_password.getText().toString())
                .addBodyParameter("token",token)
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

                                editor.putString("id", response.get("id").toString());
                                editor.putString("username", response.get("username").toString());
                                editor.putString("name", response.get("username").toString());
                                editor.commit();


                                add_user_chat(response.get("id").toString(), response.get("username").toString());
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext() ,"Proses Login Gagal", Toast.LENGTH_LONG).show();
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

    public void add_user_chat(String id, String name) {
        Map<String, Object> city = new HashMap<>();
        city.put("uid", id);
        city.put("name", name);

        db.collection("user").document(id)
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