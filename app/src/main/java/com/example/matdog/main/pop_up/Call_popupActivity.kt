package com.example.matdog.main.pop_up

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.matdog.R
import kotlinx.android.synthetic.main.activity_detail_popup.*

class Call_popupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_popup)

        closebutton.setOnClickListener(View.OnClickListener {
            //나가기
            finish()
        })
    }
}
