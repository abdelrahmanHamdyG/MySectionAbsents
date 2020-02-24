package com.example.sectionabsents.Activities

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheFather
import com.example.sectionabsents.ViewModels.MyViewModel
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.booleanPath
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.checkNetwork
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.getAllDataToRoom
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.showToast
import com.example.sectionabsents.HelpersModelsAdapters.recyclerAdapter
import com.example.sectionabsents.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert_layout.view.*




class MainActivity : AppCompatActivity() {


    lateinit var animBounce: Animation
    lateinit var animBounce2: Animation
    lateinit var viewModel: MyViewModel
    lateinit var alertDialog: AlertDialog
    lateinit var view: View
    //////////////////////////////////////////
    var ReadOrNo: Boolean = false
    var firebaseDatabase: FirebaseDatabase= FirebaseDatabase.getInstance()
    var databaseReference: DatabaseReference=firebaseDatabase.getReference(TheFather)
    var databaseReference2:DatabaseReference=firebaseDatabase.getReference(booleanPath)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    //////////////////////////////////////////////////////////////////

    inner class valueEventListener :ValueEventListener{
        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            viewModel.theFatherFun(viewModel.absentsOrPresents.value!!)

        }


    }
    //////////////////////////////////////////////////////////////////



    override fun onStart() {
            super.onStart()


        alertDialog = AlertDialog.Builder(this).create()
        view = LayoutInflater.from(this).inflate(R.layout.alert_layout, null)
        alertDialog.setView(view)
        alertDialog.setTitle("Enter Section name")

        //////////////////////////////////////////////////////////////////

        val x = ProgressDialog(this@MainActivity)
        x.setTitle("Checking network")
        x.setCancelable(false)
        x.show()
        //////////////////////////////////////////////////////////////////

        animBounce = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim2)
        animBounce2 = AnimationUtils.loadAnimation(this@MainActivity, R.anim.anim)

        //////////////////////////////////////////////////////////////////

        viewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
        viewModel.getTrueOrFalseFromFirebase()
        viewModel.trueOrFalseData!!.observe(this, Observer {


            if (it == "true") {
                ReadOrNo = true
                startSection1.visibility = View.GONE
                endSection1.visibility = View.VISIBLE
                recyclerId1.visibility = View.VISIBLE


                Thread {
                    viewModel.absentsOrPresents.postValue("absents")
                    databaseReference.removeEventListener(valueEventListener())
                    databaseReference.addValueEventListener(valueEventListener())
                }.start()
                x.dismiss()

            } else {

                ReadOrNo = false

                supportActionBar!!.title = "Section absence"
                startSection1.visibility = View.VISIBLE
                endSection1.visibility = View.GONE
                x.dismiss()
                recyclerId1.visibility = View.GONE
            }


        })

        //////////////////////////////////////////////////////////////////


        viewModel.listOfStudents.observe(this@MainActivity, Observer { array ->

            val adapter = recyclerAdapter(this@MainActivity, array)
            recyclerId1.visibility = View.VISIBLE
            recyclerId1.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            recyclerId1.adapter = adapter


        })

        //////////////////////////////////////////////////////////////////


        viewModel.numberOfStudents.observe(this@MainActivity, Observer { number ->
            if (ReadOrNo) {

                if (number == 0) {

                    supportActionBar!!.title = "No ${viewModel.absentsOrPresents.value}"
                } else {
                    supportActionBar!!.title = "number of ${viewModel.absentsOrPresents.value}= $number "
                }




            } else {
                supportActionBar!!.title = "SectionAbsents"

            }
        })

        //////////////////////////////////////////////////////////////////






        startSection1.setOnClickListener {

            if (checkNetwork(this@MainActivity)) {

                Thread {

                    viewModel.copyDataFromID()
                    databaseReference2.setValue("true")

                }.start()

                it.startAnimation(animBounce2)
                endSection1.startAnimation(animBounce)
            } else {
                showToast(this@MainActivity, "Check you Network And try Again ")

            }
        }

        //////////////////////////////////////////////////////////////////


        endSection1.setOnClickListener {

            databaseReference.removeEventListener(valueEventListener())
            if (checkNetwork(this@MainActivity)) {
                alertDialog.show()

                view.no.setOnClickListener {
                    alertDialog.dismiss()
                }
                view.yes.setOnClickListener {
                    if (view.editTextAlert.text.isEmpty()){


                        showToast(this@MainActivity,"Enter A title please")

                    }else {

                        databaseReference2.setValue("false")

                        getAllDataToRoom(view.editTextAlert.text.toString(),this@MainActivity)

                        it.startAnimation(animBounce2)
                        startSection1.startAnimation(animBounce)
                        alertDialog.dismiss()
                        recyclerId1.visibility=View.GONE
                    }

                }


            } else {

                showToast(this@MainActivity, "Check you Network And try Again ")

            }


        }



    }

    //////////////////////////////////////////////////////////////////


        override fun onCreateOptionsMenu(menu: Menu?): Boolean {

            menuInflater.inflate(R.menu.menu1, menu)
            return true

        }

    //////////////////////////////////////////////////////////////////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            when {
                item.itemId == R.id.History -> {


                    startActivity(Intent(applicationContext, HistoryActivity::class.java));return true

                    }
                //////////////////////////////////////////////////////////////////

                item.itemId == R.id.addStudent -> {



                    startActivity(Intent(applicationContext, addStudentActivity::class.java))

                    return true
                    }

                //////////////////////////////////////////////////////////////////

                item.itemId == R.id.absentsOrPresents -> {

                    if (ReadOrNo) {
                        Thread{
                        viewModel.checkShared(viewModel.absentsOrPresents.value!!)
                        databaseReference.removeEventListener(valueEventListener())
                        databaseReference.addValueEventListener(valueEventListener())
                        }.start()
                    }else{
                        showToast(this@MainActivity,"start the section First")
                    }
                    return true
                }

                //////////////////////////////////////////////////////////////////

                else -> return false
            }

        }

    override fun onPause() {
        super.onPause()
        databaseReference.removeEventListener(valueEventListener())
        databaseReference2.removeEventListener(viewModel.valueEventTrue())

    }
}


