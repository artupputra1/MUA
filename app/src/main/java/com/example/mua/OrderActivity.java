package com.example.mua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.provider.DetailProviderActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {

    private static final String TAG = "OrderActivity";
    TextView tv_service, tv_price, tv_information, tv_date, tv_time;
    EditText ed_customer, ed_phone, ed_person, ed_address;
    Button bt_booking;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int mHour;
    private int mMinute;
    String service_id;
    ProgressDialog progressDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        service_id = getIntent().getStringExtra("service_id");

        init_view();
        get_services();

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tv_date);
            }
        });

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Timepicker();
            }
        });

        bt_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent varIntent = new Intent(OrderActivity.this, OrderSummaryActivity.class);
                varIntent.putExtra("service_id", service_id);
                varIntent.putExtra("date", tv_date.getText().toString());
                varIntent.putExtra("time", tv_time.getText().toString());
                varIntent.putExtra("customer_name", ed_customer.getText().toString());
                varIntent.putExtra("phone", ed_phone.getText().toString());
                varIntent.putExtra("amount_person", ed_person.getText().toString());
                varIntent.putExtra("address", ed_address.getText().toString());
                startActivity(varIntent);
            }
        });


    }

    private void show_Timepicker() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int pHour,
                                          int pMinute) {

                        mHour = pHour;
                        mMinute = pMinute;
                        tv_time.setText(mHour+":"+mMinute);
                    }
                }, mHour, mMinute, true);

        timePickerDialog.show();
    }

    private void showDateDialog(final TextView date){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void init_view(){
        tv_service = findViewById(R.id.tvServices);
        tv_price = findViewById(R.id.tvPrice);
        tv_information = findViewById(R.id.tvInformation);
        tv_date = findViewById(R.id.tvDate);
        tv_time = findViewById(R.id.tvTime);
        ed_customer = findViewById(R.id.edCustomer);
        ed_phone = findViewById(R.id.edPhone);
        ed_person = findViewById(R.id.edPerson);
        ed_address = findViewById(R.id.edAddress);
        bt_booking = findViewById(R.id.btOrder);
    }

    public void get_services(){
        progressDialog = ProgressDialog.show(OrderActivity.this,"Proses Login","Tunggu Sebentar. . .",false,false);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}