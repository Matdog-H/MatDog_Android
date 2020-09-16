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


class fragment_my : Fragment(), View.OnClickListener{

    private lateinit var  FMrecyclerview : RecyclerView
    private var mpadapter2: mp_Adapter = mp_Adapter(R.layout.list_item)

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mylistview= inflater.inflate(R.layout.my_list, container, false)
        var thiscontext = container!!.getContext()


       FMrecyclerview = mylistview.findViewById(R.id.ml_recyclerview)

       //this로 현재 context 전달
       //mpadapter2= Adapter(thiscontext)

       mpadapter2.onItemClick(this)

       //리사이클러뷰의 어댑터 세팅
       FMrecyclerview.adapter=mpadapter2

       //리사이클러뷰 배치
       FMrecyclerview.layoutManager= GridLayoutManager(thiscontext,2)


       mpadapter2.mp_data= arrayListOf(
           ListItem(
               it_image = R.drawable.taepoong3,
               it_species = "말티즈",
               it_age = 3,
               it_status = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "2020.06.12"
           ),
           ListItem(
               it_image = R.drawable.taepoong2,
               it_species = "말티즈",
               it_age = 2,
               it_status = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "20200202"
           ),  ListItem(
               it_image = R.drawable.taepoong3,
               it_species = "말티즈",
               it_age = 3,
               it_status = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "2020.06.12"
           ),
           ListItem(
               it_image = R.drawable.taepoong2,
               it_species = "말티즈",
               it_age = 2,
               it_status = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "20200202"
           ),
           ListItem(
                       it_image = R.drawable.taepoong3,
               it_species = "말티즈",
               it_age = 3,
               it_status = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "2020.06.12"
           ),
           ListItem(
               it_image = R.drawable.taepoong2,
               it_species = "말티즈",
               it_age = 2,
               it_status = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "20200202"
       )
       )


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


        //상태값에 따라 상세화면 바뀌게 수정 필요
        if (v?.parent == FMrecyclerview){
            val intent: Intent = Intent(getActivity(), Detail_Protect_Activity::class.java)
            intent.putExtra("delete","delete_protect")
            startActivity(intent)
        }

    }

}