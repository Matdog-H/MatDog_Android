package com.example.matdog.main.pop_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_list_popup.*
import kotlinx.android.synthetic.main.activity_detail_popup.*

class Call_Miss_popupActivity : AppCompatActivity() {
    // 실종 공고 상세화면 연락처 팝업
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popup)

        // 상세화면에서 token과 공고 id 받아옴
        var token = intent.getStringExtra("token_miss")
        var registerIdx =intent.getIntExtra("registerIdx_miss",1)

        // -----------server--------------
        val callpopupMiss = UserServiceImpl.matchingDetailService.matchingDetailResponse_miss(
            token = token,
            registerStatus = 2, //상태 "실종" 고정
            registerIdx = registerIdx // 공고 id
        )

        callpopupMiss.safeEnqueue {
            if(it.isSuccessful){
                val missregister = it.body()!!.missregister
                val missvalue = it.body()!!.contactopen

                if(missvalue.telcheck==1) popup_tel?.setText(missregister.careTel) //전화번호
                else popup_tel?.setText("비공개")

                if(missvalue.emailcheck==1)  popup_email?.setText(missregister.email) //이메일
                else popup_email?.setText("비공개")

                if(missvalue.dmcheck==1) popup_dm?.setText(missregister.dm) //DM
                else popup_dm?.setText("비공개")

                Log.v("실종 공고 연락처팝업","성공적")

            }
        }

        closebutton.setOnClickListener(View.OnClickListener {
            //나가기
            finish()
        })
    }
}
