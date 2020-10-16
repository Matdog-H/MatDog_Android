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

class Call_popupActivity : AppCompatActivity() {
    // 보호소 공고 상세화면 연락처 팝업
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popup)

        // 상세화면에서 token과 공고 id 받아옴
        var token = intent.getStringExtra("token_shelter")
        var registerIdx =intent.getIntExtra("registerIdx_shelter",1)


        // -----------server--------------
        val callpopupDetail = UserServiceImpl.matchingDetailService.matchingDetailResponse(
            token = token,
            registerStatus = 1,
            registerIdx = registerIdx
        )

        callpopupDetail.safeEnqueue {
            if(it.isSuccessful){
                val detailregister = it.body()!!.detailregister
                val detailvalue = it.body()!!.contactopen

                if(detailvalue.telcheck==1) popup_tel?.setText(detailregister.careTel) //전화번호
                else popup_tel?.setText("비공개")

                if(detailvalue.emailcheck==1)  popup_email?.setText(detailregister.email) //이메일
                else popup_email?.setText("비공개")

                if(detailvalue.dmcheck==1) popup_dm?.setText(detailregister.dm) //DM
                else popup_dm?.setText("비공개")

                Log.v("보호소 공고 연락처팝업","성공적")
            }
        }

        closebutton.setOnClickListener(View.OnClickListener {
            //나가기
            finish()
        })
    }
}
