package com.example.simplenoteness

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val commit: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
