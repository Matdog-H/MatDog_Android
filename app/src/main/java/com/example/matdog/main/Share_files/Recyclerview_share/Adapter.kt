package com.example.matdog.main.Share_files.Recyclerview_share

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R

// 1.람다식 itemClick
//class Adapter(private val context: Context, val itemClick:(ListItem)->Unit) : RecyclerView.Adapter<ViewHolder>(){
class Adapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>(){

    //데이터 저장할 아이템리스트
    var data= listOf<ListItem>()
    private var onItemClickListener: View.OnClickListener? = null



    //뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //리스트 아이템 인플레이터
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false)
       view.setOnClickListener(onItemClickListener)
        return ViewHolder(view)
    }

    //아이템 사이즈
    override fun getItemCount(): Int {

        //return data.size

        if(data == null){
            return  0
        }else {
            return data.size
        }

    }


    //바인드뷰홀더 생성
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun onItemClick(l: View.OnClickListener){
        onItemClickListener = l
    }




}