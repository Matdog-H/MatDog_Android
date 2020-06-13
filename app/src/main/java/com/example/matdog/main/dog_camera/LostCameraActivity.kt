package com.example.matdog.main.dog_camera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.matdog.R

class LostCameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_camera)

        val ic_back: ImageButton = findViewById(R.id.ic_back)
        ic_back.setOnClickListener{
            finish()
        }
    }
}
