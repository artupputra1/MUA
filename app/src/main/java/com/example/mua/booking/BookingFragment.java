package com.example.mua.booking;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;
import com.example.mua.provider.Provider;
import com.example.mua.provider.ProviderActivity;
import com.example.mua.provider.ProviderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookingFragment extends Fragment {

    private static final String TAG = "BookingFragment";
    private List<Booking> dataBooking;
    private RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String id_user;
    ProgressDialog progressDialog;
    String status_booking;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_booking, container, false);

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");

//        Toast.makeText(getActivity() ,id_user, Toast.LENGTH_LONG).show();

        recyclerView = fragmentView.findViewById(R.id.rvBooking);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataBooking = new ArrayList<>();
        getData();
        return fragmentView;
    }

    public void getData(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/get_booking.php")
                .addQueryParameter("user_id", id_user)
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
                                    if(data.getString("status").equals("Dana Dicairkan")){
                                        status_booking = "Selesai";
                                    }
                                    else {
                                        status_booking = data.getString("status");
                                    }
                                    dataBooking.add(new Booking(
                                            data.getString("id"),
                                            data.getString("service_id"),
                                            data.getString("service"),
                                            data.getString("name_business"),
                                            data.getString("user_id"),
                                            data.getString("date"),
                                            data.getString("time"),
                                            data.getString("customer_name"),
                                            data.getString("phone"),
                                            data.getString("amount_person"),
                                            data.getString("address"),
                                            data.getString("total_price"),
                                            data.getString("payment_proof"),
                                            status_booking
                                    ));
                                }

                                BookingAdapter adapter = new BookingAdapter(getContext(), dataBooking);
                                recyclerView.setAdapter(adapter);
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

}