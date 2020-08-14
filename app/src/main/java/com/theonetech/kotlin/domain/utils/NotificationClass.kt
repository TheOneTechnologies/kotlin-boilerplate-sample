package com.theonetech.kotlin.domain.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.theonetech.kotlin.R
import com.theonetech.kotlin.presentation.view.DashboardActivity

/**
 * Created by Mahesh Keshvala on 31,July,2020
 */
class NotificationClass {
    private var notificationManager: NotificationManager? = null
    private var channelName: String = "kotlin_demo_channel"
    private var notContent: String = "Welcome to Demo Application"

    @SuppressLint("InvalidWakeLockTag")
    fun sendNotification(context: Context) {

        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

//      Notification Channel for android Oreo 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            val channel = NotificationChannel(
                channelName,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.setShowBadge(false)
            notificationManager?.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(context, DashboardActivity::class.java)
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pIntent = PendingIntent.getActivity(
            context,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, channelName)
        builder.setContentTitle(context.getString(R.string.notification_title))
            .setStyle(NotificationCompat.BigTextStyle().bigText(notContent))
            .setContentText(notContent)
            .setSmallIcon(R.drawable.ic_app_launcher).setAutoCancel(true)
        val uri = RingtoneManager.getDefaultUri(
            RingtoneManager.TYPE_NOTIFICATION
        )
        builder.setSound(uri)
        builder.notification.flags =
            Notification.FLAG_INSISTENT or Notification.FLAG_AUTO_CANCEL
        builder.setContentIntent(pIntent)
        notificationManager?.notify(2, builder.build())
    }
}