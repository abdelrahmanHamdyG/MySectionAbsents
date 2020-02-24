package com.example.sectionabsents.RoomDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sectionabsents.HelpersModelsAdapters.modelDataRoom

@Database(entities = [modelDataRoom::class],version = 1 ,exportSchema = false)
abstract class DataBase:RoomDatabase() {

    abstract fun returnDao(): DAO

    companion object{

        @Volatile
        private var INSTANCE: DataBase?=null

        fun getDataBase(context: Context): DataBase {

            if (INSTANCE ==null){
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DataBase::class.java,"Data").build()
                }
            }
            return INSTANCE!!
        }

    }

}