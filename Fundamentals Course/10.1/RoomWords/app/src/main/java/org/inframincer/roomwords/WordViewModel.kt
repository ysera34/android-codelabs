package org.inframincer.roomwords

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: WordRepository = WordRepository(application)
    internal val allWords: LiveData<List<Word>>? = repository.allWords

    fun insert(word: Word) {
        repository.insert(word)
    }
}
