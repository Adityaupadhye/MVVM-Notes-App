package com.adityaupadhye.mvvmnotesapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adityaupadhye.mvvmnotesapp.databinding.ActivityMainBinding
import com.adityaupadhye.mvvmnotesapp.db.Note
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity(), INotesRVAdapter {


    private lateinit var viewModel: NoteViewModel
    var title = ""
    var desc = ""
    private lateinit var dialogView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notesRV.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        binding.notesRV.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        Log.d("TAG", "items = ${adapter.itemCount}")

        viewModel.allNotes.observe(this, { notes->

            notes?.let {
                adapter.updateList(it)
                Log.d("TAG", "items in observe = ${adapter.itemCount}")
            }
            if(adapter.itemCount == 0){
                binding.emptyRV.visibility = View.VISIBLE
            }
            else
                binding.emptyRV.visibility = View.GONE
        })

        Log.d("note", "${adapter.itemCount}")
        Log.d("FAB", "FAB clicked")
        binding.addNoteFAB.setOnClickListener {
            showInputDialog()
        }
    }

    private fun createInputDialog(): AlertDialog {
        val inputView = layoutInflater.inflate(R.layout.note_dialog, null)
        dialogView = inputView

        val dialog = AlertDialog.Builder(this)
                .setView(inputView)
                .setNegativeButton("CANCEL", null)
                .create()

        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        return dialog
    }


    private fun showInputDialog(){


        val dialog = createInputDialog()

        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){ _, _ ->
            title = dialogView.findViewById<TextInputLayout>(R.id.titleWrapper).editText?.text.toString()
            desc = dialogView.findViewById<TextInputLayout>(R.id.descWrapper).editText?.text.toString()

            Log.d("note", "title = $title desc = $desc")
            if(title.isEmpty())
                title = "Untitled"
            if(desc.isEmpty()){
                Toast.makeText(this@MainActivity, "Note not entered", Toast.LENGTH_SHORT).show()

            }else{
                viewModel.insertNote(Note(title, desc))
                Toast.makeText(this@MainActivity, "Inserted $title -> $desc", Toast.LENGTH_SHORT).show()
            }

        }

        dialog.show()

    }


    override fun onDeleteItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Deleted $note", Toast.LENGTH_SHORT).show()
    }


    override fun onItemClicked(note: Note) {
        updateMyNote(note)
    }


    private fun updateMyNote(note: Note){
        val updateDialog = createInputDialog()
        dialogView.findViewById<TextInputLayout>(R.id.titleWrapper).editText?.setText(note.title)
        dialogView.findViewById<TextInputLayout>(R.id.descWrapper).editText?.setText(note.content)

        updateDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK"){ _, _ ->
            title = dialogView.findViewById<TextInputLayout>(R.id.titleWrapper).editText?.text.toString()
            desc = dialogView.findViewById<TextInputLayout>(R.id.descWrapper).editText?.text.toString()

            Log.d("note", "title = $title desc = $desc")
            if(title.isEmpty())
                title = "Untitled"
            if(desc.isEmpty()){
                Toast.makeText(this@MainActivity, "Note not entered", Toast.LENGTH_SHORT).show()

            }else{
                note.title = title
                note.content = desc
                viewModel.updateNote(note)
                Toast.makeText(this@MainActivity, "Updated $title -> $desc", Toast.LENGTH_SHORT).show()
            }

        }

        updateDialog.show()
    }
}