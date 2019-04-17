package org.inframincer.whowroteit

import android.content.Context
import android.support.v4.content.AsyncTaskLoader

class BookAsyncTaskLoader(
    context: Context,
    private val queryString: String
) : AsyncTaskLoader<String>(context) {

    private val TAG = BookAsyncTaskLoader::class.java.simpleName

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): String? {
        return NetworkUtils.getBookInfo(queryString)
    }
}
