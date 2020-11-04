package com.example.mua.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.MainActivity;
import com.example.mua.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

public class DetailBookingActivity extends AppCompatActivity {

    private static final String TAG = "DetailBookingActivity";
    TextView tv_service, tv_price, tv_information, tv_date, tv_time, tv_customer, tv_phone, tv_person, tv_address;
    private String booking_id, date, time, customer, phone, person, address;
    LinearLayout linear_button;
    Button bt_done;
    ProgressDialog progressDialog ;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booking);

        Intent intent = getIntent();

        booking_id = intent.getStringExtra("booking_id");
        type = intent.getStringExtra("type");

        init_view();
        get_booking();
        bt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

        if (type.equals("1")) {
            linear_button.setVisibility(View.GONE);
        }
        else if (type.equals("3")) {
            linear_button.setVisibility(View.VISIBLE);
        }

    }

    public void get_booking(){
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/detail_booking.php")
                .addQueryParameter("booking_id", booking_id)
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
                                    tv_service.setText(data.getString("service"));
                                    tv_information.setText(data.getString("information"));
                                    tv_date.setText(data.getString("date"));
                                    tv_time.setText(data.getString("time"));
                                    tv_customer.setText(data.getString("customer_name"));
                                    tv_phone.setText(data.getString("phone"));
                                    tv_person.setText(data.getString("amount_person"));
                                    tv_address.setText(data.getString("address"));
                                }
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

    private void done() {
        progressDialog = ProgressDialog.show(DetailBookingActivity.this,"Proses Upload","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/done.php")
                .addBodyParameter("booking_id",booking_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.get("success").toString().equals("1")) {
                                Log.d(TAG, "onResponse: " + response);
                                Toast.makeText(getApplicationContext() ,"Pesanan Selesai", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DetailBookingActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext() ,"Pesanan Gagal", Toast.LENGTH_LONG).show();
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
        tv_service = findViewById(R.id.tvServices);
        tv_price = findViewById(R.id.tvPrice);
        tv_information = findViewById(R.id.tvInformation);
        tv_date = findViewById(R.id.tvDate);
        tv_time = findViewById(R.id.tvTime);
        tv_customer = findViewById(R.id.tvCustomer);
        tv_phone = findViewById(R.id.tvPhone);
        tv_person = findViewById(R.id.tvPerson);
        tv_address = findViewById(R.id.tvAddress);
        bt_done = findViewById(R.id.btDone);
        linear_button = findViewById(R.id.linearDone);
    }
}