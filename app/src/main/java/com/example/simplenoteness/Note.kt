package com.example.simplenoteness

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.util.*


@Entity
data class Note(
    val commit: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "created_date")
    @TypeConverters(DateTimeConverter::class)
    var createDate: Date? = Date()
}
