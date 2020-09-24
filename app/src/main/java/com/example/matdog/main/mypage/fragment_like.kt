package com.example.matdog.main.mypage

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
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import com.example.matdog.main.dog_protect.Detail_Protect_Activity
import com.example.matdog.main.dog_shelter.Detail_Shelter_Activity
import kotlinx.android.synthetic.main.list_item.*

class fragment_like() : Fragment(), View.OnClickListener{
    private var token : String = ""
    private lateinit var  FLrecyclerview : RecyclerView
    private var mpadapter1: mp_Adapter = mp_Adapter(R.layout.list_item)
    var mp_datalist = ArrayList<ArrayList<ListItem>>()
    var post_status = ArrayList<Int>() //상태값 저장 배열



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var like_listview=inflater.inflate(R.layout.like_list, container, false)
        var thiscontext = container!!.getContext()

        //-------------server-----------------
        token = SharedPreferenceController.getUserToken(thiscontext)
        val calllikepost = UserServiceImpl.MyListService.listResponse_mylike(token)
        calllikepost.safeEnqueue {
            if(it.isSuccessful){
                val LikePost = it.body()!!.mylistdata
                var my_like_list = arrayListOf<ListItem>()
                for(i in 0 until LikePost.size){
                    my_like_list.add(
                       ListItem(
                           it_species = LikePost[i].kindCd,
                           it_status = LikePost[i].registerStatus,
                           it_gender = LikePost[i].sexCd,
                           it_age = LikePost[i].age,
                           it_date = LikePost[i].happenDt,
                           it_image = LikePost[i].filename
                       )
                   )
                    post_status.add(LikePost[i].registerStatus) // 클릭 아이템 공고 상태값
                }

               mpadapter1.mp_data = my_like_list
               mpadapter1.notifyDataSetChanged()
               mp_datalist.add(mpadapter1.mp_data)

           }
       }

        //-------------server-----------------


        FLrecyclerview = like_listview.findViewById(R.id.ll_recyclerview)

        //this로 현재 context 전달
        //mpadapter1= Adapter(thiscontext)

        //클릭이벤트
        //mpadapter1.onItemClick(this)
        mpadapter1.setItemClickListener(object :mp_Adapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("SSS", "${position}번 리스트 선택")
                //상태값에 따라 상세화면 바뀌게
                if (view?.parent == FLrecyclerview) {
                    if (post_status[position] == 1) {
                        val intent: Intent = Intent(getActivity(), Detail_Shelter_Activity::class.java)
                        intent.putExtra("delete","delete_shelter")
                        startActivity(intent)
                    }
                    else if (post_status[position] == 2) {
                        val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
                        intent.putExtra("delete","delete_miss")
                        startActivity(intent)
                    }
                    else{
                        val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
                        intent.putExtra("delete","delete_protect")
                        startActivity(intent)
                    }
                }
            }
        })


        //리사이클러뷰의 어댑터 세팅
        FLrecyclerview.adapter=mpadapter1

        //리사이클러뷰 배치
        FLrecyclerview.layoutManager= GridLayoutManager(thiscontext,2)



        mpadapter1.notifyDataSetChanged()

        return like_listview

    }
    override fun onClick(v: View?) {

     FLrecyclerview = view?.findViewById(R.id.ll_recyclerview)!!

        //상태값에 따라 상세화면 바뀌게
        if (v?.parent == FLrecyclerview){
//            if(status_post==1){
//                val intent: Intent = Intent(getActivity(), Detail_Shelter_Activity::class.java)
//                intent.putExtra("delete","delete_shelter")
//                startActivity(intent)
//            }
//            else if(status_post==2){
//                val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
//                intent.putExtra("delete","delete_miss")
//                startActivity(intent)
//            }
//            else{
//                val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
//                intent.putExtra("delete","delete_protect")
//                startActivity(intent)
//            }

        }
    }
}