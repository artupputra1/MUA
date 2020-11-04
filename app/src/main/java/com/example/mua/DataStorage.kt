package com.example.mua

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class DataStorage {
    internal lateinit var savedSession: SharedPreferences
    private val KEY: String = ""


    fun saveFcmToken(context: Context, tokenId: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("FCM_TOKEN_ID", tokenId)
        editor.commit()
    }


    fun getFcmToken(context: Context): String? {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("FCM_TOKEN_ID", "")
    }

    fun saveId(context: Context, id: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("id", id)
        editor.commit()
    }


    fun getId(context: Context): String? {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("id", "")
    }

    fun saveNoPesanan(context: Context, no_pesanan: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("no_pesanan", no_pesanan)
        editor.commit()
    }


    fun getNoPesanan(context: Context): String? {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("no_pesanan", "")
    }

    fun saveNoKamar(context: Context, no_kamar: String) {
        // TODO Auto-generated method stub
        val editor = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit()
        editor.putString("no_kamar", no_kamar)
        editor.commit()
    }


    fun getNoKamar(context: Context): String? {
        savedSession = context.getSharedPreferences(KEY, Activity.MODE_PRIVATE)
        return savedSession.getString("no_kamar", "")
    }

}