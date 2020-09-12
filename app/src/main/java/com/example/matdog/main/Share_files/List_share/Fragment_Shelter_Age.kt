package com.example.matdog.main.Share_files.List_share

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.dog_miss.Detail_Miss_Activity


class Fragment_Shelter_Age : Fragment(), View.OnClickListener {

    private lateinit var FArecyclerview: RecyclerView
    var myadapter2: rv_Adapter = rv_Adapter(R.layout.list_item)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.activity_fragment_age, container, false)
        //var thiscontext = container!!.getContext()

        FArecyclerview = view.findViewById(R.id.fa_recyclerview)

        //this로 현재 context 전달
        //myadapter=Adapter(thiscontext,listAllData)

        myadapter2.onItemClick(this)

        //리사이클러뷰의 어댑터 세팅
        FArecyclerview.adapter=myadapter2

        //리사이클러뷰 배치
        FArecyclerview.layoutManager= GridLayoutManager(activity,2)


        if(myadapter2!=null){
            if(myadapter2.getItemCount()==0){
//                Img_nolist_AGE.setVisibility(View.VISIBLE)
//                Tv_nolist_AGE.setVisibility(View.VISIBLE)
            }
        }

        myadapter2.notifyDataSetChanged()
        return view
    }

    override fun onClick(v: View?) {

        FArecyclerview = view?.findViewById(R.id.fa_recyclerview)!!

        if (v?.parent == FArecyclerview){
            val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
            //intent.putExtra("matchingIdx", matchingData[rv.getChildAdapterPosition(v)].matchingIdx)
            startActivity(intent)
        }

    }

}