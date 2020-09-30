package com.example.matdog.main.pop_up

import android.content.Intent
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
            //수정버튼
            var tel : String? = list_popup_tel.getText().toString()
            var email : String? = list_popup_email.getText().toString()
            var dm : String? = list_popup_dm.getText().toString()

            if(tel.equals("") && email.equals("") && dm.equals(""))
                // editText란이 하나라도 정보가 기입되지 않은 경우 토스트 띄우기
                Toast.makeText(this, "연락정보를 하나이상 입력해주세요.", Toast.LENGTH_LONG).show()
            else { // 데이터전달
                //val intent = Intent()
                intent.putExtra("tel", tel)
                intent.putExtra("email", email)
                intent.putExtra("dm", dm)
                setResult(12345,intent)
                finish()
            }
        })
    }

}
