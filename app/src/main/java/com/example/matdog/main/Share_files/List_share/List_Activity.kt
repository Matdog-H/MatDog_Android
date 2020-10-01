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
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
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

    //var array_status = arrayOfNulls<Int>(9)
    private var search_data : String? = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var a1 = intent.getStringExtra("state0") // 보호소 리스트 - 분양공고등록
        var a2 = intent.getStringExtra("state1") // 임시보호 리스트 - 실종공고등록
        var a3 = intent.getStringExtra("state2") // 실종 리스트 - 임시보호공고등록
        var a4 = intent.getStringExtra("state3") // 보호소 리스트 - 품종결과
        var a5 = intent.getStringExtra("state4") // 임시보호 리스트 - 품종결과
        var a6 = intent.getStringExtra("state5") // 실종 리스트 - 품종결과

        var array_status= arrayOf(
            a1,a2,a3,a4,a5,a6
        )

        for (i in 0 until array_status.size){
            if(array_status[i].isNullOrBlank()){}
            else{
                list_change(i)
                break
            }
        }

        init()
    }

    // 버튼 클릭 관련 함수
    private fun init(){

        ic_back.setOnClickListener{
            finish()
        }
    }

    private fun list_change(state_result : Int) {

        if(state_result==0 || state_result==1 || state_result==2) {
            edt_search.isVisible=true
            btn_search.isVisible=true

            btn_search.setOnClickListener {
                search_data = edt_search.getText().toString()

                if(search_data==null){
                    var fragmentAdapter =
                        ViewPager_Shelter_Adapter(
                            supportFragmentManager, state_result, " " // 상태값에 따라 서버연결 리스트가 변하도록
                        )
                    list_viewPager.adapter = fragmentAdapter
                    list_tablayout.setupWithViewPager(list_viewPager)
                }else {
                    var fragmentAdapter =
                        ViewPager_Shelter_Adapter(
                            supportFragmentManager,
                            state_result,
                            search_data // 상태값에 따라 서버연결 리스트가 변하도록
                        )
                    list_viewPager.adapter = fragmentAdapter
                    list_tablayout.setupWithViewPager(list_viewPager)
                }
                Log.v("searchdata값", search_data)
            }
        } else{
            edt_search.isVisible=false
            btn_search.isVisible=false
        }

        var fragmentAdapter =
            ViewPager_Shelter_Adapter(
                supportFragmentManager, state_result, search_data // 상태값에 따라 서버연결 리스트가 변하도록
            )

        list_viewPager.adapter = fragmentAdapter
        list_tablayout.setupWithViewPager(list_viewPager)

        // 글등록 버튼
        btn_write.setOnClickListener {
             if (state_result==0 || state_result==3) {
                val intent1 = Intent(this, Write_Shelter_Activity::class.java)
                startActivityForResult(intent1,2000)
            } else if (state_result==1 || state_result==4) {
                 val intent2 = Intent(this, Write_Miss_Activity::class.java)
                startActivityForResult(intent2,3000)
            } else {
                 val intent3 = Intent(this, Write_Protect_Activity::class.java)
                startActivityForResult(intent3,4000)
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == 2000 || requestCode==3000 || requestCode==4000) {
            Log.v("requestCode로 들어옴","requestCode로 들어왔음")
            var a1 = intent.getStringExtra("state0") // 보호소 리스트 - 분양공고등록
            var a2 = intent.getStringExtra("state1") // 임시보호 리스트 - 실종공고등록
            var a3 = intent.getStringExtra("state2") // 실종 리스트 - 임시보호공고등록
            var a4 = intent.getStringExtra("state3") // 보호소 리스트 - 품종결과
            var a5 = intent.getStringExtra("state4") // 임시보호 리스트 - 품종결과
            var a6 = intent.getStringExtra("state5") // 실종 리스트 - 품종결과

            var array_status = arrayOf(
                a1, a2, a3, a4, a5, a6
            )

            for (i in 0 until array_status.size) {
                if (array_status[i].isNullOrBlank()) {
                } else {
                    list_change(i)
                    break
                }
            }

            init()
        }
    }

}
