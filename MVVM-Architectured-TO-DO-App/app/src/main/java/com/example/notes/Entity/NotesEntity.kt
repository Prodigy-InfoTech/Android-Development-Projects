package com.example.notes.Entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Notes")
class NotesEntity(

    @PrimaryKey(autoGenerate = true)
    var  id :Int?= null,

    var title:String,
    var subtitle:String,
    var notes:String,
    var date: String ,
    var priority:String

) : Parcelable