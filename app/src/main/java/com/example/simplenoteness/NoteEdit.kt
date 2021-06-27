package com.example.simplenoteness

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch

class NoteEdit : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var btnAdd: Button
    private var db: AppDatabase? = null
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        editText =  findViewById(R.id.snaEditText)
        btnAdd =  findViewById(R.id.snaBtnAdd)

        context =  this
        lifecycleScope.launch {
            db = AppDatabase.getDatabase(context)
        }
    }

    fun addNote(v: View) {
        val commit: String = editText.text.toString()
        val note = Note(commit)
        lifecycleScope.launch {

            db?.noteDao()?.insert(note)

            whenResumed {
                editText.text.clear()
            }
        }
    }
}