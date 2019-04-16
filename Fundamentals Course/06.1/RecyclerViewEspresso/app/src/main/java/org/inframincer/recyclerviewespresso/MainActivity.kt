package org.inframincer.recyclerviewespresso

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val words = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        for (i in 0..20) {
            words.add("Word $i")
        }

        // Create an adapter and supply the data to be displayed.
        val adapter = WordListAdapter(this@MainActivity, words)
        // Connect the adapter with RecyclerView.
        recycler_view.adapter = adapter
        // Give the RecyclerView a default layout manager.
        recycler_view.layoutManager = LinearLayoutManager(this@MainActivity)

        fab.setOnClickListener { _ ->
            // Add a new word to the words.
            words.add("+ Word ${words.size}")
            // Notify the adapter, that the data has changed.
            // (recycler_view.adapter as WordListAdapter).notifyItemInserted(words.size)
            // adapter.notifyItemInserted(words.size)
            adapter.notifyDataSetChanged()
            // Scroll to the bottom.
            recycler_view.smoothScrollToPosition(words.size)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}