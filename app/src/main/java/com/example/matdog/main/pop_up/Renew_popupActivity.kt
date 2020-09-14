package com.example.matdog.main.pop_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_detail_list_popup.*
import kotlinx.android.synthetic.main.activity_sign_in.*

class Renew_popupActivity : AppCompatActivity() {
    // 공고등록 연락처 수정 팝업
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_list_popup)

        popup_editbutton.setOnClickListener(View.OnClickListener {
            //수정
            // editText란이 하나라도 정보가 기입되지 않은 경우 토스트 띄우기
            var tel = list_popup_tel.text.toString()
            var email = list_popup_email.text.toString()
            var dm = list_popup_dm.text.toString()

            if(tel.equals(""))
                Toast.makeText(this, "모든 연락정보를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(email.equals(""))
                Toast.makeText(this, "모든 연락정보를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (dm.equals(""))
                Toast.makeText(this, "모든 연락정보를 입력해주세요.", Toast.LENGTH_LONG).show()
            else finish()
        })
    }


}
