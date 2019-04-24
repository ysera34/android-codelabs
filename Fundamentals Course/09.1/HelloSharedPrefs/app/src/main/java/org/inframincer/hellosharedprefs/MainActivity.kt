package org.inframincer.hellosharedprefs

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

private const val COUNT_KEY = "count"
private const val COLOR_KEY = "color"

class MainActivity : AppCompatActivity() {

    private var count = 0
    private var color = 0
    private val sharedPrefFile = "org.inframincer.hellosharedprefs"
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        color = ContextCompat.getColor(this@MainActivity, R.color.default_background)
        preferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        count = preferences.getInt(COUNT_KEY, 0)
        color = preferences.getInt(COLOR_KEY, color)
        count_textview.apply {
            text = String.format("%s", count)
            setBackgroundColor(color)
        }

        savedInstanceState?.let {
            count = it.getInt(COUNT_KEY)
            count_textview.text = String.format("%s", count)

            color = it.getInt(COLOR_KEY)
            count_textview.setBackgroundColor(color)
        }

        black_background_button.setOnClickListener { changeBackground(it) }
        red_background_button.setOnClickListener { changeBackground(it) }
        blue_background_button.setOnClickListener { changeBackground(it) }
        green_background_button.setOnClickListener { changeBackground(it) }
        count_button.setOnClickListener { countUp() }
        reset_button.setOnClickListener { reset() }
    }

    override fun onPause() {
        super.onPause()
        preferences.edit().apply {
            putInt(COUNT_KEY, count)
            putInt(COLOR_KEY, color)
            apply()
        }
    }

    private fun changeBackground(view: View) {
        val color = (view.background as ColorDrawable).color
        count_textview.setBackgroundColor(color)
        this.color = color
    }

    private fun countUp() {
        this.count++
        count_textview.text = String.format("%s", count)
    }

    private fun reset() {
        count = 0
        count_textview.text = String.format("%s", count)

        color = ContextCompat.getColor(this@MainActivity, R.color.default_background)
        count_textview.setBackgroundColor(color)

        preferences.edit().apply {
            clear()
            apply()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            it.apply {
                putInt(COUNT_KEY, count)
                putInt(COLOR_KEY, color)
            }
        }
    }
}
