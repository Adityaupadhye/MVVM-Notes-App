package com.adityaupadhye.mvvmnotesapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.adityaupadhye.mvvmnotesapp.db.Note
import com.adityaupadhye.mvvmnotesapp.db.NoteDatabase
import com.adityaupadhye.mvvmnotesapp.db.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NoteRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

    fun updateNote(updatedNote: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(updatedNote)
    }

}