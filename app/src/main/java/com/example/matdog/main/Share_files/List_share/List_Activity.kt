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

    //var state : String = intent.getStringExtra("state")

    private var state0 : String? = null
    private var state1 : String? = null
    private var state2 : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


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
