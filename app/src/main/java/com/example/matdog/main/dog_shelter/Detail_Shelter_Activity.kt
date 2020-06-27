package com.example.matdog.main.dog_shelter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R
import com.example.matdog.main.Share_files.Detail_share.Viewpager_detail_adapter
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*


class Detail_Shelter_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewpager = findViewById(R.id.pager) as ViewPager

        val adapter = Viewpager_detail_adapter(this)
        viewpager.adapter = adapter

        init()
    }

    private fun init(){
        btn_detail_call.setOnClickListener {
            val i = Intent(this, Call_popupActivity::class.java)
            startActivity(i)
        }

        btn_detail_back.setOnClickListener {
            finish()
        }

        btn_zzim.setOnClickListener {
            if(CHECK_NUM==0){
                //btn_zzim.setBackgroundResource(R.drawable.ic_heart)
                btn_zzim.setSelected(true)
                CHECK_NUM=1
            }else{
                btn_zzim.setSelected(false)
                CHECK_NUM=0
            }

        }

    }
}
