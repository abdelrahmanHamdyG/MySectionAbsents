package com.example.sectionabsents.HelpersModelsAdapters
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sectionabsents.HelpersModelsAdapters.helper.Companion.log
import com.example.sectionabsents.R

class recyclerAdapter(var context:Context, var arr: ArrayList<modelData>): RecyclerView.Adapter<recyclerAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        var view=LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false)
        return viewHolder(view)

    }

    override fun getItemCount(): Int {

            return arr.size

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        var position1 = arr[position]
        holder.text_view.text = position1.name
        holder.id.text = position1.id


    }



    inner class  viewHolder(view: View):RecyclerView.ViewHolder(view){

        var text_view=view.findViewById<TextView>(R.id.text_recycler)
        var id=view.findViewById<TextView>(R.id.id_recycler)

    }
    }

