package com.example.mua.booking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.example.mua.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UploadTransactionActivity extends AppCompatActivity {

    String TAG = "UploadTransactionActivity";
    private int GALLERY = 1, CAMERA = 2;
    ImageView paymentproof;
    Bitmap FixBitmap;
    String total_price, booking_id;
    TextView tv_total, tv_datelimit;
    Button upload, save;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_transaction);

        ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Upload Bukti Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init_view();

        booking_id = getIntent().getStringExtra("booking_id");
        paymentproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
        get_booking();
    }

    public void get_booking(){
        progressDialog = ProgressDialog.show(UploadTransactionActivity.this,"Proses","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.get("http://belajarkoding.xyz/mua/user/get_upload.php")
                .addQueryParameter("id", booking_id)
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
                                    int dp = (Integer.parseInt(data.getString("total_price")))/2;
                                    tv_total.setText(new String(String.valueOf(dp)));
                                    tv_datelimit.setText("Batas Waktu Pembayaran : " + data.getString("date_limit") + " " + data.getString("time_limit"));
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

    void init_view() {
        paymentproof = findViewById(R.id.ivPaymentProof);
        tv_total = findViewById(R.id.tvTotal);
        upload = findViewById(R.id.btUpload);
        save = findViewById(R.id.btSave);
        tv_datelimit = findViewById(R.id.tvDateLimit);
    }

    private void upload() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FixBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        progressDialog = ProgressDialog.show(UploadTransactionActivity.this,"Proses Upload","Tunggu Sebentar. . .",false,false);
        AndroidNetworking.post("http://belajarkoding.xyz/mua/user/upload_payment.php")
                .addBodyParameter("payment_proof",encoded)
                .addBodyParameter("booking_id",booking_id)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if (response.get("success").toString().equals("1")) {
                                Log.d(TAG, "onResponse: " + response);
                                Toast.makeText(getApplicationContext() ,"Proses Upload Berhasil", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UploadTransactionActivity.this, MainActivity.class);
                                finish();
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext() ,"Proses Upload Gagal", Toast.LENGTH_LONG).show();
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
                    // String path = saveImage(bitmap);
                    //Toast.makeText(Camera.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    paymentproof.setImageBitmap(FixBitmap);
                    //UploadImageOnServerButton.setVisibility(View.VISIBLE);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UploadTransactionActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
            save.setEnabled(true);

        } else if (requestCode == CAMERA) {
            FixBitmap = (Bitmap) data.getExtras().get("data");
            paymentproof.setImageBitmap(FixBitmap);
            save.setEnabled(true);
            //UploadImageOnServerButton.setVisibility(View.VISIBLE);
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

}