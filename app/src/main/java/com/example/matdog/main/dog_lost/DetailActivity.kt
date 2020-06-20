package com.example.matdog.main.dog_lost

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R
import com.example.matdog.main.dog_lost.detail_popup.Detail_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewpager = findViewById(R.id.pager) as ViewPager

        val adapter = ViewPagerAdapter(this)
        viewpager.adapter = adapter

        init()
    }

    private fun init(){
        btn_detail_call.setOnClickListener {
            val i = Intent(this, Detail_popupActivity::class.java)
            startActivity(i)
        }

        btn_detail_back.setOnClickListener {
            finish()
        }
    }
}
