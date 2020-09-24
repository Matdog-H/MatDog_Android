package com.example.matdog.main.mypage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.api.EditMyDataRequest
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import kotlinx.android.synthetic.main.activity_my_page.*

class MyPageActivity : AppCompatActivity() {
    private var token1 : String = ""
    private var token2 : String = ""
    var count=1

    var edited_pcheck=0
    var edited_echeck =0
    var edited_dcheck =0

    var cnt_phone=1
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

        // -----------server(정보 가져오기)--------------
        token1 = SharedPreferenceController.getUserToken(this)
        val call_mydata = UserServiceImpl.MyService.Search_mydata(token1)
        call_mydata.safeEnqueue {
            if(it.isSuccessful){
                val my_data =it.body()!!.mydata
                user_name.setText(my_data.name)
                user_birth.setText(my_data.birth)
                user_phone.setText(my_data.tel)
                user_email.setText(my_data.email)
                user_address.setText(my_data.addr)
                user_dm.setText(my_data.dm)

                if(my_data.telcheck==0){
                    btn_privacy_phone.setText("비공개")
                    btn_privacy_phone.setTextColor(Color.parseColor(strColor1))
                }
                else{
                    btn_privacy_phone.setText("공개")
                    btn_privacy_phone.setTextColor(Color.parseColor(strColor2))
                }


                if(my_data.emailcheck==0){
                    btn_privacy_email.setText("비공개")
                    btn_privacy_email.setTextColor(Color.parseColor(strColor1))
                }
                else{
                    btn_privacy_email.setText("공개")
                    btn_privacy_email.setTextColor(Color.parseColor(strColor2))
                }

                if(my_data.dmcheck==0){
                    btn_privacy_dm.setText("비공개")
                    btn_privacy_dm.setTextColor(Color.parseColor(strColor1))
                }
                else{
                    btn_privacy_dm.setText("공개")
                    btn_privacy_dm.setTextColor(Color.parseColor(strColor2))
                }
            }
        }
        //----------------------------------

        //private fun list_change(state_result : Int) {
        //
        //        val fragmentAdapter =
        //            ViewPager_Shelter_Adapter(
        //                supportFragmentManager, state_result // 상태값에 따라 서버연결 리스트가 변하도록
        //            )
        //        list_viewPager.adapter = fragmentAdapter
        //        list_tablayout.setupWithViewPager(list_viewPager)

        val mypage_fragmentAdapter = Viewpager_mypage_adapter(supportFragmentManager)
        mypage_viewPager.adapter = mypage_fragmentAdapter

        mypage_tablayout.setupWithViewPager(mypage_viewPager)


        //정보 수정
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
                btn_privacy_email.isEnabled=false
                btn_privacy_dm.isEnabled=false

                if(btn_privacy_phone.text.equals("비공개")){
                    edited_pcheck=0
                }else{
                    edited_pcheck=1
                }

                if(btn_privacy_email.text.equals("비공개")){
                    edited_echeck=0
                }else{
                    edited_echeck=1
                }

                if(btn_privacy_dm.text.equals("비공개")){
                    edited_dcheck=0
                }else{
                    edited_dcheck=1
                }

                //----server----
                token2= SharedPreferenceController.getUserToken(this)
                val call_edited_data = UserServiceImpl.EditMyService.requsetEditmyData(
                    token2,
                    EditMyDataRequest(
                        user_name.text.toString(),
                        user_address.text.toString(),
                        user_birth.text.toString(),
                        user_phone.text.toString(),
                        user_email.text.toString(),
                        user_dm.text.toString(),
                        edited_pcheck,
                        edited_echeck,
                        edited_dcheck
                    )
                )



                call_edited_data.safeEnqueue {
                    if(it.isSuccessful){

                        Toast.makeText(
                            this,
                            "수정되었습니다.",
                            android.widget.Toast.LENGTH_LONG
                        ).show()
                    }
                }
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
                btn_privacy_dm.isEnabled=true

                btn_privacy_phone.setOnClickListener {
                    if(cnt_phone%2==0){
                        btn_privacy_phone.setText("비공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor1))
                        edited_pcheck=0
                        }
                    else {
                        btn_privacy_phone.setText("공개")
                        btn_privacy_phone.setTextColor(Color.parseColor(strColor2))
                        edited_pcheck=1
                    }
                    cnt_phone++
                }

               
                btn_privacy_email.setOnClickListener {
                    if(cnt_email%2==0){
                        btn_privacy_email.setText("비공개")
                        btn_privacy_email.setTextColor(Color.parseColor(strColor1))
                        edited_echeck=0
                    }
                    else {
                        btn_privacy_email.setText("공개")
                        btn_privacy_email.setTextColor(Color.parseColor(strColor2))
                        edited_echeck=1
                    }
                    cnt_email++
                }

                btn_privacy_dm.setOnClickListener {
                    if(cnt_dm%2==0){
                        btn_privacy_dm.setText("비공개")
                        btn_privacy_dm.setTextColor(Color.parseColor(strColor1))
                        edited_dcheck=0
                    }
                    else {
                        btn_privacy_dm.setText("공개")
                        btn_privacy_dm.setTextColor(Color.parseColor(strColor2))
                        edited_dcheck=1
                    }
                    cnt_dm++
                }

                count++
            }


        }


    }


}
