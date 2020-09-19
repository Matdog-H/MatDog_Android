package com.example.matdog.main.Share_files.List_share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import kotlinx.android.synthetic.main.activity_fragment_age.*


class Fragment_Shelter_Age(i:Int) : Fragment(), View.OnClickListener {

    // 뷰페이저 어댑터에서 받아온 상태값
    private var status_list_age = i

    private lateinit var FArecyclerview: RecyclerView
    var myadapter2: rv_Adapter = rv_Adapter(R.layout.list_item)

    var rv_datalist = ArrayList<ArrayList<ListItem>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view= inflater.inflate(R.layout.activity_fragment_age, container, false)

        //-------server-----------------------------------------
        val callAgeList = UserServiceImpl.ListService.listResponse_age()
        val callAgeList_Spot = UserServiceImpl.ListService.listResponse_age_spot() // 임시보호 : 1
        val callAgeList_Lost = UserServiceImpl.ListService.listResponse_age_lost() // 실종 : 2

        //리스트 띄우기
        if(status_list_age==0) {
            callAgeList.safeEnqueue {
                if (it.isSuccessful) {
                    val myData = it.body()!!.listdata
                    var List_age = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_age.add(
                            ListItem(
                                it_species = myData[i].kindCd,
                                it_status = myData[i].registerStatus,
                                it_gender = myData[i].sexCd,
                                it_age = myData[i].age,
                                it_date = myData[i].happenDt,
                                it_image = myData[i].filename
                            )
                        )
                    }
                    myadapter2.data = List_age
                    myadapter2.notifyDataSetChanged()
                    rv_datalist.add(myadapter2.data)
                }
            }
        } else if(status_list_age==1){
            callAgeList_Spot.safeEnqueue {
                if (it.isSuccessful) {
                    val myData = it.body()!!.listdata
                    var List_age_spot = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_age_spot.add(
                            ListItem(
                                it_species = myData[i].kindCd,
                                it_status = myData[i].registerStatus,
                                it_gender = myData[i].sexCd,
                                it_age = myData[i].age,
                                it_date = myData[i].happenDt,
                                it_image = myData[i].filename
                            )
                        )
                    }
                    myadapter2.data = List_age_spot
                    myadapter2.notifyDataSetChanged()
                    rv_datalist.add(myadapter2.data)
                }
            }
        } else {
            callAgeList_Lost.safeEnqueue {
                if (it.isSuccessful) {
                    val myData = it.body()!!.listdata
                    var List_age_lost = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_age_lost.add(
                            ListItem(
                                it_species = myData[i].kindCd,
                                it_status = myData[i].registerStatus,
                                it_gender = myData[i].sexCd,
                                it_age = myData[i].age,
                                it_date = myData[i].happenDt,
                                it_image = myData[i].filename
                            )
                        )
                    }
                    myadapter2.data = List_age_lost
                    myadapter2.notifyDataSetChanged()
                    rv_datalist.add(myadapter2.data)
                }
            }
        }
        
        //----------------------------------------------------------
        FArecyclerview = view.findViewById(R.id.fa_recyclerview)

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