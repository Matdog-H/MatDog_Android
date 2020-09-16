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


class Fragment_Shelter_Age : Fragment(), View.OnClickListener {

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

        callAgeList.safeEnqueue {
            if(it.isSuccessful){
                //추후에 내부에서 status값에 따라 분리하는 코드 작성 요함.
                val myData = it.body()!!.listdata
                Log.v("myData의 사이즈 체크", myData.size.toString())
                var List_age = arrayListOf<ListItem>()
                for(i in 0 until myData.size) {
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
                    Log.v("item값 체크하기",myData[i].happenDt)
                }
                myadapter2.data = List_age
                myadapter2.notifyDataSetChanged()
                Log.v("내용체크체크체크체크체크체크체크",myadapter2.data.toString())
                // 에러날 것 같은 부분 ※
                rv_datalist.add(myadapter2.data)
                Log.v("rv_datalist 데이터 갯수확인하기",rv_datalist.toString())
            }
        }
        
        //----------------------------------------------------------
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