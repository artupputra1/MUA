package com.example.mua;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.booking.DetailBookingActivity;
import com.example.mua.review.AddReviewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class CancelBookingActivity extends AppCompatActivity {

    private static final String TAG = "CancelBookingActivity";
    EditText ed_reason;
    Button bt_submit;
    ProgressDialog progressDialog;
    private String booking_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_booking);
        Intent intent = getIntent();
        booking_id = intent.getStringExtra("booking_id");

        ed_reason = findViewById(R.id.edReason);
        bt_submit = findViewById(R.id.btSubmit);

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_booking();
            }
        });

    }

    public void cancel_booking(){
        progressDialog = ProgressDialog.show(CancelBookingActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/cancel_booking.php")
                .addBodyParameter("booking_id", booking_id)
                .addBodyParameter("reason", ed_reason.getText().toString())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(CancelBookingActivity.this,response.getString("message"),Toast.LENGTH_LONG).show();
                            Intent varIntent = new Intent(CancelBookingActivity.this, MainActivity.class);
                            startActivity(varIntent);
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