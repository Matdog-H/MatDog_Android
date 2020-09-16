package com.example.matdog.main.Share_files.Recyclerview_share

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.matdog.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val dog_image: ImageView = view.findViewById(R.id.dog_image)
    //val love: ImageView = view.findViewById(R.id.love)
    val species: TextView = view.findViewById(R.id.species)
    val gender: ImageView = view.findViewById(R.id.gender)
    val state: ImageView = view.findViewById(R.id.state)
    val dog_age: TextView = view.findViewById(R.id.dog_age)
    //val dog_place: TextView = view.findViewById(R.id.dog_place)
    val date: TextView = view.findViewById(R.id.date)


    fun bind(data: ListItem){
        Glide.with(itemView)
            .load(data.it_image)
            .into(dog_image)

//        Glide.with(itemView)
//            .load(data.it_love)
//            .into(love)

        Glide.with(itemView)
            .load(data.it_gender)
            .into(gender)

        Glide.with(itemView)
            .load(data.it_status)
            .into(state)

        dog_age.text=data.it_age.toString()
        species.text=data.it_species
        date.text=data.it_date
        //dog_place.text=data.it_place


    }



//    fun onClick(view: View)
//    {
//        when(view.getId()) {
//            R.id.love->love.setImageResource(R.drawable.ic_love_2)
//        }
//    }




}