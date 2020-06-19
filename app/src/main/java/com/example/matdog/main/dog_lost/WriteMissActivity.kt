package com.example.matdog.main.dog_lost

/*
* WriteMissActivity.kt
* 유기견 찾기_ 실종 공고등록
* activity_write_miss.xml
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_write_miss.*

class WriteMissActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_miss)

        ic_back.setOnClickListener {
            finish()
        }
    }

}
