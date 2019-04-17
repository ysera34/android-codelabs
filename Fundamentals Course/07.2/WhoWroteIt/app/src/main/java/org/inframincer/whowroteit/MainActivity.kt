package org.inframincer.whowroteit

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v4.content.Loader
import android.support.v4.content.res.TypedArrayUtils.getText
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.lang.IllegalArgumentException

private const val QUERY_STRING = "queryString"
private const val LOADER_ID = 0

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String> {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchButton1.setOnClickListener { searchBooksByAsyncTask() }
        searchButton2.setOnClickListener { searchBooksByAsyncTaskLoader() }

        supportLoaderManager.apply {
            getLoader<BookAsyncTaskLoader>(LOADER_ID)?.let {
                initLoader(LOADER_ID, null, this@MainActivity)
            }
        }
    }

    private fun searchBooksByAsyncTask() {
        val queryString = bookInput.text.toString()

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(searchButton1.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager?.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected && queryString.isNotEmpty()) {
            BookAsyncTask(titleTextView, authorTextView).execute(queryString)
            titleTextView.text = getString(R.string.loading)
            authorTextView.text = ""
        } else {
            if (queryString.isEmpty()) {
                titleTextView.text = getText(R.string.no_search_term)
                authorTextView.text = ""
            } else {
                titleTextView.setText(R.string.no_network)
                authorTextView.text = ""
            }
        }
    }

    private fun searchBooksByAsyncTaskLoader() {
        val queryString = bookInput.text.toString()

        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputManager?.hideSoftInputFromWindow(searchButton1.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connectivityManager?.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected && queryString.isNotEmpty()) {

            val queryBundle = Bundle().apply {
                putString(QUERY_STRING, queryString)
            }
            supportLoaderManager.restartLoader(LOADER_ID, queryBundle, this@MainActivity)
            titleTextView.text = getString(R.string.loading)
            authorTextView.text = ""
        } else {
            if (queryString.isEmpty()) {
                titleTextView.text = getText(R.string.no_search_term)
                authorTextView.text = ""
            } else {
                titleTextView.setText(R.string.no_network)
                authorTextView.text = ""
            }
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        val queryString = args?.getString(QUERY_STRING) ?: throw IllegalArgumentException()
        return BookAsyncTaskLoader(this@MainActivity, queryString)
    }

    override fun onLoadFinished(loader: Loader<String>, data: String?) {
        try {
            val jsonObject = JSONObject(data)
            val items = jsonObject.getJSONArray("items")

            var index = 0
            var title: String? = null
            var authors: String? = null

            while (index < items.length()) {
                val book = items.getJSONObject(index)
                val volumeInfo = book.getJSONObject("volumeInfo")

                try {
                    title = volumeInfo.getString("title")
                    authors = volumeInfo.getString("authors")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                index++
            }

            if (title != null && authors != null) {
                titleTextView.text = title
                authorTextView.text = authors
            } else {
                titleTextView.setText(R.string.no_results)
                authorTextView.text = ""
            }
        } catch (e: Exception) {
            titleTextView.setText(R.string.no_results)
            authorTextView.text = ""
            e.printStackTrace()
        }
    }

    override fun onLoaderReset(p0: Loader<String>) {
    }
}
