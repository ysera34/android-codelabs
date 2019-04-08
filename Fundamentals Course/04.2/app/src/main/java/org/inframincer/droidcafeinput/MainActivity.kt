package org.inframincer.droidcafeinput

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    var orderMessage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
//            val intent = Intent(applicationContext, OrderActivity::class.java)
//            startActivity(intent)
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
