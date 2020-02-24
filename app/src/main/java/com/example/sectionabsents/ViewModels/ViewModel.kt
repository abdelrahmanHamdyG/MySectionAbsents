package com.example.sectionabsents.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.StudentsID
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheFather
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.booleanPath
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.showToast
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.studentsAbsentsPath
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.studentsPresentsPath
import com.example.sectionabsents.HelpersModelsAdapters.modelData
import com.google.firebase.database.*

class MyViewModel(var app:Application):AndroidViewModel(app) {


    lateinit var databaseReference: DatabaseReference
    lateinit var databaseReference2: DatabaseReference
    lateinit var databaseReference3: DatabaseReference
    lateinit var databaseReferenceFather: DatabaseReference
    ///////////////////////////////////////////////////////////
    var trueOrFalseData=MutableLiveData<String>()
    var listNormal=ArrayList<modelData>()
    var firebaseDatabase=FirebaseDatabase.getInstance()
    var listOfStudents=MutableLiveData<ArrayList<modelData>>()
    var numberOfStudents=MutableLiveData<Int>()
    var absentsOrPresents=MutableLiveData<String>()
    var absentsOrPresnetsString=MutableLiveData<String>()

    //////////////////////////////////////////////////////////////////

    fun copyDataFromID(){

        databaseReference2=firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath)
        databaseReference=firebaseDatabase.getReference(StudentsID)

        databaseReference.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {

                var x=p0.value
                databaseReference2.setValue(x)

            }


        })


    }

    //////////////////////////////////////////////////////////////////

    fun getTrueOrFalseFromFirebase(){

        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference3=firebaseDatabase.getReference(booleanPath)
        databaseReference3.addValueEventListener(valueEventTrue())



    }

    //////////////////////////////////////////////////////////////////


    fun getPresentsStudentsFromFirebase(){



        firebaseDatabase= FirebaseDatabase.getInstance()
        databaseReference=firebaseDatabase.getReference(TheFather).child(studentsPresentsPath)
        databaseReference.addListenerForSingleValueEvent(valueEventListener1())

    }

    //////////////////////////////////////////////////////////////////

    fun getAbsentsStudentsFromFireBase() {



        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference2 = firebaseDatabase.getReference(TheFather).child(studentsAbsentsPath)
        databaseReference2.addListenerForSingleValueEvent(valueEventListener2())

    }

    //////////////////////////////////////////////////////////////////

    fun checkShared(type:String){

        if (type=="absents"){

            absentsOrPresents.postValue("presents")

        }else{

            absentsOrPresents.postValue("absents")

        }

    }

    //////////////////////////////////////////////////////////////////

    fun theFatherFun(type:String){
        firebaseDatabase= FirebaseDatabase.getInstance()

        databaseReferenceFather=firebaseDatabase.getReference(TheFather)
        databaseReferenceFather.addListenerForSingleValueEvent(theFatherEventListener(type))

    }


    //////////////////////////////////////////////////////////////////


    inner class valueEventListener2:ValueEventListener{

        override fun onCancelled(p0: DatabaseError) {

            showToast(app,p0.message)
        }

        override fun onDataChange(p0: DataSnapshot) {

            listNormal.clear()
            for (i in p0.children) {
                var data=i.getValue(modelData::class.java)
                listNormal.add(data!!)


            }
            listOfStudents.value = listNormal
            numberOfStudents.value=listNormal.size
            absentsOrPresnetsString.value="absents"

        }

    }

    //////////////////////////////////////////////////////////////////

    inner class valueEventListener1:ValueEventListener{

        override fun onCancelled(p0: DatabaseError) {

            showToast(app,p0.message)
        }

        override fun onDataChange(p0: DataSnapshot) {

            listNormal.clear()


            for (i in p0.children) {
                var data=i.getValue(modelData::class.java)
                listNormal.add(data!!)

            }

            listOfStudents.value = listNormal
            numberOfStudents.value=listNormal.size
            absentsOrPresnetsString.value="presents"


        }

    }

    //////////////////////////////////////////////////////////////////

    inner class theFatherEventListener(var type:String):ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            if (type=="absents"){

                getAbsentsStudentsFromFireBase()

            }else{
                getPresentsStudentsFromFirebase()

            }

        }


    }
    inner class valueEventTrue:ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {
            showToast(app,p0.message)
            trueOrFalseData!!.value="false"


        }

        override fun onDataChange(p0: DataSnapshot) {

            var data = p0.getValue(String::class.java)

            trueOrFalseData!!.value=data

        }

    }


    }











