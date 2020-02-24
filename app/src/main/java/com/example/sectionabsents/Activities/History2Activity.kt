package com.example.sectionabsents.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sectionabsents.RoomDataBase.DAO
import com.example.sectionabsents.RoomDataBase.DataBase
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheDateOfTheHistory
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheNameOfTheHistory
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.log
import com.example.sectionabsents.R
import com.example.sectionabsents.ViewModels.ViewModelHistory2
import com.example.sectionabsents.HelpersModelsAdapters.recyclerAdapterStudentsInHistory
import kotlinx.android.synthetic.main.activity_history2.*

class History2Activity : AppCompatActivity() {

    lateinit var dao: DAO
    lateinit var viewModel: ViewModelHistory2
    var type:String="absents"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history2)


        var i=intent.extras!!.getString(TheNameOfTheHistory)!!
        var i2=intent.extras!!.getString(TheDateOfTheHistory)

        //////////////////////////////////////////////////////////////////

        viewModel = ViewModelProviders.of(this).get(ViewModelHistory2::class.java)
        dao= DataBase.getDataBase(this@History2Activity).returnDao()
        viewModel.changeType(type)
        viewModel.typeM.observe(this, Observer{

            if (it=="absents"){
                log("we are getting absents history data")
                viewModel.getAbsentsData(i,dao)
                type="absents"

            }else{
                viewModel.getPresentsData(i,dao)
                type="presents"

            }

            //////////////////////////////////////////////////////////////////
            viewModel.listOfHistory.observe(this, Observer {it2->

                supportActionBar!!.title="$i $i2 ${it2.size} $type"
            recyclerHistory2.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
            recyclerHistory2.adapter=
                recyclerAdapterStudentsInHistory(
                    this,
                    it2
                )

            })
        })





}

    //////////////////////////////////////////////////////////////////

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu2, menu)
        return true
    }
    //////////////////////////////////////////////////////////////////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //////////////////////////////////////////////////////////////////

        when {
            /////////////////////////////////////////////////////////////////
            item.itemId == R.id.absentsOrPresents -> {
                if (type=="absents"){
                    type="presents"
                }else{
                    type="absents"
                }
                viewModel.changeType(type)

                return true
            }

            //////////////////////////////////////////////////////////////////

            item.itemId== R.id.Section ->{
                startActivity(Intent(this,MainActivity::class.java))
                return true
            }

            //////////////////////////////////////////////////////////////////

            else->return false
        }

    }

}
