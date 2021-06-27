package com.example.simplenoteness

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import kotlinx.coroutines.launch

class NoteEdit : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var btnAction: Button
    private lateinit var titleNote: TextView
    private var db: AppDatabase? = null
    private lateinit var note: Note
    private var noteId = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)
        editText =  findViewById(R.id.snaEditText)
        btnAction =  findViewById(R.id.snaBtnAdd)
        titleNote = findViewById(R.id.titleNote)

        lifecycleScope.launch {
            db = AppDatabase.getDatabase(this@NoteEdit)
        }

        noteId = intent.getIntExtra(NOTE_ACTION, 0)
        if(noteId > 0) {
            titleNote.text = "Edición de contenido"
            btnAction.text = "Editar"
            this.readNote(noteId)
        } else {
            titleNote.text = "Agregando nueva nota"
            btnAction.text = "Agregar"
        }
    }

    private fun readNote(id: Int) {
        lifecycleScope.launch {
            note = db?.noteDao()?.getById(id)!!
            whenResumed {
                editText.setText(note.commit)
            }
        }
    }

    fun actionButton(v: View) {
        if(noteId > 0) {
            editNote()
        } else {
            addNote()
        }
    }

    private fun addNote() {
        val commit: String = editText.text.toString()
        note = Note(commit)
        lifecycleScope.launch {
            db?.noteDao()?.insert(note)

            whenResumed {
                editText.text.clear()
                finish()
            }
        }
    }

    private fun editNote() {
        lifecycleScope.launch {
            note.commit = editText.text.toString()
            db?.noteDao()?.update(note)

            whenResumed {
                Toast.makeText(this@NoteEdit, "Actualizado con éxito", Toast.LENGTH_SHORT).show()
            }
        }
    }
}