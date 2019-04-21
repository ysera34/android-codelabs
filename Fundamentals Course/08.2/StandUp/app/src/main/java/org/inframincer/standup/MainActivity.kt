package org.inframincer.standup

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    private val notificationManager by lazy { createNotificationManager() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val notifyIntent = Intent(this@MainActivity, AlarmReceiver::class.java)
        val alarmUp = PendingIntent.getBroadcast(this@MainActivity,
            NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null
        alarmToggleButton.isChecked = alarmUp

        val notifyPendingIntent = PendingIntent.getBroadcast(
            this@MainActivity, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        createNotificationChannel()
        alarmToggleButton.setOnCheckedChangeListener { _, isChecked ->
            val toastMessage: String
            if (isChecked) {
//                deliverNotification(this@MainActivity)

                val repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES
                val triggerTime = SystemClock.elapsedRealtime() + repeatInterval

                // If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime, repeatInterval, notifyPendingIntent)

                toastMessage = getString(R.string.check_on_toast_message)
            } else {
                notificationManager.cancelAll()
                alarmManager.cancel(notifyPendingIntent)

                toastMessage = getString(R.string.check_off_toast_message)
            }
            toast(toastMessage)
        }
    }

    private fun createNotificationManager(): NotificationManager {
        return getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID, getString(R.string.notification_channel_name), NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = getString(R.string.notification_channel_description)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
