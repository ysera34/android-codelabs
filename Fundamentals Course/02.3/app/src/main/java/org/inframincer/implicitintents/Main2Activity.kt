package org.inframincer.implicitintents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val intent = intent
        val uri = intent.data

        val uriString = uri?.let {
            "URI: $uri"
        } ?: ""

        text_uri_message.text = uriString
    }
}
