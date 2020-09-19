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

    private var state0 : String? = null // 보호소 리스트 - 분양공고등록
    private var state1 : String? = null // 임시보호 리스트 - 실종공고등록
    private var state2 : String? = null // 실종 리스트 - 임시보호공고등록

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        state0 = intent.getStringExtra("state0")
        state1 = intent.getStringExtra("state1")
        state2 = intent.getStringExtra("state2")

        if(state1.isNullOrBlank() && state2.isNullOrBlank())
            list_change(0)
        else if(state0.isNullOrBlank() && state2.isNullOrBlank())
            list_change(1)
        else
            list_change(2)

        init()

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

    private fun list_change(state_result : Int) {

        val fragmentAdapter =
            ViewPager_Shelter_Adapter(
                supportFragmentManager, state_result // 상태값에 따라 서버연결 리스트가 변하도록
            )
        list_viewPager.adapter = fragmentAdapter
        list_tablayout.setupWithViewPager(list_viewPager)

        // 글등록 버튼
        btn_write.setOnClickListener {
             if (state_result==0) {
                val intent1 = Intent(this, Write_Shelter_Activity::class.java)
                startActivity(intent1)
            } else if (state_result==1) {
                 val intent2 = Intent(this, Write_Miss_Activity::class.java)
                startActivity(intent2)
            } else {
                 val intent3 = Intent(this, Write_Protect_Activity::class.java)
                startActivity(intent3)
            }

        }

    }

}
