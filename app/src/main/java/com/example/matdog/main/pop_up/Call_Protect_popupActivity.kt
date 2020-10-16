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

class Call_Protect_popupActivity : AppCompatActivity() {
    // 임시보호 공고 상세화면 연락처 팝업
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popup)

        // 상세화면에서 token과 공고 id 받아옴
        var token = intent.getStringExtra("token_protect")
        var registerIdx =intent.getIntExtra("registerIdx_protect",1)

        // -----------server--------------
        val callpopupProtect = UserServiceImpl.matchingDetailService.matchingDetailResponse_protect(
            token = token,
            registerStatus = 3, //상태 "실종" 고정
            registerIdx = registerIdx  // 공고 id
        )

        callpopupProtect.safeEnqueue {
            if(it.isSuccessful){
                val protectregister = it.body()!!.protectregister
                val protectvalue = it.body()!!.contactopen

                if(protectvalue.telcheck==1) popup_tel?.setText(protectregister.careTel) //전화번호
                else popup_tel?.setText("비공개")

                if(protectvalue.emailcheck==1)  popup_email?.setText(protectregister.email) //이메일
                else popup_email?.setText("비공개")

                if(protectvalue.dmcheck==1) popup_dm?.setText(protectregister.dm) //DM
                else popup_dm?.setText("비공개")

                Log.v("임시보호 공고 연락처팝업","성공적")
            }
        }

        closebutton.setOnClickListener(View.OnClickListener {
            //나가기
            finish()
        })
    }
}
