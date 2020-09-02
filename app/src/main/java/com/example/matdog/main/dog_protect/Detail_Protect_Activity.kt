package com.example.matdog.main.dog_protect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.matdog.R
import com.example.matdog.main.Share_files.Detail_share.Viewpager_detail_adapter
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail_miss.*
import kotlinx.android.synthetic.main.activity_detail_protect.*

class Detail_Protect_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_protect)

        viewpager = findViewById(R.id.pager_protect) as ViewPager

        val adapter = Viewpager_detail_adapter(this)
        viewpager.adapter = adapter


        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        //마이페이지에서 넘어왔을 때
        if (delete_state != null && delete_state.equals("delete_protect")){
            btn_delete_protect.setVisibility(View.VISIBLE)
            btn_delete_protect.isEnabled=true

            //삭제 버튼
            btn_delete_protect.setOnClickListener {
                //해당 공고 삭제
                finish()
            }
        }

        init()


    }

    private fun init(){
        btn_detail_call_protect.setOnClickListener {
            val i = Intent(this, Call_popupActivity::class.java)
            startActivity(i)
        }

        btn_detail_back_protect.setOnClickListener {
            finish()
        }

        btn_zzim_protect.setOnClickListener {
            if(CHECK_NUM==0){
                //btn_zzim.setBackgroundResource(R.drawable.ic_heart)
                btn_zzim_protect.setSelected(true)
                CHECK_NUM=1
            }else{
                btn_zzim_protect.setSelected(false)
                CHECK_NUM=0
            }

        }

    }
}
