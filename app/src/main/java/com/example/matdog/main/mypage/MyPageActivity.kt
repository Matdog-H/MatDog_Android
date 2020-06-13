package com.example.matdog.main.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.matdog.R
import com.example.matdog.main.dog_list.ListAdapter
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_my_page.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MyPageActivity : AppCompatActivity() {

    var count=1
    var cnt_phone=1
    var cnt_email=1
    var cnt_memo=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page)



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
                user_memo.isEnabled=false

                btn_privacy_phone.isEnabled=false
                btn_privacy_email.isEnabled=false
                btn_privacy_memo.isEnabled=false

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
                user_memo.isEnabled=true

                btn_privacy_phone.isEnabled=true
                btn_privacy_email.isEnabled=true
                btn_privacy_memo.isEnabled=true

                btn_privacy_phone.setOnClickListener {
                    if(cnt_phone%2==0)
                        btn_privacy_phone.setText("비공개")
                    else
                        btn_privacy_phone.setText("공개")
                    cnt_phone++
                }

                btn_privacy_email.setOnClickListener {
                    if(cnt_email%2==0)
                        btn_privacy_email.setText("비공개")
                    else
                        btn_privacy_email.setText("공개")
                    cnt_email++
                }

                btn_privacy_memo.setOnClickListener {
                    if(cnt_memo%2==0)
                        btn_privacy_memo.setText("비공개")
                    else
                        btn_privacy_memo.setText("공개")
                    cnt_memo++
                }

                count++
            }


        }


    }



    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
}
