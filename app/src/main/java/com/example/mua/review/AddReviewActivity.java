package com.example.mua.review;

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
import com.example.mua.OrderSummaryActivity;
import com.example.mua.R;

import org.json.JSONException;
import org.json.JSONObject;

public class AddReviewActivity extends AppCompatActivity {

    private static final String TAG = "AddReviewActivity";
    EditText ed_review;
    Button bt_save;
    ProgressDialog progressDialog;
    String service_id, id_user;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        init_view();

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");
        service_id = getIntent().getStringExtra("service_id");
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_review();
            }
        });

    }

    public void init_view(){
        ed_review = findViewById(R.id.edReview);
        bt_save = findViewById(R.id.btSave);
    }

    public void save_review(){
        progressDialog = ProgressDialog.show(AddReviewActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/add_review.php")
                .addBodyParameter("service_id", service_id)
                .addBodyParameter("user_id", id_user)
                .addBodyParameter("review", ed_review.getText().toString())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(AddReviewActivity.this,response.getString("message"),Toast.LENGTH_LONG).show();
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