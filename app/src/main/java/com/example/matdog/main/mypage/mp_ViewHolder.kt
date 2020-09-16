package com.example.matdog.main.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import kotlinx.android.synthetic.main.list_item.view.*


class mp_ViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val mp_img : ImageView =view.findViewById(R.id.dog_image)
    val mp_species : TextView =view.findViewById(R.id.species)
    val mp_gender : ImageView = view.findViewById(R.id.gender)
    val mp_state : ImageView = view.findViewById(R.id.state)
    val mp_dog_age : TextView = view.findViewById(R.id.dog_age)
    val mp_date : TextView = view.findViewById(R.id.date)


    fun bind(data:ListItem){
        //강아지 이미지
        Glide.with(itemView)
            .load(data.it_image)
            .into(mp_img)

        //성별
        var dog_gender : Int=data.it_gender
        if(dog_gender==0){
            Glide.with(itemView)
                .load(R.drawable.gender_man)
                .into(mp_gender)
        }else{
            Glide.with(itemView)
                .load(R.drawable.gender_woman)
                .into(mp_gender)
        }

        //등록상태
        var register_state : Int=data.it_status
        if(register_state==0){
            Glide.with(itemView)
                .load(R.drawable.state_shelter)
                .into(mp_state)
        }else if(register_state==1){
            Glide.with(itemView)
                .load(R.drawable.state_missing)
                .into(mp_state)
        }else{
            Glide.with(itemView)
                .load(R.drawable.state_missing)
                .into(mp_state)
        }

        //품종, 나이, 등록일
        mp_species.text=data.it_species
        mp_dog_age.text=data.it_age.toString()
        mp_date.text=data.it_date

    }
}