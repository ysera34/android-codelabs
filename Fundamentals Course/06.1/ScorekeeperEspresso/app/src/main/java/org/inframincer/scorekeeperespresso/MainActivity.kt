package org.inframincer.scorekeeperespresso

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // member variables for holding the score
    private var score1 = 0
    private var score2 = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        decreaseTeam1ImageButton.setOnClickListener(this)
        increaseTeam1ImageButton.setOnClickListener(this)
        decreaseTeam2ImageButton.setOnClickListener(this)
        increaseTeam2ImageButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.decreaseTeam1ImageButton -> {
                score1--
                score1TextView.text = score1.toString()
            }
            R.id.increaseTeam1ImageButton -> {
                score1++
                score1TextView.text = score1.toString()
            }
            R.id.decreaseTeam2ImageButton -> {
                score2--
                score2TextView.text = score2.toString()
            }
            R.id.increaseTeam2ImageButton -> {
                score2++
                score2TextView.text = score2.toString()
            }
        }
    }
}
