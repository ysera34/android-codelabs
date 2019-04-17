package org.inframincer.simpleasynctask

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import kotlin.random.Random

private const val TEXT_STATE = "currentText"

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState?.apply { textView.text = savedInstanceState.getString(TEXT_STATE) }

        button.setOnClickListener {
            textView.text = getString(R.string.napping)
            SimpleAsyncTask(textView).execute()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.apply { putString(TEXT_STATE, textView.text.toString()) }
    }

    class SimpleAsyncTask(textView: TextView) : AsyncTask<Void, Void, String>() {

        private var textView: WeakReference<TextView> = WeakReference(textView)

        override fun doInBackground(vararg params: Void?): String {
            val random = Random
            val number = random.nextInt(11)
            val second = number * 200L
            try {
                Thread.sleep(second)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            return "Awake at last after sleeping for $second milliseconds!"
        }

        override fun onPostExecute(result: String?) {
            textView.get()?.text = result
        }
    }
}
