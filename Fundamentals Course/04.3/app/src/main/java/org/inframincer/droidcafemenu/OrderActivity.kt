package org.inframincer.droidcafemenu

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import kotlinx.android.synthetic.main.activity_order.*
import org.jetbrains.anko.toast

const val EXTRA_MESSAGE = "com.example.android.droidcafe.extra.MESSAGE"

class OrderActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private val TAG = OrderActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val intent = intent
        val message = "Order: ${intent.getStringExtra(EXTRA_MESSAGE)}"
        order_textview.text = message

        label_spinner.onItemSelectedListener = this@OrderActivity

        // Create ArrayAdapter using the string array and default spinner layout.
        val labelAdapter = ArrayAdapter.createFromResource(
            applicationContext, R.array.labels_array, android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        label_spinner.adapter = labelAdapter

        send_phone_text.setOnEditorActionListener { _, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                dialNumber()
                handled = true
            }
            handled
        }

        val checkBoxes = mutableMapOf(
            check_box_1 to getString(R.string.chocolate_syrup),
            check_box_2 to getString(R.string.sprinkles),
            check_box_3 to getString(R.string.crushed_nuts),
            check_box_4 to getString(R.string.cherries),
            check_box_5 to getString(R.string.orio_cookie_crumbles)
        )

        checkBoxes.keys.map {
            it.setOnClickListener {
                val checkedBoxMessages = mutableListOf<String>()
                checkBoxes.keys.map {
                    if (it.isChecked) checkedBoxMessages.add(checkBoxes[it] ?: "")
                }
                toast("Toppings: ${checkedBoxMessages.joinToString(" ")}")
            }
        }
    }

    private fun dialNumber() {
//        val sendPhoneText = send_phone_text.text.toString()
//        makeCall(sendPhoneText)

        val sendPhoneText = "tel:${send_phone_text.text.toString()}"
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse(sendPhoneText)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            Log.d(TAG, "Can't handle this!")
        }
    }

    fun onRadioButtonClicked(view: View) {
        val checked = (view as RadioButton).isChecked
        when (view.id) {
            R.id.sameday -> if (checked) toast(getString(R.string.same_day_messenger_service))
            R.id.nextday -> if (checked) toast(getString(R.string.next_day_ground_delivery))
            R.id.pickup -> if (checked) toast(getString(R.string.pick_up))
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val spinnerLabel = parent?.let {
            it.getItemAtPosition(position).toString()
        } ?: ""
        toast(spinnerLabel)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
