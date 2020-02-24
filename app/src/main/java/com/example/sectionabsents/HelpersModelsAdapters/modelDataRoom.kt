package com.example.sectionabsents.HelpersModelsAdapters

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MyTable")
data class modelDataRoom(
    @PrimaryKey(autoGenerate = false)
    var id:Int?,
    var time:String,
     var name:String
    , var listAbsents:String
    , var listPresents:String
)

