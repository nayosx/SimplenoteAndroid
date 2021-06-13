package com.example.simplenoteness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private val noteList: ArrayList<Note>, var listener: NoteAdapter.OnItemListener)
    : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    interface OnItemListener {
        fun showMessage(position: Int, note: Note)
        fun alertDialog(position : Int, note: Note)
    }

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.holder_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    fun removeItem(position: Int) {
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
        , View.OnClickListener, View.OnLongClickListener {

        private val textView: TextView = itemView.findViewById(R.id.txtHolder)
        private lateinit var note: Note

        init {
            context = itemView.context
        }

        fun bind(note: Note) {
            this.note = note
            textView.text = note.commit
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.showMessage(adapterPosition, this.note)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.alertDialog(adapterPosition, this.note)
            return true
        }

    }
}