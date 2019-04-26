package org.inframincer.roomwords

import android.app.Application
import android.arch.lifecycle.LiveData
import android.os.AsyncTask

class WordRepository(application: Application) {

    private var wordDao: WordDao?
    var allWords: LiveData<List<Word>>?

    init {
        val db = WordRoomDatabase.getDatabase(application)
        wordDao = db?.wordDao()
        allWords = wordDao?.getAllWords()
    }

    fun insert(word: Word) {
        wordDao?.let {
            InsertAsyncTask(it).execute(word)
        }
    }

    private class InsertAsyncTask(
        private val asyncTaskDao: WordDao
    ) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word): Void? {
            asyncTaskDao.insert(params[0])
            return null
        }
    }
}
