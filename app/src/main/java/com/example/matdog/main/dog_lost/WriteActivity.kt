package com.example.matdog.main.dog_lost

/*
* WriteActivity.kt
* 분양 공고 등록
* activity_write.xml
 */

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_write.*

class WriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        ic_back.setOnClickListener {
            finish()
        }
    }
}
