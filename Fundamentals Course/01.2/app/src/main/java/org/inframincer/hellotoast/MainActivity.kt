package org.inframincer.hellotoast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toast_button.setOnClickListener {
            toast(getString(R.string.toast_message))
        }
        count_button.setOnClickListener {
            ++count
            count_text_view.text = count.toString()
        }
    }
}
