package org.inframincer.powerreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.jetbrains.anko.toast
import java.lang.IllegalArgumentException

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val intentAction = intent.action
        val toastMessage = intentAction?.let {
            when (it) {
                Intent.ACTION_POWER_CONNECTED -> "Power connected!"
                Intent.ACTION_POWER_DISCONNECTED -> "Power disconnected!"
                ACTION_CUSTOM_BROADCAST -> "Custom Broadcast Received!"
                else -> "unknown intent action"
            }
        } ?: throw IllegalArgumentException()
        context.toast(toastMessage)
    }
}
