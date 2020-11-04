package com.example.mua.notification;

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class MyFirebaseInstanceIdService: FirebaseInstanceIdService() {

    var sharedpreferences: SharedPreferences? = null
    val my_shared_preferences = "mua"

    override fun onTokenRefresh() {
        //Mendapatkan Instance dan Memperoleh Token
        val refreshedToken = FirebaseInstanceId.getInstance().token

        //Menampilkan Token pada Log
        Log.d(TAG, "Token Saya : $refreshedToken")
        val sharedPreference =  getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("fcm_id", refreshedToken)
        editor.commit()
        sendRegistrationToServer(refreshedToken)

        //Method berikut ini digunakan untuk memperoleh token dan mennyimpannya ke server aplikasi
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(refreshedToken: String?) {
        //Disini kita biarkan kosong saja
    }
}