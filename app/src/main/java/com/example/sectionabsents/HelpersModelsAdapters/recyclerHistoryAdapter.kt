package com.example.sectionabsents.HelpersModelsAdapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sectionabsents.Activities.History2Activity
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheDateOfTheHistory
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.TheNameOfTheHistory
import com.example.sectionabsents.R

class recyclerHistoryAdapter(var context: Context,var array:ArrayList<TimeAndName>):RecyclerView.Adapter<recyclerHistoryAdapter.viewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        var view=LayoutInflater.from(context).inflate(R.layout.recycler_history,parent,false)
        return viewHolder(view)
    }

    //////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int {
        return array.size
    }

    //////////////////////////////////////////////////////////////////

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        var pos=array[position]
        holder.text1.text=pos.name
        holder.text2.text=pos.time
        //////////////////////////////////////////////////////////////////
        holder.itemView.setOnClickListener {
            var intent=Intent(context, History2Activity::class.java)
            intent.putExtra(TheNameOfTheHistory,holder.text1.text.toString())
            intent.putExtra(TheDateOfTheHistory,holder.text2.text.toString())
            context.startActivity(intent)

        }
    }

    //////////////////////////////////////////////////////////////////

    inner class viewHolder(view: View):RecyclerView.ViewHolder(view){

        var text1=view.findViewById<TextView>(R.id.text_recycler)
        var text2=view.findViewById<TextView>(R.id.id_recycler)

    }

}