package com.example.matdog.main.dog_lost

/*
* WriteProtectActivity.kt
* 유기견 찾기_ 임시보호 공고등록
* activity_write_protect.xml
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_write_protect.*

class WriteProtectActivity : AppCompatActivity() {

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

    }
}
