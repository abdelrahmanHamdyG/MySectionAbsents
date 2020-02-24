package com.example.sectionabsents.HelpersModelsAdapters

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.example.sectionabsents.RoomDataBase.DataBase
import com.google.firebase.database.*
import com.google.gson.Gson
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class helper {

    companion object {

        const val booleanPath = "trueOrFalse"
        const val tag = "MyTag"
        const val studentsPresentsPath = "studentPresents"
        const val studentsAbsentsPath = "studentAbsents"
        const val TheFather = "theFather"
        const val StudentsID = "studentsId"
        const val TheNameOfTheHistory = "historyName"
        const val TheDateOfTheHistory = "historyDate"
        var firebaseDatabase=FirebaseDatabase.getInstance()

        fun showToast(context: Context, text: String) {

            Toast.makeText(context, text, Toast.LENGTH_LONG).show()

        }

        //////////////////////////////////////////////////////////////////

        fun log(text: String) {

            Log.i(tag, text)
        }

        //////////////////////////////////////////////////////////////////

        fun checkNetwork(context: Context): Boolean {

            var connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(
                        ConnectivityManager
                            .TYPE_MOBILE
                    ).state == NetworkInfo.State
                .CONNECTED

        }

        //////////////////////////////////////////////////////////////////

        fun insertSectionDataToFireBase(data1:String,data2: String,title:String,context: Context){

            var databaseReference= firebaseDatabase.getReference(TheFather)
            log("we are Inserting data")
            var calender = Calendar.getInstance()
            var dateFormat = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calender.time)

            Thread {

                var dao = DataBase.getDataBase(context).returnDao()
                dao.insertData(
                    modelDataRoom(
                        null,
                        dateFormat,
                        title,
                        data1,
                        data2
                    )
                )

                databaseReference.removeValue()


        }.start()


    }
        //////////////////////////////////////////////////////////////////



        fun getAllDataToRoom(title:String,context: Context){

            var listRoomNormalPre=ArrayList<modelData>()
            var listRoomNormalAbs=ArrayList<modelData>()
            var databaseReference=firebaseDatabase.getReference(TheFather).child(studentsPresentsPath)
            databaseReference.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {

                    for (i in p0.children){

                        var data=i.getValue(modelData::class.java)
                        listRoomNormalPre.add(data!!)

                    }
                    databaseReference= firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath)
                    databaseReference.addListenerForSingleValueEvent(object :ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {

                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            for (i in p0.children){

                                var data=i.getValue(modelData::class.java)
                                listRoomNormalAbs.add(data!!)

                            }

                            insertSectionDataToFireBase(convertToGson(listRoomNormalPre),
                                convertToGson(listRoomNormalAbs),title,context)



                        }
                    })

                }


            })


        }

        //////////////////////////////////////////////////////////////////


        fun convertToGson(ar:ArrayList<modelData>):String{
            var gson=Gson()
            return gson.toJson(ar)

        }

}

}