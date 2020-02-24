package com.example.sectionabsents.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheFather
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.showToast
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.studentsAbsentsPath
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.studentsPresentsPath
import com.example.sectionabsents.HelpersModelsAdapters.modelData
import com.google.firebase.database.*


class viewModelAddStudent(var app:Application):AndroidViewModel(app) {

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    lateinit var databaseReference3: DatabaseReference

    //////////////////////////////////////////////////////////////////

    fun addTheData(name: String) {

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath)
        databaseReference.orderByChild("id").equalTo(name).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            //////////////////////////////////////////////
            override fun onDataChange(p0: DataSnapshot) {



                if (p0.value!=null) {
                    for (i in p0.children) {

                        databaseReference = firebaseDatabase.getReference(TheFather).child(studentsPresentsPath).push()
                        var data = i.getValue(modelData::class.java)

                        //////////////////////////////////////////////

                        databaseReference.setValue(modelData(data!!.id!!,data.name!!,databaseReference.key.toString())).addOnCompleteListener {

                            if (it.isSuccessful) {

                                showToast(app, "${data!!.name}  added successfully")

                            } else {

                                showToast(app, "Check your Internet")
                                }
                            }

                        //////////////////////////////////////////////

                        databaseReference2 = firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath).child(data!!.key!!)
                        databaseReference2.removeValue()

                    }

                } else {

                    showToast(app, "this name is already added")
                }
            }
        })
    }


    //////////////////////////////////////////////////////////////////
    fun removeData(name:String){

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference3 = firebaseDatabase.getReference(TheFather).child(studentsPresentsPath)
        databaseReference3.orderByChild("id").equalTo(name).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(p0: DatabaseError) {

            }

            //////////////////////////////////////////////

            override fun onDataChange(p0: DataSnapshot) {

                if (p0.value!=null) {

                    for (i in p0.children) {

                        var data = i.getValue(modelData::class.java)

                        databaseReference = firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath).push()
                        databaseReference.setValue(modelData(data!!.id!!,data.name!!,databaseReference.key.toString())).addOnCompleteListener {
                            if (it.isSuccessful) {

                                showToast(app, "${data!!.name}  Removed successfully")

                            } else {

                                showToast(app, "Check your Internet")
                            }
                        }

                        //////////////////////////////////////////////
                        databaseReference2 = firebaseDatabase.getReference(TheFather).child(studentsPresentsPath).child(data!!.key!!)
                        databaseReference2.removeValue()

                    }

                } else {

                    showToast(app, "this name is already removed")
                }
            }

        })

    }



}