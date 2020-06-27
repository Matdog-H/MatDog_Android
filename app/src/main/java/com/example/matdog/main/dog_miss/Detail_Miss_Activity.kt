package com.example.matdog.main.dog_miss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R
import com.example.matdog.main.Share_files.Detail_share.Viewpager_detail_adapter
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail_miss.*

class Detail_Miss_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_miss)

        viewpager = findViewById(R.id.pager_missing) as ViewPager

        val adapter = Viewpager_detail_adapter(this)
        viewpager.adapter = adapter

        init()
    }

    private fun init(){
        btn_detail_call_missing.setOnClickListener {
            val i = Intent(this, Call_popupActivity::class.java)
            startActivity(i)
        }

        btn_detail_back_missing.setOnClickListener {
            finish()
        }

        btn_zzim_missing.setOnClickListener {
            if(CHECK_NUM==0){
                //btn_zzim.setBackgroundResource(R.drawable.ic_heart)
                btn_zzim_missing.setSelected(true)
                CHECK_NUM=1
            }else{
                btn_zzim_missing.setSelected(false)
                CHECK_NUM=0
            }

        }

    }
}
