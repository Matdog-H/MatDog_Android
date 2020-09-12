package com.example.matdog.main.Share_files.List_share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import kotlinx.android.synthetic.main.activity_fragment_new.*
import kotlinx.android.synthetic.main.list_item.*


class Fragment_Shelter_New : Fragment(), View.OnClickListener{

    private lateinit var  FNrecyclerview : RecyclerView
    var myadapter1: rv_Adapter = rv_Adapter(R.layout.list_item)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.activity_fragment_new, container, false)
        var thiscontext = container!!.getContext()
        Log.v("999999999","9999999999")
        FNrecyclerview = view.findViewById(R.id.fn_recyclerview)
        Log.v("@@@@@@@@@@","@@@@@@@@@@@@@22222")

        Log.v("9#############39","###########39")
        myadapter1.onItemClick(this)
        FNrecyclerview.adapter=myadapter1
        Log.v("리스트뷰에서 확인할 것",FNrecyclerview.adapter.toString())

        //리사이클러뷰 배치
        FNrecyclerview.layoutManager= GridLayoutManager(activity,2)
        myadapter1.notifyDataSetChanged()
        //리사이클러뷰의 어댑터 세팅

        Log.v("아이템 갯수.. 확인하기",myadapter1.itemCount.toString()) // 0개로 뜬다.. 이유가 뭐지?
        //일단 Fragment에서 추가된 것들이 못들어왔다는 것 같은데.. 왜 못들어왔을까?

//        if(myadapter1.itemCount==0){
//            Log.v("999--------------","999----------------")
//                Log.v("9999999++++++++99","9999++++++++++999999")
////                Img_nolist_New.setVisibility(View.VISIBLE)
////                Tv_nolist_New.setVisibility(View.VISIBLE)
//        }

        myadapter1.notifyDataSetChanged()
        return view
    }

    override fun onClick(v: View?) {

        FNrecyclerview = view?.findViewById(R.id.fn_recyclerview)!!

        if (v?.parent == FNrecyclerview){
            val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
            //intent.putExtra("matchingIdx", matchingData[rv.getChildAdapterPosition(v)].matchingIdx)
            startActivity(intent)
        }

    }

}