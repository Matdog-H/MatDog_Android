package com.example.matdog.main.Share_files.Recyclerview_share

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.ListAllData

class rv_ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val dog_image: ImageView = view.findViewById(R.id.dog_image)
    val species: TextView = view.findViewById(R.id.species)
    val list_gender: ImageView = view.findViewById(R.id.gender)
    val state: ImageView = view.findViewById(R.id.state)
    val dog_age: TextView = view.findViewById(R.id.dog_age)
    val date: TextView = view.findViewById(R.id.date)


    fun bind(data: ListItem){
        Glide.with(itemView)
            .load(data.it_image)
            .into(dog_image)

        Glide.with(itemView)
            .load(R.drawable.gender_man)
            .into(list_gender)
        var gender : String = data.it_gender
        if(gender == "M" || gender == "1"){
            Glide.with(itemView)
                .load(R.drawable.gender_man)
                .into(list_gender)
        }else{
            Glide.with(itemView)
                .load(R.drawable.gender_woman)
                .into(list_gender)
        }

        //추후에 0,1,2에 따른 상황 수정필요
        var registerStatus : Int = data.it_status
        if(registerStatus==0){
            Glide.with(itemView)
                .load(R.drawable.state_shelter)
                .into(state)
        }
        else if(registerStatus==1){
            Glide.with(itemView)
                .load(R.drawable.state_missing)
                .into(state)
        }
        else{
            Glide.with(itemView)
                .load(R.drawable.state_protect)
                .into(state)
        }

        dog_age.text= data.it_age.toString()
        species.text=data.it_species
        date.text=data.it_date
    }


}