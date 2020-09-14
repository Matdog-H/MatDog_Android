package com.example.matdog.main.Share_files.List_share

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.matdog.R
import com.example.matdog.api.ListAllData
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.Share_files.Recyclerview_share.ListItem
import com.example.matdog.main.dog_miss.Write_Miss_Activity
import com.example.matdog.main.dog_protect.Write_Protect_Activity
import com.example.matdog.main.dog_shelter.Write_Shelter_Activity
import kotlinx.android.synthetic.main.activity_list.*


class List_Activity : AppCompatActivity() {

    private val fragment_age = Fragment_Shelter_Age()
    private val fragment_new = Fragment_Shelter_New()

    var rv_datalist = ArrayList<ArrayList<ListItem>>()

    //var state : String = intent.getStringExtra("state")

    private var state0 : String? = null
    private var state1 : String? = null
    private var state2 : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val callNewList = UserServiceImpl.ListService.listResponse_new()
        val callAgeList = UserServiceImpl.ListService.listResponse_age()

//        callNewList.safeEnqueue {
//            if(it.isSuccessful){
//                //추후에 내부에서 status값에 따라 분리하는 코드 작성 요함.
//                Log.v("666666666666666","666666666666666666666")
//                val myData = it.body()!!.listdata
//                Log.v("myData의 사이즈 체크", myData.size.toString())
//                var List_new = arrayListOf<ListItem>()
//                for(i in 0 until myData.size) {
//                    Log.v("777777777777","777777777777777777")
//                    List_new.add(
//                        ListItem(
//                            it_image = R.drawable.taepoong,
//                            it_species = myData[i].kindCd,
//                            it_status = myData[i].registerStatus,
//                            it_gender = myData[i].sexCd,
//                            it_age = myData[i].age,
//                            it_date = myData[i].happenDt
//                        )
//                    )
//                    Log.v("item값 체크하기",myData[i].kindCd)
//                }
//                fragment_new.myadapter1.data = List_new
//                fragment_new.myadapter1.notifyDataSetChanged()
//                // 에러날 것 같은 부분 ※
//                Log.v("888888888888888","888888888888888")
//                rv_datalist.add(fragment_new.myadapter1.data) // 이부분에서 어댑터 통해 리사이클러뷰로 못가는 것 같음
//                Log.v("데이터리스트 확인하기",rv_datalist.toString())
//            }
//        }

//        callAgeList.safeEnqueue {
//            if(it.isSuccessful){
//                //추후에 내부에서 status값에 따라 분리하는 코드 작성 요함.
//                val myData = it.body()!!.listdata
//                Log.v("myData의 사이즈 체크", myData.size.toString())
//                var List_age = arrayListOf<ListItem>()
//                for(i in 0 until myData.size) {
//                    List_age.add(
//                        ListItem(
//                            it_image = R.drawable.taepoong,
//                            it_species = myData[i].kindCd,
//                            it_status = myData[i].registerStatus,
//                            it_gender = myData[i].sexCd,
//                            it_age = myData[i].age,
//                            it_date = myData[i].happenDt
//                        )
//                    )
//                    Log.v("item값 체크하기",myData[i].happenDt)
//                }
//                fragment_age.myadapter2.data = List_age
//                fragment_age.myadapter2.notifyDataSetChanged()
//                Log.v("내용체크체크체크체크체크체크체크",fragment_age.myadapter2.data.toString())
//                // 에러날 것 같은 부분 ※
//                rv_datalist.add(fragment_age.myadapter2.data)
//                Log.v("rv_datalist 데이터 갯수확인하기",rv_datalist.toString())
//            }
//        }

        val fragmentAdapter =
            ViewPager_Shelter_Adapter(
                supportFragmentManager
            )
        list_viewPager.adapter = fragmentAdapter
        list_tablayout.setupWithViewPager(list_viewPager)

        init()
        list_change()

    }

    // 버튼 클릭 관련 함수
    private fun init(){
        var count=1 //버튼 클릭 횟수

        val ic_back: ImageButton = findViewById(R.id.ic_back)
        ic_back.setOnClickListener{
            finish()
        }

        btn_search.setOnClickListener {
            val search_edt = EditText(applicationContext)
            val p = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            if(count>1 && count%2==0){ //버튼을 두번 클릭 했을 시
                //검색 기능 추가
                searchview.removeAllViews();
                count++

            } else{ //버튼을 눌렀을 때
                search_edt.layoutParams = p
                search_edt.setHint("검색어를 입력하세요.")
                search_edt.setPadding(50, 0, 50, 0)
                search_edt.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15F)
//                search_edt.background(txt_box)
                //search_edt.setPadding(5)
                search_edt.setBackgroundDrawable(getResources().getDrawable(R.drawable.txt_box))

                searchview.addView(search_edt)
                count++
            }
        }
    }

    private fun list_change() {

        state0 = intent.getStringExtra("state0")
        state1 = intent.getStringExtra("state1")
        state2 = intent.getStringExtra("state2")


        btn_write.setOnClickListener {
             if (state1.isNullOrBlank() && state2.isNullOrBlank()) {
                val intent1 = Intent(this, Write_Shelter_Activity::class.java)
                startActivity(intent1)
            } else if (state0.isNullOrBlank() && state2.isNullOrBlank()) {
                 val intent2 = Intent(this, Write_Miss_Activity::class.java)
                startActivity(intent2)
            } else {
                 val intent3 = Intent(this, Write_Protect_Activity::class.java)
                startActivity(intent3)
            }

        }

    }
}
