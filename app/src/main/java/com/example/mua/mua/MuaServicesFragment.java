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
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MuaServicesFragment extends Fragment {


    private static final String TAG = "ServiceFragment";
    private List<MuaServiceProvider> dataProvider;
    private RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String provider_id;
    ProgressDialog progressDialog;
    Button add_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_mua_services, container, false);
        add_service = fragmentView.findViewById(R.id.btAddService);

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectCategoryActivity.class);
                startActivity(intent);
            }
        });

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        provider_id = sharedpreferences.getString("id_provider", "");

        recyclerView = fragmentView.findViewById(R.id.rvServices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataProvider = new ArrayList<>();
        getData();

        return fragmentView;
    }

    public void getData(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_services.php")
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
                                    dataProvider.add(new MuaServiceProvider(
                                            data.getString("id"),
                                            data.getString("service"),
                                            data.getString("price"),
                                            data.getString("duration"),
                                            data.getString("information")
                                    ));
                                }

                                MuaServiceProviderAdapter adapter = new MuaServiceProviderAdapter(getContext(), dataProvider);
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