package com.example.matdog.main.pop_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_detail_list_popup.*
import kotlinx.android.synthetic.main.activity_detail_popup.*

class Call_popupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popup)

        closebutton.setOnClickListener(View.OnClickListener {
            // editText란이 하나라도 정보가 기입되지 않은 경우
            if(list_popup_tel.equals("") || list_popup_dm.equals("") || list_popup_email.equals(""))
                Toast.makeText(this, "모든 연락정보를 입력해주세요.",Toast.LENGTH_LONG)
            else //나가기
                finish()
        })
    }
}
