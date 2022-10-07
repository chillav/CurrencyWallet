package com.krasovitova.currencywallet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber


class MyFireBaseMessagingServer : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        // TODO(developer): Handle FCM messages here.

        Timber.tag(TAG).d("From: %s", remoteMessage.from)
        if (remoteMessage.data.isNotEmpty()) {
            Timber.tag(TAG).d("Message data payload: %s", remoteMessage.data)
            if (true) {
                scheduleJob()
            } else {
                handleNow()
            }
        }

        if (remoteMessage.notification != null) {
            Timber.tag(TAG).d("Message Notification Body: %s", remoteMessage.notification!!.body)
        }
    }

    override fun onNewToken(token: String) {
        Timber.tag(TAG).d("Refreshed token: %s", token)
        sendRegistrationToServer(token)
    }

    private fun scheduleJob() {
//        val work: OneTimeWorkRequest = ImageRequest.Builder(MyWorker::class.java)
//            .build()
//        WorkManager.getInstance().beginWith(work).enqueue()
    }

    private fun handleNow() {
        Timber.tag(TAG).d("Short lived task is done.")
    }

    private fun sendRegistrationToServer(token: String) {
        // TODO: Implement this method to send token to your app server.
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {

        val intent = Intent(this, MainActivity::class.java)
        //TODO(check multiple pushes)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id);
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(remoteMessage.notification?.title)
            .setContentText(remoteMessage.notification?.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}
