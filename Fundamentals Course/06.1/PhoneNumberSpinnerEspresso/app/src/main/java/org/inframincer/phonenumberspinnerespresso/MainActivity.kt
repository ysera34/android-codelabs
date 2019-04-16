package org.inframincer.phonenumberspinnerespresso

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val log = AnkoLogger(this.javaClass)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        labelSpinner.onItemSelectedListener = this@MainActivity

        val labelAdapter = ArrayAdapter.createFromResource(
            applicationContext, R.array.labels_array, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        labelSpinner.adapter = labelAdapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinnerLabel = parent?.let {
            it.getItemAtPosition(position).toString()
        } ?: ""

        val phoneNumber = "${mainEditText.text} - $spinnerLabel"
        toast(phoneNumber)
        phoneNumberTextView.text = phoneNumber
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        log.debug("onNothingSelected: ")
    }
}
