package com.example.sectionabsents.RoomDataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sectionabsents.HelpersModelsAdapters.modelDataRoom


@Dao
interface DAO {

    @Insert
    fun insertData(modelDataRoom: modelDataRoom)

    //////////////////////////////////////////////////////////////////

    @Query("SELECT * from MyTable")
    fun readData():List<modelDataRoom>

    //////////////////////////////////////////////////////////////////

    @Query("DELETE FROM MyTable")
    fun deleteData()

    //////////////////////////////////////////////////////////////////

    @Query("SELECT * FROM MyTable WHERE name = :name1")
    fun readOneData(name1:String):modelDataRoom

}