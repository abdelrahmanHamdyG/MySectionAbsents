package com.example.sectionabsents.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sectionabsents.*
import com.example.sectionabsents.HelpersModelsAdapters.TimeAndName
import com.example.sectionabsents.HelpersModelsAdapters.recyclerHistoryAdapter
import com.example.sectionabsents.RoomDataBase.DAO
import com.example.sectionabsents.RoomDataBase.DataBase
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {


    lateinit var dao: DAO
    var ar=ArrayList<TimeAndName>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)


        Thread {

            dao = DataBase.getDataBase(this).returnDao()
            var x = dao.readData()
            for (i in x){

                ar.add(TimeAndName(i.time, i.name))

                    }

            var adapter= recyclerHistoryAdapter(this, ar)
            recyclerHistory.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            recyclerHistory.adapter=adapter

            }.start()
        }
    }

