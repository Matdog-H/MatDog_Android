package com.example.matdog.main.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.Share_files.Recyclerview_share.rv_ViewHolder

class mp_Adapter(mp_item: Int) : RecyclerView.Adapter<mp_ViewHolder>(){
    var mp_data= arrayListOf<ListItem>()
    private var onItemClickListener: View.OnClickListener? = null
    val mp_item=mp_item


    //뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mp_ViewHolder {
        //리스트 아이템 인플레이터
        val view = LayoutInflater.from(parent.context).inflate(mp_item,parent,false)
        view.setOnClickListener(onItemClickListener)
        return mp_ViewHolder(view)
    }
    //아이템 사이즈
    override fun getItemCount(): Int {
        return mp_data.size

//        if(data == null){
//            return  0
//        }else {
//            return data.size
//        }
    }

    //바인드뷰홀더 생성
    override fun onBindViewHolder(holder: mp_ViewHolder, position: Int) {
        holder.bind(mp_data[position])
    }

    fun onItemClick(l: View.OnClickListener){
        onItemClickListener = l
    }


}