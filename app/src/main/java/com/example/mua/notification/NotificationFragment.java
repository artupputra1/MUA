package com.example.mua.notification;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;
import com.example.mua.booking.Booking;
import com.example.mua.booking.BookingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    private static final String TAG = "NotificationFragment";
    private List<Notification> dataNotification;
    private RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String id_user;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_notification, container, false);
        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");
        recyclerView = fragmentView.findViewById(R.id.rvNotification);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataNotification = new ArrayList<>();
        getData();
        return fragmentView;
    }

    public void getData(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/get_notification.php")
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
                                    dataNotification.add(new Notification(
                                            data.getString("id"),
                                            data.getString("user_id"),
                                            data.getString("user_id"),
                                            data.getString("title"),
                                            data.getString("content"),
                                            data.getString("date")
                                    ));
                                }

                                NotificationAdapter adapter = new NotificationAdapter(getContext(), dataNotification);
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