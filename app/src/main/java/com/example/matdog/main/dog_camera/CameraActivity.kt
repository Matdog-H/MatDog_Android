package com.example.matdog.main.dog_camera


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import com.example.matdog.R

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val ic_back: ImageButton = findViewById(R.id.ic_back)
        ic_back.setOnClickListener{
            finish()
        }

    }
}
