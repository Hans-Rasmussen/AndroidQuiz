package com.example.dummyproject.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dummyproject.R
import com.example.dummyproject.model.Category

class CategoryRecyclerAdapter(var myList: List<Category>): RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView1.text = myList[position].id.toString()
        holder.textView2.text = myList[position].name
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //var itemImage: ImageView = itemView.findViewById(R.id.image_check)
        var textView1: TextView = itemView.findViewById(R.id.row_text1)
        var textView2: TextView = itemView.findViewById(R.id.row_text2)

    }

}