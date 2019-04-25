package org.inframincer.appwithsettings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

const val KEY_PREF_EXAMPLE_SWITCH = "example_switch"

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content,  SettingsFragment())
            .commit()
    }
}
