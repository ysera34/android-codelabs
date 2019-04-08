package org.inframincer.droidcafemenu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var orderMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity<OrderActivity>(
                EXTRA_MESSAGE to orderMessage
            )
        }

        donut.setOnClickListener {
            orderMessage = getString(R.string.donut_order_message)
            toast(orderMessage)
        }
        ice_cream.setOnClickListener {
            orderMessage = getString(R.string.ice_cream_order_message)
            toast(orderMessage)
        }
        froyo.setOnClickListener {
            orderMessage = getString(R.string.froyo_order_message)
            toast(orderMessage)
        }

        registerForContextMenu(donut_description)
        registerForContextMenu(ice_cream_description)
        registerForContextMenu(froyo_description)

        alert_button.setOnClickListener {
            alert("Click OK to continue, or Cancel to stop:", "Alert") {
                okButton { toast("Pressed OK") }
                cancelButton { toast("Pressed Cancel") }
            }.show()
        }

        date_button.setOnClickListener {
            DatePickerFragment().apply {
                show(supportFragmentManager, "datePicker")
            }
        }
        time_button.setOnClickListener {
            TimePickerFragment().apply {
                show(supportFragmentManager, "timePicker")
            }
        }
    }

    fun processDatePickerResult(year: Int, month: Int, day: Int) {
//        var dateString = "$year / ${month + 1} / $day"
//        val dateFormat = SimpleDateFormat( "yyyy / MM / dd", Locale.US)
//        val date = dateFormat.parse(dateString)
//        dateString = dateFormat.format(date)
        toast("Date: $year / ${month + 1} / $day")
    }

    fun processTimePickerResult(hour: Int, minute: Int) {
        toast("TIme: $hour:$minute")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.action_order -> {
                toast(getString(R.string.action_order_message))
                startActivity<OrderActivity>(
                    EXTRA_MESSAGE to orderMessage
                )
                true
            }
            R.id.action_status -> {
                toast(getString(R.string.action_status_message))
                true
            }
            R.id.action_favorites -> {
                toast(getString(R.string.action_favorites_message))
                true
            }
            R.id.action_contact -> {
                toast(getString(R.string.action_contact_message))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?) =
        when (item?.itemId) {
            R.id.context_edit -> {
                toast("Edit choice clicked.")
                true
            }
            R.id.context_share -> {
                toast("Share choice clicked.")
                true
            }
            R.id.context_delete -> {
                toast("Delete choice clicked.")
                true
            }
            else -> super.onContextItemSelected(item)
        }
}
