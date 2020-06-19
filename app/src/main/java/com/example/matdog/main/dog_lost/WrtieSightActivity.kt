package com.example.matdog.main.dog_lost

/*
* WriteSightActivity.kt
* 유기견 찾기_ 목격 공고등록
* activity_write_sight.xml
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_wrtie_sight.*

class WrtieSightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrtie_sight)

        ic_back.setOnClickListener {
            finish()
        }
    }
}
