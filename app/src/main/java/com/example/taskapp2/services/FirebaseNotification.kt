package com.example.taskapp2.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.taskapp2.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseNotification :FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("olo", "onMessageReceived: "+ message.notification?.title )
        Log.e("olo", "onMessageReceived: "+ message.notification?.body )
        sendNotification(message)
        super.onMessageReceived(message)
    }
    private fun sendNotification(remoteMessage: RemoteMessage){
       val builderNotification = NotificationCompat.Builder(this,"task_channelId")
        builderNotification.setSmallIcon(R.mipmap.ic_launcher)
        builderNotification.setContentTitle(remoteMessage.notification?.title)
        builderNotification.setContentText(remoteMessage.notification?.body)

        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel("task_channelId","Channel human readable title",NotificationManager.IMPORTANCE_DEFAULT)
        notifyManager.createNotificationChannel(channel)
        notifyManager.notify(1,builderNotification.build())
    }

}