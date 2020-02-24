
package com.example.sectionabsents.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sectionabsents.RoomDataBase.DAO
import com.example.sectionabsents.HelpersModelsAdapters.modelData
import com.google.gson.Gson

class ViewModelHistory2(application: Application):AndroidViewModel(application) {


        var listOfHistory=MutableLiveData<Array<modelData>>()
        var typeM=MutableLiveData<String>()

        /////////////////////////////////////////////////////

        fun getAbsentsData(string: String, dao: DAO){
            Thread {

                var gson = Gson()
                var object1 = dao.readOneData(string)

                var listAbsents = gson.fromJson(object1.listAbsents, Array<modelData>::class.java)
                listOfHistory.postValue(listAbsents!!)

            }.start()
        }


        /////////////////////////////////////////////////////
        fun getPresentsData(string: String, dao: DAO){

            Thread {

                var gson = Gson()
                var object1 = dao.readOneData(string)
                var listPresents = gson.fromJson(object1.listPresents, Array<modelData>::class.java)

                /////////////////////////////////////////////////////
                listOfHistory.postValue(listPresents)

            }.start()

        }

        /////////////////////////////////////////////////////

        fun changeType(type:String){

            typeM.value=type

        }
        /////////////////////////////////////////////////////



}