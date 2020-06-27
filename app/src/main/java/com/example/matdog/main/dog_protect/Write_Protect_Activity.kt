package com.example.matdog.main.dog_protect

/*
* WriteProtectActivity.kt
* 유기견 찾기_ 임시보호 공고등록
* activity_write_protect.xml
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write_protect.*
import kotlinx.android.synthetic.main.activity_write_protect.btn_okwrite
import kotlinx.android.synthetic.main.activity_write_protect.ic_back
import kotlinx.android.synthetic.main.activity_write_protect.radionotouch
import kotlinx.android.synthetic.main.activity_write_protect.species_modify
import kotlinx.android.synthetic.main.activity_write_protect.species_name

class Write_Protect_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_protect)

        ic_back.setOnClickListener {
            finish()
        }

        btn_okwrite.setOnClickListener {
            finish()
        }

        species_modify.setOnClickListener {
            species_name.isEnabled = true // 종 수정하기
        }


        radionotouch.setOnClickListener {
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivity(i)

        }
    }
}
