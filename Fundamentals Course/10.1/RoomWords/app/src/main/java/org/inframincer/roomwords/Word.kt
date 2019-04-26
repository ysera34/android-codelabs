package org.inframincer.roomwords

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val word: String
)
