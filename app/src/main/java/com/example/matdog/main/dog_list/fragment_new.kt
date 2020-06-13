package com.example.matdog.main.dog_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.main.mypage.Adapter
import com.example.matdog.main.mypage.ListItem


class fragment_new : Fragment(){

    private lateinit var  recyclerview : RecyclerView
    private lateinit var myadapter: Adapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var fnlistview= inflater.inflate(R.layout.activity_fragment_new, container, false)
        var thiscontext = container!!.getContext()


        recyclerview = fnlistview.findViewById(R.id.fn_recyclerview)

        //this로 현재 context 전달
        myadapter= Adapter(thiscontext)

        //리사이클러뷰의 어댑터 세팅
        recyclerview.adapter=myadapter

        //리사이클러뷰 배치
        recyclerview.layoutManager= GridLayoutManager(thiscontext,2)

        myadapter.data= listOf(
            ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_protect,
                it_gender =R.drawable.gender_man,
                it_date = "2020.05.31",
                it_place="경기도 고양시"
            ),
            ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "4살 추정",
                it_state =R.drawable.state_sighting,
                it_gender =R.drawable.gender_woman,
                it_date = "2020.06.11",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "4살 추정",
                it_state =R.drawable.state_sighting,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "5살 추정",
                it_state =R.drawable.state_sighting,
                it_gender =R.drawable.gender_woman,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_woman,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            ), ListItem(
                it_image=R.drawable.profile_image,
                it_love = R.drawable.ic_love,
                it_species ="말티즈",
                it_age = "3살 추정",
                it_state =R.drawable.state_shelter,
                it_gender =R.drawable.gender_man,
                it_date = "2020.06.12",
                it_place="경기도 고양시"
            )

        )


        return fnlistview
    }
}