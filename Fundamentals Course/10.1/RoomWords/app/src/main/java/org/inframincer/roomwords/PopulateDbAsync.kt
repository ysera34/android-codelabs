package org.inframincer.roomwords

import android.os.AsyncTask

class PopulateDbAsync internal constructor(db: WordRoomDatabase?) : AsyncTask<Void, Void, Void>() {

    private val dao: WordDao? = db?.wordDao()
    var words = arrayOf("dolphin", "crocodile", "cobra")

    override fun doInBackground(vararg params: Void): Void? {
        dao?.deleteAll()

        for (i in 0 until words.size) {
            val word = Word(word = words[i])
            dao?.insert(word)
        }
        return null
    }
}
