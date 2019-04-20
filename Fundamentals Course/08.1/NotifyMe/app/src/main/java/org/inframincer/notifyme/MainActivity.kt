package org.inframincer.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
private const val PRIMARY_CHANNEL_NAME = "Mascot Notification"
private const val PRIMARY_CHANNEL_DESCRIPTION = "Notification from Mascot"
private const val ACTION_UPDATE_NOTIFICATION = "ACTION_UPDATE_NOTIFICATION"

private const val NOTIFICATION_ID = 0

class MainActivity : AppCompatActivity() {

    private val notifyManager by lazy { createNotificationChannel() }
    private val notificationReceiver = NotificationReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(notificationReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION))
        notifyButton.setOnClickListener { sendNotification() }
        updateButton.setOnClickListener { updateNotification() }
        cancelButton.setOnClickListener { cancelNotification() }
        setNotificationButtonState(isNotifyEnabled = true, isUpdateEnabled = false, isCancelEnabled = false)
    }

    private fun sendNotification() {
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(
            this@MainActivity, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)
        val notifyBuilder = getNotificationBuilder().apply {
            addAction(R.drawable.ic_update, getString(R.string.update_notification_title), updatePendingIntent) }
        notifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(isNotifyEnabled = false, isUpdateEnabled = true, isCancelEnabled = true)
    }

    private fun createNotificationChannel(): NotificationManager {

        val notifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = PRIMARY_CHANNEL_DESCRIPTION
            }
            notifyManager.createNotificationChannel(notificationChannel)
        }
        return notifyManager
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val notificationIntent = Intent(this@MainActivity, MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(
            this@MainActivity, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        return NotificationCompat.Builder(this@MainActivity, PRIMARY_CHANNEL_ID).apply {
            setContentTitle(getString(R.string.content_title))
            setContentText(getString(R.string.content_text))
            setSmallIcon(R.drawable.ic_android)
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_HIGH
            setDefaults(NotificationCompat.DEFAULT_ALL)
        }
    }

    fun updateNotification() {
        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        val notifyBuilder = getNotificationBuilder()
        notifyBuilder.apply {
            setStyle(NotificationCompat
                .BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle(getString(R.string.updated_content_title)))
        }
        notifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(isNotifyEnabled = false, isUpdateEnabled = false, isCancelEnabled = true)
    }

    private fun cancelNotification() {
        notifyManager.cancel(NOTIFICATION_ID)
        setNotificationButtonState(isNotifyEnabled = true, isUpdateEnabled = false, isCancelEnabled = false)
    }

    private fun setNotificationButtonState(
        isNotifyEnabled: Boolean,
        isUpdateEnabled: Boolean,
        isCancelEnabled: Boolean
    ) {
        notifyButton.isEnabled = isNotifyEnabled
        updateButton.isEnabled = isUpdateEnabled
        cancelButton.isEnabled = isCancelEnabled
    }

    override fun onDestroy() {
        unregisterReceiver(notificationReceiver)
        super.onDestroy()
    }

    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            (context as MainActivity).updateNotification()
        }
    }
}
