package com.example.matdog.main.Share_files.List_share

import android.content.Intent
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
import com.example.matdog.main.Share_files.Recyclerview_share.Adapter
import com.example.matdog.main.dog_shelter.Detail_Shelter_Activity
import kotlinx.android.synthetic.main.activity_fragment_age.view.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list.view.*


class Fragment_Shelter_Age : Fragment() {

    private lateinit var recyclerview: RecyclerView
    private lateinit var myadapter: Adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var falistview = inflater.inflate(R.layout.activity_fragment_age, container, false)
        var thiscontext = container!!.getContext()


        recyclerview = falistview.findViewById(R.id.fa_recyclerview)

        //this로 현재 context 전달
        myadapter = Adapter(thiscontext)

        //리사이클러뷰의 어댑터 세팅
        recyclerview.adapter = myadapter

        //리사이클러뷰 배치
        recyclerview.layoutManager = GridLayoutManager(thiscontext, 2)


//        if (myadapter!= null){
//            if (myadapter.getItemCount() > 0){
//                // implement your work
//            }else {
//                // do whatever you want on empty list adapter
//                falistview.Img_nolist.setVisibility(View.VISIBLE)
//                falistview.Tv_nolist.setVisibility(View.VISIBLE)
//            }
//        }

        if(myadapter!=null){
            if(myadapter.getItemCount()==0){
                falistview.Img_nolist.setVisibility(View.VISIBLE)
                falistview.Tv_nolist.setVisibility(View.VISIBLE)
            }
        }


        return falistview
    }


}