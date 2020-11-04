package com.example.mua.mua;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;
import com.example.mua.account.EditProfileActivity;
import com.example.mua.chat.MuaListChatActivity;
import com.example.mua.mua.notif.MuaNotifActivity;
import com.example.mua.provider.DetailProviderActivity;
import com.example.mua.provider.detail_provider.ServiceProviderAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MuaHomeFragment extends Fragment {

    private static final String TAG = "BookingFragment";
    private List<MuaBooking> dataBooking;
    private RecyclerView recyclerView;
    TextView tv_mua;
    ImageView chat, notif, profil;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String provider_id;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_mua_home, container, false);

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        provider_id = sharedpreferences.getString("id_provider", "");

        chat = fragmentView.findViewById(R.id.ivMail);
        notif = fragmentView.findViewById(R.id.ivNotification);
        tv_mua = fragmentView.findViewById(R.id.tvMua);
        profil = fragmentView.findViewById(R.id.ivProfile);

        recyclerView = fragmentView.findViewById(R.id.rvBooking);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataBooking = new ArrayList<>();
        getData();
        getProvider();

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MuaListChatActivity.class);
                startActivity(intent);
            }
        });

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MuaNotifActivity.class);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public void getProvider(){
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/detail_provider.php")
                .addQueryParameter("provider_id", provider_id)
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
                                    Picasso.get()
                                            .load("http://belajarkoding.xyz/mua/upload/profile/"+data.getString("image"))
                                            .fit()
                                            .into(profil);
                                    tv_mua.setText(data.getString("name_business"));
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

    public void getData(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_booking.php")
                .addQueryParameter("provider_id", provider_id)
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
                                    dataBooking.add(new MuaBooking(
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
                                            data.getString("status")
                                    ));
                                }

                                MuaBookingAdapter adapter = new MuaBookingAdapter(getContext(), dataBooking);
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