package com.example.matdog.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.matdog.R
import com.example.matdog.main.dog_camera.CameraActivity
import com.example.matdog.main.dog_camera.LostCameraActivity
import com.example.matdog.main.dog_list.ListActivity
import com.example.matdog.main.mypage.MyPageActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_camera.setOnClickListener{
            val intent = Intent(this@MainActivity, CameraActivity::class.java)
            startActivity(intent)
        }
        btn_lost.setOnClickListener{
            val intent = Intent(this@MainActivity, LostCameraActivity::class.java)
            startActivity(intent)
        }
        btn_list.setOnClickListener{
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            startActivity(intent)
        }
        btn_mypage.setOnClickListener{
            val intent = Intent(this@MainActivity, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

}
