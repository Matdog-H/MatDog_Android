package com.example.matdog.main.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R

class Adapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>(){

    var data= listOf<ListItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
//        if (data == null) {
//            return 0;
//        }
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}