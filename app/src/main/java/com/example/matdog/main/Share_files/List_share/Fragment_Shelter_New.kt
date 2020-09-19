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
import kotlinx.android.synthetic.main.activity_fragment_new.*


class Fragment_Shelter_New(i: Int) : Fragment(), View.OnClickListener{

    // 뷰페이저 어댑터에서 받아온 상태값
    private var status_list = i

    private lateinit var  FNrecyclerview : RecyclerView
    var myadapter1: rv_Adapter = rv_Adapter(R.layout.list_item)

    var rv_datalist = ArrayList<ArrayList<ListItem>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.activity_fragment_new, container, false)

        //-------server--------------
        val callNewList = UserServiceImpl.ListService.listResponse_new() // 보호소 : 0
        val callNewList_Spot = UserServiceImpl.ListService.listResponse_new_spot() // 임시보호 : 1
        val callNewList_Lost = UserServiceImpl.ListService.listResponse_new_lost() // 실종 : 2

        //리스트 띄우기
        if(status_list==0) {
            callNewList.safeEnqueue {
                if (it.isSuccessful) {
                    val myData = it.body()!!.listdata
                    var List_new = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_new.add(
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
                    myadapter1.data = List_new
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        } else if(status_list==1){
            callNewList_Spot.safeEnqueue {
                if (it.isSuccessful) {
                    val myData = it.body()!!.listdata
                    var List_new_spot = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_new_spot.add(
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
                    myadapter1.data = List_new_spot
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        } else {
            callNewList_Lost.safeEnqueue {
                if (it.isSuccessful) {
                    val myData = it.body()!!.listdata
                    var List_new_lost = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_new_lost.add(
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
                    myadapter1.data = List_new_lost
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        }

        //-------------------------------------------------

        FNrecyclerview = view.findViewById(R.id.fn_recyclerview)

        myadapter1.onItemClick(this)
        FNrecyclerview.adapter=myadapter1

        //리사이클러뷰 배치
        FNrecyclerview.layoutManager= GridLayoutManager(activity,2)

        if(myadapter1.itemCount==0){
            Log.v("999--------------","999----------------")
                Log.v("9999999++++++++99","9999++++++++++999999")
//                Img_nolist_New.setVisibility(View.VISIBLE)
//                Tv_nolist_New.setVisibility(View.VISIBLE)
        }

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