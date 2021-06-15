package com.example.simplenoteness

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity(), NoteAdapter.OnItemListener {

    private lateinit var editText: EditText
    private lateinit var btnAdd: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteList: ArrayList<Note>
    private lateinit var adapter: NoteAdapter
    private lateinit var context:Context

    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context =  this


        lifecycleScope.launch {
            db = AppDatabase.getDatabase(context)
        }

        val manager = LinearLayoutManager(this)
        editText =  findViewById(R.id.snaEditText)
        btnAdd =  findViewById(R.id.snaBtnAdd)
        recyclerView = findViewById(R.id.snaRecycleView)

        noteList = ArrayList()

        readNotesFromDatabase()

        recyclerView.setHasFixedSize(true)
        adapter = NoteAdapter(noteList, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, manager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

    }

    fun addNote(v: View) {
        val commit: String = editText.text.toString()
        val note = Note(commit)
        lifecycleScope.launch {

            db?.noteDao()?.insert(note)

            whenResumed {
                noteList.add(note)
                editText.text.clear()
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun readNotesFromDatabase() {
        lifecycleScope.launch {
            val notes = db?.noteDao()?.getAll();

            whenResumed {
                if (notes != null) {
                    for (note:Note in notes) {
                        noteList.add(note)
                    }
                }
            }
        }
    }

    override fun showMessage(position: Int, note: Note) {
        Toast.makeText(this, note.commit, Toast.LENGTH_SHORT).show()
    }

    override fun alertDialog(position: Int, note: Note) {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setTitle("Emilinar nota")
        alertDialog.setMessage(note.commit)
        alertDialog.setPositiveButton("Si") { _, _ ->

            lifecycleScope.launch {
                db?.noteDao()?.delete(note)
                whenResumed {
                    adapter.removeItem(position);
                }
            }
        }
        alertDialog.setNegativeButton("No") { _, _ -> }
        alertDialog.show()
    }

}