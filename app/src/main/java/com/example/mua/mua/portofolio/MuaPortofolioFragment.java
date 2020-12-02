package com.example.mua.mua.portofolio;

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

public class MuaPortofolioFragment extends Fragment {

    private static final String TAG = "PortofolioFragment";
    private List<MuaPortofolioProvider> dataPortofolio;
    private RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String provider_id;
    ProgressDialog progressDialog;
    Button add_portofolio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_mua_portofolio, container, false);

        add_portofolio = fragmentView.findViewById(R.id.btAddPortofolio);

        add_portofolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPortofolioActivity.class);
                startActivity(intent);
            }
        });

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        provider_id = sharedpreferences.getString("id_provider", "");

        recyclerView = fragmentView.findViewById(R.id.rvPortofolio);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dataPortofolio = new ArrayList<>();
        getData();

        return fragmentView;
    }

    public void getData(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_portofolio.php")
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
                                    dataPortofolio.add(new MuaPortofolioProvider(
                                            data.getString("id"),
                                            data.getString("provider_id"),
                                            data.getString("link")
                                    ));
                                }

                                MuaPortofolioProviderAdapter adapter = new MuaPortofolioProviderAdapter(getContext(), dataPortofolio);
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