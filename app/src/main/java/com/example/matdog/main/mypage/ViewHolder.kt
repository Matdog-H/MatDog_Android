package com.example.matdog.main.mypage

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matdog.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val dog_image: ImageView = view.findViewById(R.id.dog_image)
    val species: TextView = view.findViewById(R.id.species)
    val gender: ImageView = view.findViewById(R.id.gender)
    val state: ImageView = view.findViewById(R.id.state)
    val dog_age: TextView = view.findViewById(R.id.dog_age)
    val dog_place: TextView = view.findViewById(R.id.dog_place)
    val date: TextView = view.findViewById(R.id.date)

//
//
//    val it_image: Int,
//    val it_species: String,
//    val it_state :Int,
//    val it_gender:Int,
//    val it_age : String,
//    val it_date: String,
//    val it_place: String


//    fun bind(data:Item){
//
//        Glide.with(itemView)
//            .load(data.it_image)
//            .into(dog_pic)
//        //dog_pic.setImageDrawable(data.it_image)
//        species.text=data.it_species
//        age.text=data.it_age
//        feature.text=data.it_feature
//        place.text=data.it_place
//    }

    fun bind(data:ListItem){
        Glide.with(itemView)
            .load(data.it_image)
            .into(dog_image)

        Glide.with(itemView)
            .load(data.it_gender)
            .into(gender)

        Glide.with(itemView)
            .load(data.it_state)
            .into(state)

        dog_age.text=data.it_age
        species.text=data.it_species
        date.text=data.it_date
        dog_place.text=data.it_place

    }


}