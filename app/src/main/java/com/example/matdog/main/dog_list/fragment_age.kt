package com.example.matdog.main.dog_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.main.dog_lost.DetailActivity
import com.example.matdog.main.mypage.Adapter
import com.example.matdog.main.mypage.ListItem
import kotlinx.android.synthetic.main.activity_fragment_age.*
import kotlinx.android.synthetic.main.activity_list.*


class fragment_age : Fragment(){

    private lateinit var  recyclerview : RecyclerView
    private lateinit var myadapter: Adapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var falistview= inflater.inflate(R.layout.activity_fragment_age, container, false)
        var thiscontext = container!!.getContext()



        recyclerview = falistview.findViewById(R.id.fa_recyclerview)

        //this로 현재 context 전달
        myadapter= Adapter(thiscontext)

        //리사이클러뷰의 어댑터 세팅
        recyclerview.adapter=myadapter

        //리사이클러뷰 배치
        recyclerview.layoutManager= GridLayoutManager(thiscontext,2)


//        if(myadapter.getItemCount() == null){
//            Img_nolist.setVisibility(View.VISIBLE)
//            Tv_nolist.setVisibility(View.VISIBLE)
//            btn_newpost.setVisibility(View.VISIBLE)
//            btn_rephoto.setVisibility(View.VISIBLE)
////
////            Tv_nolist.isVisible=true
////            btn_newpost.isVisible=true
////            btn_rephoto.isVisible=true
//
//            btn_rephoto.isEnabled=true
//            btn_newpost.isEnabled=true
//        }

        return falistview
    }
}