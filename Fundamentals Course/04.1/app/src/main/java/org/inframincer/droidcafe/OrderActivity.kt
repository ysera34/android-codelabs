package org.inframincer.droidcafe

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_order.*

const val EXTRA_MESSAGE = "com.example.android.droidcafe.extra.MESSAGE"

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val intent = intent
        val message = "Order: ${intent.getStringExtra(EXTRA_MESSAGE)}"
        order_textview.text = message
    }
}
