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

        // -----------server--------------
        val callpopupDetail = UserServiceImpl.matchingDetailService.matchingDetailResponse(
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNYXREb2ciLCJ1c2VySWR4Ijo2fQ.IuYm_J1zncxiL00mMH5n_Sc7eBmT5elC9_8H86lKiH0",
            registerStatus = 1,
            registerIdx = 10
        )

        callpopupDetail.safeEnqueue {
            if(it.isSuccessful){
                val detailregister = it.body()!!.detailregister
                // 연락처 데이터 줌
                popup_tel?.setText(detailregister.careTel) //전화번호
                popup_email?.setText(detailregister.email) //이메일
                popup_dm?.setText(detailregister.dm) //DM
                Log.v("보호소 공고 연락처팝업","성공적")
            }
        }

        closebutton.setOnClickListener(View.OnClickListener {
            //나가기
            finish()
        })
    }
}
