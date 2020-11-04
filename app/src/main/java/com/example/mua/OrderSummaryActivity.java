package com.example.mua;

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
import com.example.mua.booking.UploadTransactionActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderSummaryActivity extends AppCompatActivity {

    private static final String TAG = "OrderSummary";
    TextView tv_service, tv_price, tv_information, tv_date, tv_time, tv_customer, tv_phone, tv_person, tv_address, tv_total, tv_dp;
    private String service_id, date, time, customer, phone, person, address;
    Button bt_book;
    ProgressDialog progressDialog;
    String id_user;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    int harga, total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");
        init_view();

        service_id = getIntent().getStringExtra("service_id");
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        customer = getIntent().getStringExtra("customer_name");
        phone = getIntent().getStringExtra("phone");
        person = getIntent().getStringExtra("amount_person");
        address = getIntent().getStringExtra("address");

        get_services();

        tv_date.setText(date);
        tv_time.setText(time);
        tv_customer.setText(customer);
        tv_phone.setText(phone);
        tv_person.setText(person);
        tv_address.setText(address);

        bt_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_service();
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
        tv_total = findViewById(R.id.tvTotal);
        tv_dp = findViewById(R.id.tvDP);
        bt_book = findViewById(R.id.btOrder);
    }

    public void get_services(){
        progressDialog = ProgressDialog.show(OrderSummaryActivity.this,"Proses Login","Tunggu Sebentar. . .",false,false);
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
                                    tv_service.setText(data.getString("service"));
                                    tv_price.setText(data.getString("price"));
                                    tv_information.setText(data.getString("information"));
                                }
                                int total = (Integer.parseInt(tv_price.getText().toString())*Integer.parseInt(person));
                                int dp = (Integer.parseInt(tv_price.getText().toString())*Integer.parseInt(person))/2;
                                tv_dp.setText(new String(String.valueOf(dp)));
                                tv_total.setText(new String(String.valueOf(total)));
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

    public void book_service(){
        progressDialog = ProgressDialog.show(OrderSummaryActivity.this,"Proses Pendaftaran","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/booking.php")
                .addBodyParameter("service_id", service_id)
                .addBodyParameter("date", date)
                .addBodyParameter("time", time)
                .addBodyParameter("user_id", id_user)
                .addBodyParameter("customer_name", customer)
                .addBodyParameter("phone", phone)
                .addBodyParameter("amount_person", person)
                .addBodyParameter("address", address)
                .addBodyParameter("total_price", tv_total.getText().toString())
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(OrderSummaryActivity.this,response.getString("message"),Toast.LENGTH_LONG).show();
                            if (response.getString("message").equals("Jadwal Penuh")) {

                            } else {
                                Intent intent = new Intent(OrderSummaryActivity.this, MainActivity.class);
                                intent.putExtra("booking_id", response.getString("id"));
                                startActivity(intent);
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