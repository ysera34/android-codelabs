package org.inframincer.twoactivities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

const val EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE"
const val TEXT_REQUEST = 1

class MainActivity : AppCompatActivity() {

    private val LOG_TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate()")
        setContentView(R.layout.activity_main)
    }

    fun launchSecondActivity(view: View) {
        Log.d(LOG_TAG, "Button clicked!")
        val message = editText_main.text.toString()

        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
//        startActivity(intent)
        startActivityForResult(intent, TEXT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val reply = data?.getStringExtra(EXTRA_REPLY) ?: ""
                text_header_reply.visibility = View.VISIBLE
                text_message_reply.text = reply
                text_message_reply.visibility = View.VISIBLE
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy()")
    }
}
