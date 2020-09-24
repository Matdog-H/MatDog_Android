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
import com.example.matdog.api.MyListAllData
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import com.example.matdog.main.dog_protect.Detail_Protect_Activity
import com.example.matdog.main.dog_shelter.Detail_Shelter_Activity


class fragment_my() : Fragment(), View.OnClickListener{
    private var token : String = ""
    private lateinit var  FMrecyclerview : RecyclerView
    private var mpadapter2: mp_Adapter = mp_Adapter(R.layout.list_item)
    var mp_datalist = ArrayList<ArrayList<ListItem>>()
    var post_status = ArrayList<Int>()
    var post_registerIdx = ArrayList<Int>() //아이디값 저장 리스트


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mylistview= inflater.inflate(R.layout.my_list, container, false)
        var thiscontext = container!!.getContext()


       //--------server---------
        token = SharedPreferenceController.getUserToken(thiscontext)
        val callmypost = UserServiceImpl.MyListService.listResponse_mywrite(token)
       callmypost.safeEnqueue {
           if(it.isSuccessful) {
               val myPost = it.body()!!.mylistdata
               var my_write_List = arrayListOf<ListItem>()

               for (i in 0 until myPost.size) {
                   my_write_List.add(
                       ListItem(
                           it_species = myPost[i].kindCd,
                           it_status = myPost[i].registerStatus,
                           it_gender = myPost[i].sexCd,
                           it_age = myPost[i].age,
                           it_date = myPost[i].happenDt,
                           it_image = myPost[i].filename
                       )
                   )
                   post_status.add(myPost[i].registerStatus) // 클릭 아이템 공고 상태값
                   post_registerIdx.add(myPost[i].registerIdx) //클릭 아이템 공고 아이디 값 저장
               }

               mpadapter2.mp_data = my_write_List
               mpadapter2.notifyDataSetChanged()
               mp_datalist.add(mpadapter2.mp_data)



           }
       }

       //--------server---------




       FMrecyclerview = mylistview.findViewById(R.id.ml_recyclerview)

       //this로 현재 context 전달
       //mpadapter2= Adapter(thiscontext)

       //mpadapter2.onItemClick(this)
        mpadapter2.setItemClickListener(object :mp_Adapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {
                Log.d("SSS", "${position}번 리스트 선택")
                //상태값에 따라 상세화면 바뀌게
                if (view?.parent == FMrecyclerview) {
                    if (post_status[position] == 1) {
                        val intent: Intent = Intent(getActivity(), Detail_Shelter_Activity::class.java)
                        intent.putExtra("delete","delete_shelter")
                        intent.putExtra("registerIdx",post_registerIdx[position])
                        startActivity(intent)
                    }
                    else if (post_status[position] == 2) {
                        val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
                        intent.putExtra("delete","delete_miss")
                        intent.putExtra("registerIdx",post_registerIdx[position])
                        startActivity(intent)
                    }
                    else{
                        val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
                        intent.putExtra("delete","delete_protect")
                        intent.putExtra("registerIdx",post_registerIdx[position])
                        startActivity(intent)
                    }
                }
            }
        })
       //리사이클러뷰의 어댑터 세팅
       FMrecyclerview.adapter=mpadapter2

       //리사이클러뷰 배치
       FMrecyclerview.layoutManager= GridLayoutManager(thiscontext,2)


//       mpadapter2.mp_data= arrayListOf(
//           ListItem(
//               it_image = R.drawable.taepoong,
//               it_species = "말티즈",
//               it_age = "3",
//               it_status = R.drawable.state_shelter,
//               it_gender = "1",
//               it_date = "2020.06.12"
//           )
//       )


       //클릭 리스너 등록
//       myadapter.setItemClickListener(object : Adapter.ItemClickListener{
//           override fun onClick(view:View,position:Int){
//               Log.d("Lss","${position}번 리스트 선택")
//           }
//       })

       mpadapter2.notifyDataSetChanged()



       return mylistview

    }


    override fun onClick(v: View?) {
        FMrecyclerview = view?.findViewById(R.id.ml_recyclerview)!!

//        상태값에 따라 상세화면 바뀌게
//        if (v?.parent == FMrecyclerview){
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
//
//        }

    }

}