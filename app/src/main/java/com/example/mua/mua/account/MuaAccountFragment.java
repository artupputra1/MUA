package com.example.mua.mua.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.MainActivity;
import com.example.mua.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MuaAccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";
    TextView tv_owner, tv_name, tv_address, tv_since, tv_jobdone, tv_muasince, tv_note;
    Button bt_user, bt_edit, bt_report;
    ImageView profil;
    ProgressDialog progressDialog;
    String provider_id;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_mua_account, container, false);
        tv_owner = fragmentView.findViewById(R.id.tvOwner);
        tv_name = fragmentView.findViewById(R.id.tvMua);
        tv_address = fragmentView.findViewById(R.id.tvAddress);
        tv_since = fragmentView.findViewById(R.id.tvMemberSince);
        tv_jobdone = fragmentView.findViewById(R.id.tvJobDone);
        tv_muasince = fragmentView.findViewById(R.id.tvMuaSince);
        tv_note = fragmentView.findViewById(R.id.tvInfo);
        bt_user = fragmentView.findViewById(R.id.btUser);
        bt_edit = fragmentView.findViewById(R.id.btEdit);
        bt_report = fragmentView.findViewById(R.id.btReport);
        profil = fragmentView.findViewById(R.id.ivProfile);

        sharedpreferences = this.getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        provider_id = sharedpreferences.getString("id_provider", "");

        get_provider();

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditMuaActivity.class);
                startActivity(intent);
            }
        });
        bt_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        bt_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MuaReportActivity.class);
                startActivity(intent);
            }
        });
        return fragmentView;
    }

    public void get_provider(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/provider/get_provider.php")
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
                                    Picasso.get()
                                            .load("http://belajarkoding.xyz/mua/upload/profile/"+data.getString("image"))
                                            .fit()
                                            .into(profil);
                                    tv_owner.setText(data.getString("owner"));
                                    tv_name.setText(data.getString("name_business"));
                                    tv_address.setText(data.getString("address"));
                                    tv_since.setText(data.getString("member_since"));
                                    tv_jobdone.setText(data.getString("Job_done"));
                                    tv_muasince.setText(data.getString("mua_since"));
                                    tv_note.setText(data.getString("info"));

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

}