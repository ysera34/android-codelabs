package org.inframincer.notificationscheduler

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.NotificationCompat

private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"

class NotificationJobService : JobService() {

    private fun createNotificationChannel(): NotificationManager {
        return (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).run {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val notificationChannel = NotificationChannel(
                    PRIMARY_CHANNEL_ID, "Job Service notification", NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    enableLights(true)
                    lightColor = Color.RED
                    enableVibration(true)
                    description = "Notifications from Job Service"
                }
                createNotificationChannel(notificationChannel)
            }
            this
        }
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        val notifyManager by lazy { createNotificationChannel() }
        val contentPendingIntent = PendingIntent.getActivity(
            this@NotificationJobService,
            0,
            Intent(this@NotificationJobService, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(this@NotificationJobService, PRIMARY_CHANNEL_ID).apply {
            setContentTitle("Job Service")
            setContentText("Your Job ran to completion!")
            setContentIntent(contentPendingIntent)
            setSmallIcon(R.drawable.ic_job_running)
            priority = NotificationCompat.PRIORITY_HIGH
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setAutoCancel(true)
        }

        notifyManager.notify(0, notificationBuilder.build())
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }
}
