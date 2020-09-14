package com.example.matdog.main.Share_files.Recyclerview_share

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R

// 1.람다식 itemClick
//class Adapter(private val context: Context, val itemClick:(ListItem)->Unit) : RecyclerView.Adapter<ViewHolder>(){
class rv_Adapter(rv_item: Int) : RecyclerView.Adapter<rv_ViewHolder>(){
//private val context: Context, private var listAllData: ArrayList<ListAllData>
    //데이터 저장할 아이템리스트
    var data= arrayListOf<ListItem>()
    private var onItemClickListener: View.OnClickListener? = null
    val rv_item=rv_item

    //뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rv_ViewHolder {
        //리스트 아이템 인플레이터
        val view = LayoutInflater.from(parent.context).inflate(rv_item,parent,false)
        view.setOnClickListener(onItemClickListener)
        return rv_ViewHolder(view)
    }

    //아이템 사이즈
    override fun getItemCount(): Int {
        return data.size
        Log.v("아이템 사이즈 크기는????????????????",data.size.toString())

//        if(data == null){
//            return  0
//        }else {
//            return data.size
//        }
    }

    //바인드뷰홀더 생성
    override fun onBindViewHolder(holder: rv_ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    fun onItemClick(l: View.OnClickListener){
        onItemClickListener = l
    }




}