package com.example.matdog.main.dog_miss

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail_miss.*

class Detail_Miss_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_miss)


        init()


        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        //마이페이지에서 넘어왔을 때
        if (delete_state != null && delete_state.equals("delete_miss")){
            btn_delete_miss.setVisibility(View.VISIBLE)
            btn_delete_miss.isEnabled=true

            //삭제 버튼
            btn_delete_miss.setOnClickListener {
                //해당 공고 삭제
                finish()
            }
        }





    }

    private fun init(){
        btn_detail_call_missing.setOnClickListener {
            val i = Intent(this, Call_popupActivity::class.java)
            startActivity(i)
        }

        btn_detail_back_missing.setOnClickListener {
            finish()
        }

        btn_zzim_missing.setOnClickListener {
            if(CHECK_NUM==0){
                //btn_zzim.setBackgroundResource(R.drawable.ic_heart)
                btn_zzim_missing.setSelected(true)
                CHECK_NUM=1
            }else{
                btn_zzim_missing.setSelected(false)
                CHECK_NUM=0
            }

        }

    }
}
