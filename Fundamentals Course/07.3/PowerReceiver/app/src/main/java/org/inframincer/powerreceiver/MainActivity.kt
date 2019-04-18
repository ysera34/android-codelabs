package org.inframincer.powerreceiver

import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.*

const val ACTION_CUSTOM_BROADCAST = "${BuildConfig.APPLICATION_ID}.ACTION_CUSTOM_BROADCAST"

class MainActivity : AppCompatActivity() {

    private val receiver = CustomReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }

        registerReceiver(receiver, filter)

        sendBroadcastButton.setOnClickListener { sendCustomBroadcast() }
        LocalBroadcastManager.getInstance(this@MainActivity)
            .registerReceiver(receiver, IntentFilter(ACTION_CUSTOM_BROADCAST))
    }

    private fun sendCustomBroadcast() {
        val customBroadcastReceiver = Intent(ACTION_CUSTOM_BROADCAST)
        LocalBroadcastManager.getInstance(this@MainActivity).sendBroadcast(customBroadcastReceiver)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        LocalBroadcastManager.getInstance(this@MainActivity)
            .unregisterReceiver(receiver)
        super.onDestroy()
    }
}
