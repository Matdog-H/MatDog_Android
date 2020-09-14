package com.example.matdog.main.dog_shelter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*


class Detail_Shelter_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 사진 주기
        detail_img.setImageResource(R.drawable.taepoong2)

        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        //마이페이지에서 넘어왔을 때
        if (delete_state != null && delete_state.equals("delete_shelter")){
            btn_delete.setVisibility(View.VISIBLE)
            btn_delete.isEnabled=true

            //삭제 버튼
            btn_delete.setOnClickListener {
                //해당 공고 삭제
                finish()
            }
        }

        init()

    }

    private fun init(){
        //연락처 팝업버튼
        btn_detail_call.setOnClickListener {
            val i = Intent(this, Call_popupActivity::class.java)
            startActivity(i)
        }

        // 뒤로가기
        btn_detail_back.setOnClickListener {
            finish()
        }

        // 찜버튼
        btn_zzim.setOnClickListener {
            if(CHECK_NUM==0){
                //btn_zzim.setBackgroundResource(R.drawable.ic_heart)
                btn_zzim.setSelected(true)
                CHECK_NUM=1
            }else{
                btn_zzim.setSelected(false)
                CHECK_NUM=0
            }

        }

    }
}
