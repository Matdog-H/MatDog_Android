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
import com.example.matdog.api.ListAllData
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import com.example.matdog.main.dog_protect.Detail_Protect_Activity
import com.example.matdog.main.dog_shelter.Detail_Shelter_Activity
import kotlinx.android.synthetic.main.activity_fragment_new.*


class Fragment_Shelter_New(i: Int) : Fragment(), View.OnClickListener {

    // 뷰페이저 어댑터에서 받아온 상태값
    private var status_list = i

    private lateinit var FNrecyclerview: RecyclerView
    var myadapter1: rv_Adapter = rv_Adapter(R.layout.list_item)

    var rv_datalist = ArrayList<ArrayList<ListItem>>()

    //서버에서 registerIdx 필요
    var myData: ArrayList<ListAllData> = arrayListOf()
    var post_registerIdx = ArrayList<Int>() //아이디값 저장 리스트


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.activity_fragment_new, container, false)

        //-------server--------------
        val callNewList = UserServiceImpl.ListService.listResponse_new() // 보호소 : 0
        val callNewList_Spot = UserServiceImpl.ListService.listResponse_new_spot() // 임시보호 : 1
        val callNewList_Lost = UserServiceImpl.ListService.listResponse_new_lost() // 실종 : 2
        val callNewList_result = UserServiceImpl.ListService.list_register_result(2, "포메")

        //리스트 띄우기
        if (status_list == 0) {
            callNewList.safeEnqueue {
                if (it.isSuccessful) {
                    myData = it.body()!!.listdata
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
                        post_registerIdx.add(myData[i].registerIdx)
                    }
                    myadapter1.data = List_new
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        } else if (status_list == 1) {
            callNewList_Spot.safeEnqueue {
                if (it.isSuccessful) {
                    myData = it.body()!!.listdata
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
                        post_registerIdx.add(myData[i].registerIdx)
                    }
                    myadapter1.data = List_new_spot
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        } else if (status_list == 2) {
            callNewList_Lost.safeEnqueue {
                if (it.isSuccessful) {
                    myData = it.body()!!.listdata
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
                        post_registerIdx.add(myData[i].registerIdx)
                    }
                    myadapter1.data = List_new_lost
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        } else if (status_list == 3) {
            callNewList_result.safeEnqueue {
                if (it.isSuccessful) {
                    myData = it.body()!!.listdata
                    var List_result_new = arrayListOf<ListItem>()
                    for (i in 0 until myData.size) {
                        List_result_new.add(
                            ListItem(
                                it_species = myData[i].kindCd,
                                it_status = myData[i].registerStatus,
                                it_gender = myData[i].sexCd,
                                it_age = myData[i].age,
                                it_date = myData[i].happenDt,
                                it_image = myData[i].filename
                            )
                        )
                        post_registerIdx.add(myData[i].registerIdx)
                    }
                    myadapter1.data = List_result_new
                    myadapter1.notifyDataSetChanged()
                    rv_datalist.add(myadapter1.data)
                }
            }
        }

        //-------------------------------------------------

        FNrecyclerview = view.findViewById(R.id.fn_recyclerview)

        //myadapter1.onItemClick(this)
         myadapter1.setItemClickListener(object :rv_Adapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                Log.d("SSS", "${position}번 리스트 선택")
                                //상태값에 따라 상세화면 바뀌게
                                if (view?.parent == FNrecyclerview) {
                                    Log.d("SSS", "들어옴")
                                    if (status_list== 1) {//강아지를 찾아주세요(임시보호)
                                        val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
                                        intent.putExtra("registerIdx",post_registerIdx[position])
                                        startActivity(intent)
                                    }
                                    else if (status_list == 2) {//주인을 찾아주세요(실종)
                                        val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
                                        intent.putExtra("registerIdx",post_registerIdx[position])
                                        startActivity(intent)
                                    }
                                    else if(status_list==3){//추천공고보기
                                        val intent: Intent = Intent(getActivity(), Detail_Shelter_Activity::class.java)
                                        intent.putExtra("registerIdx",post_registerIdx[position])
                                        startActivity(intent)
                                    }
                                    else{//분양공고보기
                                        val intent: Intent = Intent(getActivity(), Detail_Shelter_Activity::class.java)
                                        intent.putExtra("registerIdx",post_registerIdx[position])
                                        startActivity(intent)
                                    }
                                }
                            }
                        })


        FNrecyclerview.adapter = myadapter1

        //리사이클러뷰 배치
        FNrecyclerview.layoutManager = GridLayoutManager(activity, 2)

        if (myadapter1.itemCount == 0) {
            Log.v("999--------------", "999----------------")
            Log.v("9999999++++++++99", "9999++++++++++999999")
//                Img_nolist_New.setVisibility(View.VISIBLE)
//                Tv_nolist_New.setVisibility(View.VISIBLE)
        }

        myadapter1.notifyDataSetChanged()
        return view
    }

    override fun onClick(v: View?) {

//        if (v?.parent == FNrecyclerview) {
//            if (status_list == 0 || status_list == 3 || status_list == 6) {
//                val intent: Intent = Intent(getActivity(), Detail_Shelter_Activity::class.java)
////                intent.putExtra(
////                    "register_idx_new_shelter",
////                    myData[FNrecyclerview.getChildAdapterPosition(v)].registerIdx
////                )
//                startActivity(intent)
//            } else if (status_list == 1 || status_list == 4 || status_list == 7) {
//                val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
////                    Log.v("FNrecyclerview.getChildAdapterPosition(v)",FNrecyclerview.getChildAdapterPosition(v).toString())
////                intent.putExtra(
////                    "register_idx_new_protect",
////                    myData[FNrecyclerview.getChildAdapterPosition(v)].registerIdx
////                )
//                startActivity(intent)
//            } else {
//                val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
////                    intent.putExtra(
////                        "register_idx_new_miss",
////                        myData[FNrecyclerview.getChildAdapterPosition(v)].registerIdx
//                        //detailData[FNrecyclerview.getChildAdapterPosition(v)].registerIdx
////                    )
//                startActivity(intent)
//            }
        }
    }

