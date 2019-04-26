package org.inframincer.roomwords

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast

const val NEW_WORD_ACTIVITY_REQUEST_CODE = 0

class MainActivity : AppCompatActivity() {

    lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val wordAdapter = WordListAdapter(this@MainActivity)
        recyclerview.adapter = wordAdapter
        recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)

        wordViewModel = ViewModelProviders.of(this@MainActivity).get(WordViewModel::class.java)
        wordViewModel.allWords?.observe(
            this@MainActivity,
            Observer<List<Word>> { words ->
                wordAdapter.words = words
            }
        )

        fab.setOnClickListener {
            startActivityForResult<NewWordActivity>(NEW_WORD_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                val word = Word(word = data.getStringExtra(EXTRA_REPLY))
                wordViewModel.insert(word)
            }
        } else {
            toast(R.string.empty_not_saved)
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
