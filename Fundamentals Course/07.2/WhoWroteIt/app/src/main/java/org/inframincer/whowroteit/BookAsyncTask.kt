package org.inframincer.whowroteit

import android.os.AsyncTask
import android.widget.TextView
import org.json.JSONObject
import java.lang.ref.WeakReference

class BookAsyncTask(
    titleTextView: TextView,
    authorTextView: TextView
) : AsyncTask<String, Void, String>() {

    private val TAG = BookAsyncTask::class.java.simpleName

    private val titleTextView = WeakReference<TextView>(titleTextView)
    private val authorTextView = WeakReference<TextView>(authorTextView)

    override fun doInBackground(vararg params: String?): String {
        return NetworkUtils.getBookInfo(params[0] ?: "")
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        try {
            val jsonObject = JSONObject(result)
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
                titleTextView.get()?.text = title
                authorTextView.get()?.text = authors
            } else {
                titleTextView.get()?.setText(R.string.no_results)
                authorTextView.get()?.text = ""
            }
        } catch (e: Exception) {
            titleTextView.get()?.setText(R.string.no_results)
            authorTextView.get()?.text = ""
            e.printStackTrace()
        }
    }
}
