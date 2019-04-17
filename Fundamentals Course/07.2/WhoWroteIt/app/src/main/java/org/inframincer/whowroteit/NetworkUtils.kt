package org.inframincer.whowroteit

import android.net.Uri
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object NetworkUtils {
    private val LOG_TAG = NetworkUtils::class.java.simpleName
    private const val BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?"
    private const val QUERY_PARAM = "q"
    private const val MAX_RESULT = "maxResults"
    private const val PRINT_TYPE = "printType"

    fun getBookInfo(queryString: String): String {
        var urlConnection: HttpURLConnection? = null
        var reader: BufferedReader? = null
        var bookJSONString = ""

        try {
            val builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, queryString)
                .appendQueryParameter(MAX_RESULT, "5")
                .appendQueryParameter(PRINT_TYPE, "books")
                .build()
            val requestURL = URL(builtURI.toString())
            urlConnection = requestURL.openConnection() as HttpURLConnection
            urlConnection.apply {
                requestMethod = "GET"
                connect()
            }
            val inputStream = urlConnection.inputStream
            reader = BufferedReader(InputStreamReader(inputStream))
            val stringBuilder = StringBuffer()

            while (true) {
                reader.readLine()?.let {
                    stringBuilder.append("$it\n")
                } ?: break
            }

            if (stringBuilder.isEmpty()) {
                return ""
            }
            bookJSONString = stringBuilder.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            urlConnection?.disconnect() // if (urlConnection != null) { urlConnection.disconnect() }
            try {
                reader?.close() // if (reader != null) { reader.close() }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        Log.d(LOG_TAG, bookJSONString)
        return bookJSONString
    }
}
