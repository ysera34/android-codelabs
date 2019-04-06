package org.inframincer.hellocompat

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val colorArray = listOf(
        "red",
        "pink",
        "purple",
        "deep_purple",
        "indigo",
        "blue",
        "light_blue",
        "cyan",
        "teal",
        "green",
        "light_green",
        "lime",
        "yellow",
        "amber",
        "orange",
        "deep_orange",
        "brown",
        "grey",
        "blue_grey",
        "black"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // restore saved instance state (the text color)
        if (savedInstanceState != null) {
            hello_textview.setTextColor(savedInstanceState.getInt("color"))
        }

        color_button.setOnClickListener {
//            val random = Random()
//            val colorName = colorArray[random.nextInt(colorArray.size)]
            val colorName = colorArray.shuffled().take(1)[0]
            val colorResourceName = resources.getIdentifier(colorName, "color", applicationContext.packageName)

//            val colorResource = resources.getColor(colorResourceName, applicationContext.theme)
            val colorResource = ContextCompat.getColor(applicationContext, colorResourceName)
            hello_textview.setTextColor(colorResource)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        // save the current text color
        outState?.putInt("color", hello_textview.currentTextColor)
    }
}
