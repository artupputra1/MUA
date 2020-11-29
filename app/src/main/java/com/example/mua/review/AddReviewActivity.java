package com.example.mua.review;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.mua.LoginActivity;
import com.example.mua.MainActivity;
import com.example.mua.OrderSummaryActivity;
import com.example.mua.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddReviewActivity extends AppCompatActivity {

    private static final String TAG = "AddReviewActivity";
    EditText ed_review;
    Button bt_save, select_image;
    RatingBar rating;
    ProgressDialog progressDialog;
    ImageView iv_review;
    String service_id, id_user, order_id;
    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "mua";
    Bitmap FixBitmap;
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        init_view();

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        id_user = sharedpreferences.getString("id", "");
        service_id = getIntent().getStringExtra("service_id");
        order_id = getIntent().getStringExtra("order_id");
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_review();
            }
        });
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        get_review();

    }

    public void init_view(){
        ed_review = findViewById(R.id.edReview);
        bt_save = findViewById(R.id.btSave);
        select_image = findViewById(R.id.btImage);
        iv_review = findViewById(R.id.ivReview);
        rating =(RatingBar)findViewById(R.id.rating);
    }

    public void save_review(){

        FixBitmap = ((BitmapDrawable)iv_review.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        progressDialog = ProgressDialog.show(AddReviewActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/add_review.php")
                .addBodyParameter("service_id", service_id)
                .addBodyParameter("order_id", order_id)
                .addBodyParameter("user_id", id_user)
                .addBodyParameter("review", ed_review.getText().toString())
                .addBodyParameter("rating", String.valueOf(rating.getRating()))
                .addBodyParameter("image", encoded)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            Toast.makeText(AddReviewActivity.this,response.getString("message"),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(AddReviewActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
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

    public void get_review(){
        String token = sharedpreferences.getString("fcm_id", "");
        // Menampilkan progress dialog
        progressDialog = ProgressDialog.show(AddReviewActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        // Menagkses URL http://belajarkoding.xyz/mua/user/cek_review.php dengan mengirim data order_id dan user_id
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/cek_review.php")
                .addBodyParameter("order_id",order_id)
                .addBodyParameter("user_id",id_user)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    // Ketika ada response
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            // Ketika success
                            if (response.get("success").toString().equals("1")) {
                                ed_review.setText(response.get("review").toString());
                                ed_review.setEnabled(false);
                                rating.setRating(Float.parseFloat(response.get("rating").toString()));
                                rating.setEnabled(false);
                                Picasso.get()
                                        .load("http://belajarkoding.xyz/mua/upload/review/"+response.get("image").toString())
                                        .fit()
                                        .into(iv_review);
                                select_image.setVisibility(View.GONE);
                                bt_save.setVisibility(View.GONE);

                            }
                            // Jika gagal
                            else {

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

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    FixBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    iv_review.setImageBitmap(FixBitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            iv_review.setImageBitmap(FixBitmap);

        }
    }

}