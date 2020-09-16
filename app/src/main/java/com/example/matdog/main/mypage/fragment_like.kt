package com.example.matdog.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.main.Share_files.Recyclerview_share.rv_Adapter
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.dog_protect.Detail_Protect_Activity
import kotlinx.android.synthetic.main.list_item.*

class fragment_like : Fragment(), View.OnClickListener{

    private lateinit var  FLrecyclerview : RecyclerView
    private var mpadapter1: mp_Adapter = mp_Adapter(R.layout.list_item)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var like_listview=inflater.inflate(R.layout.like_list, container, false)
        var thiscontext = container!!.getContext()

        FLrecyclerview = like_listview.findViewById(R.id.ll_recyclerview)

        //this로 현재 context 전달
        //mpadapter1= Adapter(thiscontext)

        //클릭이벤트
        mpadapter1.onItemClick(this)

        //리사이클러뷰의 어댑터 세팅
        FLrecyclerview.adapter=mpadapter1

        //리사이클러뷰 배치
        FLrecyclerview.layoutManager= GridLayoutManager(thiscontext,2)

//        mpadapter1.mp_data= arrayListOf(
//            ListItem(
//                it_image = "1",
//                it_species = "말티즈",
//                it_age = "3",
//                it_status = R.drawable.state_shelter,
//                it_gender = "1",
//                it_date = "2020.06.12"
//            ), ListItem(
//                it_image = "1",
//                it_species = "말티즈",
//                it_age = "3",
//                it_status = R.drawable.state_shelter,
//                it_gender = "1",
//                it_date = "2020.06.12"
//            ), ListItem(
//                it_image = "1",
//                it_species = "말티즈",
//                it_age = "3",
//                it_status = R.drawable.state_shelter,
//                it_gender = "1",
//                it_date = "2020.06.12"
//            ), ListItem(
//                it_image = "1",
//                it_species = "말티즈",
//                it_age = "3",
//                it_status = R.drawable.state_shelter,
//                it_gender = "1",
//                it_date = "2020.06.12"
//            ), ListItem(
//                it_image = "1",
//                it_species = "말티즈",
//                it_age = "3",
//                it_status = R.drawable.state_shelter,
//                it_gender = "1",
//                it_date = "2020.06.12"
//            )
//        )

        mpadapter1.notifyDataSetChanged()

        return like_listview

    }
    override fun onClick(v: View?) {

     FLrecyclerview = view?.findViewById(R.id.ll_recyclerview)!!

        //상태값에 따라 상세화면 바뀌게 수정 필요
        if (v?.parent == FLrecyclerview){
            val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
            intent.putExtra("delete","delete_protect")
            startActivity(intent)
        }

    }
}