package com.example.matdog.main.mypage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R

class fragment_like : Fragment(){

    private lateinit var  recyclerview : RecyclerView
    private lateinit var myadapter: Adapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var like_listview=inflater.inflate(R.layout.like_list, container, false)
        var thiscontext = container!!.getContext()


        recyclerview = like_listview.findViewById(R.id.ll_recyclerview)

        //this로 현재 context 전달
        myadapter= Adapter(thiscontext)

        //리사이클러뷰의 어댑터 세팅
        recyclerview.adapter=myadapter

        //리사이클러뷰 배치
        recyclerview.layoutManager= GridLayoutManager(thiscontext,2)


        myadapter.data= listOf(
            ListItem(
                it_image=R.drawable.profile_image,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ),
            ListItem(
                it_image=R.drawable.profile_image,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ),
            ListItem(
                it_image=R.drawable.profile_image,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            )
        )


        myadapter.notifyDataSetChanged()

        return like_listview


    }
}