package com.example.matdog.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import com.example.matdog.main.Share_files.List_share.List_Activity
import kotlinx.android.synthetic.main.activity_lost_camera.*

class LostCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_camera)

        ic_back.setOnClickListener{
            finish()
        }

        finddog.setOnClickListener{
            val i1 = Intent(this, List_Activity::class.java)
            i1.putExtra("state1","1")
            startActivity(i1)
        }

        findjuin.setOnClickListener{
            val i2 = Intent(this, List_Activity::class.java)
            i2.putExtra("state2","2")
            startActivity(i2)
        }
    }
}
