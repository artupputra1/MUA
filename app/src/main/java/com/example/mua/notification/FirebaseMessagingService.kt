package com.example.mua.notification;

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.mua.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseMessageService"


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)

        if(remoteMessage!!.data != null){
            sendNotification(remoteMessage)
        }

    }

    private fun sendNotification(remoteMessage: RemoteMessage?) {

        val title = remoteMessage!!.data["title"]
        val content = remoteMessage!!.data["content"]

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId: String = getString(R.string.app_name);

        @RequiresApi(Build.VERSION_CODES.O)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, "Notifikasi", NotificationManager.IMPORTANCE_MAX)
            notificationChannel.description = "Kotlin Notif"
            notificationChannel.enableLights(true)
            notificationChannel.vibrationPattern = longArrayOf(0,1000,500,500)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)

        notificationBuilder.setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.notification_icon_background)
            .setContentTitle(title)
            .setContentText(content)
        notificationManager.notify(1, notificationBuilder.build())
    }
}