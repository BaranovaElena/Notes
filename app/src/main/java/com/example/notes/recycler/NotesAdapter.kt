package com.example.notes.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.notes.repo.NoteEntity
import android.view.ViewGroup

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private var list: MutableList<NoteEntity> = mutableListOf()
    private var onItemClickListener: OnItemClickListener? = null
    private var onItemDeleteListener: OnItemDeleteListener? = null

    fun setList(list: List<NoteEntity>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(note: NoteEntity?)
    }

    interface OnItemDeleteListener {
        fun onItemDelete(note: NoteEntity?)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemDeleteListener(onItemDeleteListener: OnItemDeleteListener?) {
        this.onItemDeleteListener = onItemDeleteListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(parent, onItemClickListener, onItemDeleteListener)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}