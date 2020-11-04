package com.example.mua.account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.mua.OrderSummaryActivity;
import com.example.mua.R;
import com.example.mua.RegisterActivity;
import com.example.mua.SearchActivity;
import com.example.mua.mua.MenuMuaActivity;
import com.example.mua.mua.MuaRegisterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountFragment extends Fragment {

    private static final String TAG = "OrderSummary";
    TextView tv_name, tv_email, tv_phone;
    Button bt_edit, bt_mua;
    ImageView profil;
    ProgressDialog progressDialog;
    String id_user;
    String id_mua, name_mua;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentview =  inflater.inflate(R.layout.fragment_account, container, false);
        tv_name = fragmentview.findViewById(R.id.tvName);
        tv_email = fragmentview.findViewById(R.id.tvEmail);
        tv_phone = fragmentview.findViewById(R.id.tvPhone);
        bt_edit = fragmentview.findViewById(R.id.btEdit);
        bt_mua = fragmentview.findViewById(R.id.btMua);
        profil = fragmentview.findViewById(R.id.ivProfile);

        sharedpreferences = getActivity().getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");

        db = FirebaseFirestore.getInstance();

        get_user();

        bt_mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id_mua.equals("0")) {
                    Intent intent = new Intent(getActivity(), MuaRegisterActivity.class);
                    startActivity(intent);
                }
                else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("id_provider", id_mua);
                    editor.commit();

                    add_user_chat(id_mua, name_mua);

                    Intent intent = new Intent(getActivity(), MenuMuaActivity.class);
                    intent.putExtra("id_mua",id_mua);
                    startActivity(intent);
                }
            }
        });

        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return fragmentview;
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
                                    tv_name.setText(data.getString("name"));
                                    tv_email.setText(data.getString("email"));
                                    tv_phone.setText(data.getString("phone"));
                                    id_mua = data.getString("id_mua");
                                    name_mua = data.getString("name_business");

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

    public void add_user_chat(String id, String name) {
        Map<String, Object> city = new HashMap<>();
        city.put("id_mua", id);
        city.put("name_mua", name);

        db.collection("mua").document(id)
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }
}