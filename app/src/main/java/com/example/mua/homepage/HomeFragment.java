package com.example.mua.homepage;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mua.LoginActivity;
import com.example.mua.MainActivity;
import com.example.mua.R;
import com.example.mua.SearchActivity;
import com.example.mua.chat.ListChatActivity;
import com.example.mua.provider.ProviderActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    ProgressDialog progressDialog;
    LinearLayout make_up, hair_do, hena_art, nail_art;
    TextView tv_name;
    EditText ed_search;
    Button bt_search;
    ImageView iv_chat, profil;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    String id_user;
    String name_user;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        make_up = fragmentView.findViewById(R.id.makeUp);
        hair_do = fragmentView.findViewById(R.id.hairDo);
        hena_art = fragmentView.findViewById(R.id.henaArt);
        nail_art = fragmentView.findViewById(R.id.nailArt);
        iv_chat = fragmentView.findViewById(R.id.ivChat);
        tv_name = fragmentView.findViewById(R.id.tvName);
        ed_search = fragmentView.findViewById(R.id.edSearch);
        bt_search = fragmentView.findViewById(R.id.btSearch);
        profil = fragmentView.findViewById(R.id.ivProfile);

        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");
        name_user = sharedpreferences.getString("name", "");

        tv_name.setText(name_user);

        get_user();

        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("keyword", ed_search.getText().toString());
                startActivity(intent);
            }
        });

        iv_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ListChatActivity.class);
                intent.putExtra("user_id", id_user);
                startActivity(intent);
            }
        });

        make_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProviderActivity.class);
                intent.putExtra("category_id", "1");
                startActivity(intent);
            }
        });

        hair_do.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProviderActivity.class);
                intent.putExtra("category_id", "2");
                startActivity(intent);
            }
        });

        hena_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProviderActivity.class);
                intent.putExtra("category_id", "4");
                startActivity(intent);
            }
        });

        nail_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProviderActivity.class);
                intent.putExtra("category_id", "3");
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public void get_user(){
        progressDialog = ProgressDialog.show(getActivity(),"Proses Login","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/get_user.php")
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
                                    Picasso.get()
                                            .load("http://belajarkoding.xyz/mua/upload/profile/"+data.getString("image"))
                                            .fit()
                                            .into(profil);
                                    //tv_name.setText(data.getString("name"));
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