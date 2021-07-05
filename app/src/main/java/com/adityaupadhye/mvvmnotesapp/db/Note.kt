package com.adityaupadhye.mvvmnotesapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Note(@ColumnInfo(name = "title") var title: String,
           @ColumnInfo(name = "content") var content: String) {

    @PrimaryKey(autoGenerate = true) var id = 0

}