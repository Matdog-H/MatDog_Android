package com.example.matdog.main.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matdog.R

// 1.람다식 itemClick
//class Adapter(private val context: Context, val itemClick:(ListItem)->Unit) : RecyclerView.Adapter<ViewHolder>(){
class Adapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>(){

    //데이터 저장할 아이템리스트
    var data= listOf<ListItem>()

//    //클릭 인터페이스 정의
//    interface  ItemClickListener{fun onClick(view: View, position: Int)}
//
//    //클릭리스너 선언
//    private lateinit var itemClickLister: ItemClickListener
//
//    //클릭리스너 등록 메소드
//    fun setItemClickListener(ItemClickListener:ItemClickListener){this.itemClickLister=itemClickLister}


    //뷰 홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //리스트 아이템 인플레이터
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item,parent,false)
//        return ViewHolder(view,itemClick)
        return ViewHolder(view)
    }

    //아이템 사이즈
    override fun getItemCount(): Int {
//        if (data == null) {
//            return 0;
//        }
        return data.size
    }

    //바인드뷰홀더 생성
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
//
//        //view에 OnClickListener 달고 , 그 안에서 직접 만든 itemClickLister 연결결
//       holder.itemView.setOnClickListener{itemClickLister.onClick(it,position)}
    }

//
//    inner class Holder(itemView:View?, itemClick: (ListItem) -> Unit):RecyclerView.ViewHolder(itemView!!){
//        // 2.holder에서 클릭에 대한 처리를 할 것이므로 holder에 파라미터에 람다식 itemclick을 넣는다.
//        val dog_image = itemView?.findViewById<ImageView>(R.id.dog_image)
//        val love = itemView?.findViewById<ImageView>(R.id.love)
//        val species = itemView?.findViewById<TextView>(R.id.species)
//        val gender = itemView?.findViewById<ImageView>(R.id.gender)
//        val state = itemView?.findViewById<ImageView>(R.id.state)
//        val dog_age = itemView?.findViewById<TextView>(R.id.dog_age)
//        val date = itemView?.findViewById<TextView>(R.id.date)
//        val dog_place = itemView?.findViewById<TextView>(R.id.dog_place)
//
//        fun bind(item:ListItem,context: Context){
//
//
//
//            species?.text=item.it_species
//            dog_age?.text=item.it_age
//            date?.text=item.it_date
//            dog_place?.text=item.it_place
//
//            itemView.setOnClickListener { itemClick(item) }
//            // 3.itemView가 클릭됐을때 처리할 일을 itemClick으로 설정한다.
//        }
//    }



}