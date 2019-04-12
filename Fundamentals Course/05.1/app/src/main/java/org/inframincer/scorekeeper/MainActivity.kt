package org.inframincer.scorekeeper

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

private const val STATE_SCORE_1 = "Team 1 Score"
private const val STATE_SCORE_2 = "Team 2 Score"
private const val BATTERY_LEVEL = "Battery Level"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // member variables for holding the score
    private var score1 = 0
    private var score2 = 0
    private var batteryLevel = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.apply {
            score1 = getInt(STATE_SCORE_1)
            score2 = getInt(STATE_SCORE_2)
            batteryLevel = getInt(BATTERY_LEVEL)

            score1TextView.text = score1.toString()
            score2TextView.text = score2.toString()
            batteryImageView.setImageLevel(batteryLevel)
        }

        decreaseTeam1ImageButton.setOnClickListener(this)
        increaseTeam1ImageButton.setOnClickListener(this)
        decreaseTeam2ImageButton.setOnClickListener(this)
        increaseTeam2ImageButton.setOnClickListener(this)
        decreaseBatteryImageButton.setOnClickListener(this)
        increaseBatteryImageButton.setOnClickListener(this)
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
            R.id.decreaseBatteryImageButton -> {
                changeLevel(batteryImageView.drawable.level, BatteryOperationMethod.DECREASE)
            }
            R.id.increaseBatteryImageButton -> {
                changeLevel(batteryImageView.drawable.level, BatteryOperationMethod.INCREASE)
            }
        }
    }

    enum class BatteryOperationMethod {
        DECREASE, INCREASE
    }

    private fun changeLevel(level: Int, operationMethod: BatteryOperationMethod) {
        val batteryLevels = listOf(20, 30, 50, 60, 80, 90, 100)
        val currentLevelIndex = batteryLevels.indexOf(level)

        when (operationMethod) {
            BatteryOperationMethod.DECREASE -> {
                if (currentLevelIndex > 0) {
                    batteryImageView.setImageLevel(batteryLevels[currentLevelIndex - 1])
                } else {
                    toast("cannot decrease level")
                }
            }
            BatteryOperationMethod.INCREASE -> {
                if (currentLevelIndex < batteryLevels.size - 1) {
                    batteryImageView.setImageLevel(batteryLevels[currentLevelIndex + 1])
                } else {
                    toast("cannot increase level")
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // Change the label of the menu based on the state of the app.
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu?.let {
                it.findItem(R.id.night_mode).title = getString(R.string.day_mode)
            }
        } else {
            menu?.let {
                it.findItem(R.id.night_mode).title = getString(R.string.night_mode)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //Check if the correct item was clicked
        when (item?.itemId) {
            R.id.night_mode -> {
                // Get the night mode state of the app.
                val nightMode = AppCompatDelegate.getDefaultNightMode()
                // Set the theme mode for the restarted activity
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                // Recreate the activity for the theme change to take effect.
                recreate()
                return true
            }
            else -> return true
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        // Save the scores
        outState?.apply {
            putInt(STATE_SCORE_1, score1)
            putInt(STATE_SCORE_2, score2)
            putInt(BATTERY_LEVEL, batteryImageView.drawable.level)
        }
        super.onSaveInstanceState(outState)
    }
}
