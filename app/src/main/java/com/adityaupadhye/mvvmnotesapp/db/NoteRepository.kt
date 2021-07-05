package com.adityaupadhye.mvvmnotesapp.db

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note){
        noteDao.insert(note)
    } 

    suspend fun deleteNote(note: Note){
        noteDao.delete(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.update(note)
    }

}