package com.example.sectionabsents.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.checkNetwork
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.showToast
import com.example.sectionabsents.R
import com.example.sectionabsents.ViewModels.viewModelAddStudent
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_student.*

class addStudentActivity : AppCompatActivity() {


    lateinit var firebaseDatabase: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        firebaseDatabase=FirebaseDatabase.getInstance()

        var viewModel=ViewModelProviders.of(this).get(viewModelAddStudent::class.java)



        buttonAddStudent.setOnClickListener {



            if (!checkNetwork(this@addStudentActivity)) {
                showToast(this@addStudentActivity, "Check you Network And try Again ")
            } else {

                if (editID.text.isEmpty()) {

                    showToast(this,"empty")

                } else {
                    viewModel.addTheData(editID.text.toString())



                    }

                }
            }



        buttonRemoveStudent.setOnClickListener {

            if (!checkNetwork(this@addStudentActivity)) {
                showToast(this@addStudentActivity, "Check you Network And try Again ")
            } else {

                if (editID.text.isEmpty()) {

                    showToast(this,"empty")

                } else {


                   viewModel.removeData(editID.text.toString())



                }
                }



        }

        }
    }