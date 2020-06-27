package com.example.matdog.main.dog_camera

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.matdog.R
import com.example.matdog.main.dog_list.ListActivity
import kotlinx.android.synthetic.main.activity_lost_camera.*

class LostCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_camera)

        ic_back.setOnClickListener{
            finish()
        }

        finddog.setOnClickListener{
            val i = Intent(this, ListActivity::class.java)
            startActivity(i)
        }
    }
}
