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
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import com.example.matdog.main.dog_protect.Detail_Protect_Activity
import com.example.matdog.main.dog_shelter.Detail_Shelter_Activity
import kotlinx.android.synthetic.main.activity_fragment_new.*
import kotlinx.android.synthetic.main.list_item.*


class Fragment_Shelter_New : Fragment(), View.OnClickListener{

    private lateinit var  FNrecyclerview : RecyclerView
    var myadapter1: rv_Adapter = rv_Adapter(R.layout.list_item)

    var rv_datalist = ArrayList<ArrayList<ListItem>>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view= inflater.inflate(R.layout.activity_fragment_new, container, false)

        //-------server--------------
        val callNewList = UserServiceImpl.ListService.listResponse_new()

        callNewList.safeEnqueue {
            if(it.isSuccessful){
                //추후에 내부에서 status값에 따라 분리하는 코드 작성 요함.
                Log.v("666666666666666","666666666666666666666")
                val myData = it.body()!!.listdata
                Log.v("myData의 사이즈 체크", myData.size.toString())
                var List_new = arrayListOf<ListItem>()
                for(i in 0 until myData.size) {
                    Log.v("777777777777","777777777777777777")
                    List_new.add(
                        ListItem(
                            it_species = myData[i].kindCd,
                            it_status = myData[i].registerStatus,
                            it_gender = myData[i].sexCd,
                            it_age = myData[i].age,
                            it_date = myData[i].happenDt,
                            it_image = myData[i].popfile
                        )
                    )
                    Log.v("item값 체크하기",myData[i].toString())
                }
                myadapter1.data = List_new
                myadapter1.notifyDataSetChanged()
                // 에러날 것 같은 부분 ※
                Log.v("888888888888888","888888888888888")
                rv_datalist.add(myadapter1.data) // 이부분에서 어댑터 통해 리사이클러뷰로 못가는 것 같음
                Log.v("데이터리스트 확인하기",rv_datalist.toString())
            }
        }

        //-------------------------------------------------
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
        recyclerview.adapter=myadapter

        //리사이클러뷰 배치
        recyclerview.layoutManager= GridLayoutManager(thiscontext,2)

        myadapter.data= listOf(
            ListItem(
                it_image = R.drawable.taepoong,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "3살 추정",
                it_state = R.drawable.state_protect,
                it_gender = R.drawable.gender_man,
                it_date = "2020.05.31"
                //it_place = "경기도 고양시"
            ),
            ListItem(
                it_image = R.drawable.taepoong2,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "4살 추정",
                it_state = R.drawable.state_sighting,
                it_gender = R.drawable.gender_woman,
                it_date = "2020.06.11"
                //it_place = "경기도 고양시"
            ), ListItem(
                it_image = R.drawable.taepoong3,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "4살 추정",
                it_state = R.drawable.state_sighting,
                it_gender = R.drawable.gender_man,
                it_date = "2020.06.12"
                //it_place = "경기도 고양시"
            ), ListItem(
                it_image = R.drawable.taepoong,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "5살 추정",
                it_state = R.drawable.state_sighting,
                it_gender = R.drawable.gender_woman,
                it_date = "2020.06.12"
                //it_place = "경기도 고양시"
            ), ListItem(
                it_image = R.drawable.taepoong2,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "3살 추정",
                it_state = R.drawable.state_shelter,
                it_gender = R.drawable.gender_woman,
                it_date = "2020.06.12"
                //it_place = "경기도 고양시"
            ), ListItem(
                it_image = R.drawable.taepoong3,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "3살 추정",
                it_state = R.drawable.state_shelter,
                it_gender = R.drawable.gender_man,
                it_date = "2020.06.12"
                //it_place = "경기도 고양시"
            ), ListItem(
                it_image = R.drawable.taepoong,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "3살 추정",
                it_state = R.drawable.state_shelter,
                it_gender = R.drawable.gender_man,
                it_date = "2020.06.12"
                //it_place = "경기도 고양시"
            ), ListItem(
                it_image = R.drawable.taepoong2,
                it_love = R.drawable.ic_love,
                it_species = "말티즈",
                it_age = "3살 추정",
                it_state = R.drawable.state_shelter,
                it_gender = R.drawable.gender_man,
                it_date = "2020.06.12"
                //it_place = "경기도 고양시"
            )

        )

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