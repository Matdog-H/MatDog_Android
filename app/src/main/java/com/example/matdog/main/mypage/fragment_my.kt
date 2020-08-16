package com.example.matdog.main.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matdog.R
import com.example.matdog.main.Share_files.Recyclerview_share.Adapter
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.dog_miss.Detail_Miss_Activity
import com.example.matdog.main.dog_shelter.Detail_Shelter_Activity

class fragment_my : Fragment(), View.OnClickListener{

    private lateinit var  recyclerview : RecyclerView
    private lateinit var myadapter: Adapter


   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mylistview= inflater.inflate(R.layout.my_list, container, false)
        var thiscontext = container!!.getContext()


       recyclerview = mylistview.findViewById(R.id.ml_recyclerview)

       //this로 현재 context 전달
       myadapter= Adapter(thiscontext)

       myadapter.onItemClick(this)


//       myadapter= Adapter(thiscontext,ListItem){item ->
//           Toast.makeText(thiscontext, "개의 품종은 ${item.it_species} 이며, 나이는 ${item.it_age}세이다.", Toast.LENGTH_SHORT).show()
//       }

       //리사이클러뷰의 어댑터 세팅
       recyclerview.adapter=myadapter

       //리사이클러뷰 배치
       recyclerview.layoutManager=GridLayoutManager(thiscontext,2)


       myadapter.data= listOf(
           ListItem(
               it_image = R.drawable.taepoong,
               it_love = R.drawable.ic_love,
               it_species = "말티즈",
               it_age = "3살 추정",
               it_state = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "2020.06.12"
               //it_place = "경기도 고양시"
           ),
           ListItem(
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
           ),
           ListItem(
               it_image = R.drawable.taepoong3,
               it_love = R.drawable.ic_love,
               it_species = "말티즈",
               it_age = "3살 추정",
               it_state = R.drawable.state_shelter,
               it_gender = R.drawable.gender_man,
               it_date = "2020.06.12"
               //it_place = "경기도 고양시"
           )
       )

       //클릭 리스너 등록
//       myadapter.setItemClickListener(object : Adapter.ItemClickListener{
//           override fun onClick(view:View,position:Int){
//               Log.d("Lss","${position}번 리스트 선택")
//           }
//       })

       myadapter.notifyDataSetChanged()



       return mylistview

    }


    override fun onClick(v: View?) {
        recyclerview = view?.findViewById(R.id.ml_recyclerview)!!

        if (v?.parent == recyclerview){
            val intent: Intent = Intent(getActivity(), Detail_Miss_Activity::class.java)
            intent.putExtra("delete","delete_miss")
            startActivity(intent)
        }

    }

}