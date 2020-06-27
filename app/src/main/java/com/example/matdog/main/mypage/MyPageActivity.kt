package com.example.matdog.main.mypage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.matdog.R
import com.example.matdog.main.dog_list.ListAdapter
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MyPageActivity : AppCompatActivity() {

    var count=1
    var cnt_phone=1
    var cnt_address=1
    var cnt_email=1
    var cnt_dm=1
    var strColor1 = "#FF565A" // 비공개
    var strColor2 = "#5ea096" //공개

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)

        val ic_back: ImageButton = findViewById(R.id.ic_back)
        ic_back.setOnClickListener{
            finish()
        }

        val mypage_fragmentAdapter = MypageAdapter(supportFragmentManager)
        mypage_viewPager.adapter = mypage_fragmentAdapter

        mypage_tablayout.setupWithViewPager(mypage_viewPager)


        btn_edit.setOnClickListener {

            if(count%2==0)
            {
                //정보 저장
                btn_edit.setImageResource(R.drawable.ic_edit)

                user_name.isEnabled=false
                user_birth.isEnabled=false
                user_address.isEnabled=false
                user_phone.isEnabled=false
                user_email.isEnabled=false
                user_dm.isEnabled=false

                btn_privacy_phone.isEnabled=false
                btn_privacy_address.isEnabled=false
                btn_privacy_email.isEnabled=false
                btn_privacy_dm.isEnabled=false

                count++
            }
            else{

                //정보 수정
                btn_edit.setImageResource(R.drawable.ic_ok)
                user_name.isEnabled=true
                user_birth.isEnabled=true
                user_address.isEnabled=true
                user_phone.isEnabled=true
                user_email.isEnabled=true
                user_dm.isEnabled=true

                btn_privacy_phone.isEnabled=true
                btn_privacy_email.isEnabled=true
                btn_privacy_email.isEnabled=true
                btn_privacy_dm.isEnabled=true

                btn_privacy_phone.setOnClickListener {
                    if(cnt_phone%2==0){
                        btn_privacy_phone.setText("비공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor1))
                        }
                    else {
                        btn_privacy_phone.setText("공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor2))
                    }
                    cnt_phone++
                }

                btn_privacy_address.setOnClickListener {
                    if(cnt_email%2==0){
                        btn_privacy_phone.setText("비공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor1))
                    }
                    else {
                        btn_privacy_phone.setText("공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor2))
                    }
                    cnt_address++
                }
                btn_privacy_email.setOnClickListener {
                    if(cnt_email%2==0){
                        btn_privacy_phone.setText("비공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor1))
                    }
                    else {
                        btn_privacy_phone.setText("공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor2))
                    }
                    cnt_email++
                }

                btn_privacy_dm.setOnClickListener {
                    if(cnt_dm%2==0){
                        btn_privacy_phone.setText("비공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor1))
                    }
                    else {
                        btn_privacy_phone.setText("공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor2))
                    }
                    cnt_dm++
                }

                count++
            }


        }


    }


}
