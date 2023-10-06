package com.phoenix.note.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val message: String,
    val dateCreated: Long,
    val dateUpdated: Long = System.currentTimeMillis()
)