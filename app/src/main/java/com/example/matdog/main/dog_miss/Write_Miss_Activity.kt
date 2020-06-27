package com.example.matdog.main.dog_miss

/*
* WriteMissActivity.kt
* 유기견 찾기_ 실종 공고등록
* activity_write_miss.xml
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write_miss.*
import kotlinx.android.synthetic.main.activity_write_miss.btn_okwrite
import kotlinx.android.synthetic.main.activity_write_miss.ic_back
import kotlinx.android.synthetic.main.activity_write_miss.radionotouch
import kotlinx.android.synthetic.main.activity_write_miss.species_modify
import kotlinx.android.synthetic.main.activity_write_miss.species_name

class Write_Miss_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_miss)

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
