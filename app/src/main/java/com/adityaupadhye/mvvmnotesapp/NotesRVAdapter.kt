package com.adityaupadhye.mvvmnotesapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.adityaupadhye.mvvmnotesapp.db.Note


class NotesRVAdapter(private val context: Context, private val listener: INotesRVAdapter):
        RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder>() {

    private val allNotes = ArrayList<Note>()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titleText: TextView = itemView.findViewById(R.id.note_title)
        val descText: TextView = itemView.findViewById(R.id.note_content)
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteBtn)
        val noteCard: CardView = itemView.findViewById(R.id.note_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.note_item, parent, false)
        val viewHolder = NoteViewHolder(item)
        viewHolder.deleteBtn.setOnClickListener{
            listener.onDeleteItemClicked(allNotes[viewHolder.adapterPosition])
        }
        viewHolder.noteCard.setOnClickListener{
            listener.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = allNotes[position]
        holder.titleText.text = currentNote.title
        holder.descText.text = currentNote.content
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }

}

interface INotesRVAdapter{
    fun onDeleteItemClicked(note: Note)

    fun onItemClicked(note: Note)
}