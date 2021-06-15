package com.example.simplenoteness

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM Note WHERE id = :id")
    suspend fun getById(id: Int): Note

    @Update
    suspend fun update(note: Note)

    @Insert
    suspend fun insert(note: Note)

    @Delete
    suspend fun delete(note: Note)
}