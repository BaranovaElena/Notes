package com.example.notes.recycler

import androidx.recyclerview.widget.RecyclerView
import com.example.notes.repo.NoteEntity
import android.view.ViewGroup

class NotesAdapter : RecyclerView.Adapter<NoteViewHolder>() {
    private var list: MutableList<NoteEntity> = mutableListOf()
    private var onItemListener: OnItemListener? = null

    fun setList(list: List<NoteEntity>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    fun addItem(note: NoteEntity) {
        list.add(note)
        notifyItemInserted(itemCount-1)
    }

    fun removeItem(note: NoteEntity) {
        val index = list.indexOf(note)
        list.remove(note)
        notifyItemRemoved(index)
    }

    interface OnItemListener {
        fun onItemClick(note: NoteEntity?)
        fun onItemDelete(note: NoteEntity?)
    }

    fun setOnItemListener(onItemListener: OnItemListener?) {
        this.onItemListener = onItemListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(parent, onItemListener)

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}