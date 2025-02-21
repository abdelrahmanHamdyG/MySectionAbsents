package com.example.sectionabsents.HelpersModelsAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sectionabsents.R

class recyclerAdapterStudentsInHistory(var context: Context, var arr: Array<modelData>): RecyclerView.Adapter<recyclerAdapterStudentsInHistory.viewHolder2>()  {

    //////////////////////////////////////////////////////////////////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder2 {


        var view= LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false)
        return viewHolder2(view)

    }

    //////////////////////////////////////////////////////////////////

    override fun getItemCount(): Int {
        return arr.size
    }

    //////////////////////////////////////////////////////////////////


    override fun onBindViewHolder(holder: viewHolder2, position: Int) {

        var position1 = arr[position]
        holder.text_view.text = position1.name
        holder.id.text = position1.id


    }

    //////////////////////////////////////////////////////////////////


    inner class  viewHolder2(view: View): RecyclerView.ViewHolder(view){

        var text_view=view.findViewById<TextView>(R.id.text_recycler)
        var id=view.findViewById<TextView>(R.id.id_recycler)

    }
}