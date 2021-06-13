package com.example.simplenoteness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), NoteAdapter.OnItemListener {

    private lateinit var editText: EditText
    private lateinit var btnAdd: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var noteList: ArrayList<Note>
    private lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = LinearLayoutManager(this)
        editText =  findViewById(R.id.snaEditText)
        btnAdd =  findViewById(R.id.snaBtnAdd)
        recyclerView = findViewById(R.id.snaRecycleView)

        noteList = ArrayList()

        recyclerView.setHasFixedSize(true)
        adapter = NoteAdapter(noteList, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = manager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, manager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

    }

    fun addNote(v: View) {
        val commit: String = editText.text.toString()
        noteList.add(Note(commit))
        editText.text.clear()

        adapter.notifyDataSetChanged()
    }

    override fun showMessage(position: Int, note: Note) {
        Toast.makeText(this, note.commit, Toast.LENGTH_SHORT).show()
    }

    override fun alertDialog(position: Int, note: Note) {
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setTitle("Emilinar nota")
        alertDialog.setMessage(note.commit)
        alertDialog.setPositiveButton("Si") { _, _ ->
            adapter.removeItem(position);
        }
        alertDialog.setNegativeButton("No") { _, _ -> }
        alertDialog.show()
    }

}