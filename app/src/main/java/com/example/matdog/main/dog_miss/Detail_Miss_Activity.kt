package com.example.matdog.main.dog_miss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.SigninRequest
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.MainActivity
import com.example.matdog.main.pop_up.Call_Miss_popupActivity
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_miss.*

class Detail_Miss_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_miss)

        // 사진 주기
        detail_miss_img.setImageResource(R.drawable.taepoong2)

        // -----------server--------------
        val callDetailMiss = UserServiceImpl.matchingDetailService.matchingDetailResponse_miss(
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNYXREb2ciLCJ1c2VySWR4Ijo2fQ.IuYm_J1zncxiL00mMH5n_Sc7eBmT5elC9_8H86lKiH0",
            registertatus = 2, //상태 "실종" 고정
            registerIdx = 8 // 공고 id
        )

        callDetailMiss.safeEnqueue {
            if(it.isSuccessful){
                val missregister = it.body()!!.missregister
                // 데이터 줌
                txt_detail_happenDt_missing.setText(missregister.happenDt) // 등록일
                txt_detail_jong_missing.setText(missregister.kindCd) //종
                txt_detail_age_missing.setText("나이 "+missregister.age) //나이
                txt_detail_scale_missing.setText(missregister.weight) //체중
                txt_detail_missing_location.setText(missregister.lostPlace) //잃어버린 장소
                txt_detail_missing_date.setText(missregister.lostDate) //잃어버린 날짜
                txt_detail_feature_miss.setText(missregister.specialMark) //특징

                Log.v("개ㅐ애애"+missregister.filename,"개이미지") //개이미지 string 못받아옴 null값
                Glide.with(this)
                    .load(missregister.filename)
                    .into(detail_miss_img) //사진

                //성별
                var sexCd : String = missregister.sexCd
                if(sexCd == "M"){
                    //detail_sex.setImageResource(R.drawable.gender_man)
                    Glide.with(this)
                        .load(R.drawable.gender_man)
                        .into(detail_sex_miss)
                }else{
                    //detail_sex.setImageResource(R.drawable.gender_woman)
                    Glide.with(this)
                        .load(R.drawable.gender_woman)
                        .into(detail_sex_miss)
                }

            }
        }


        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        //마이페이지에서 넘어왔을 때
        if (delete_state != null && delete_state.equals("delete_miss")){
            btn_delete_miss.setVisibility(View.VISIBLE)
            btn_delete_miss.isEnabled=true

            //삭제 버튼
            btn_delete_miss.setOnClickListener {
                //해당 공고 삭제
                finish()
            }
        }

        init()

    }

    private fun init(){
        //연락처 팝업버튼
        btn_detail_call_missing.setOnClickListener {
            val i = Intent(this, Call_Miss_popupActivity::class.java)
            startActivity(i)
        }

        // 뒤로가기
        btn_detail_back_missing.setOnClickListener {
            finish()
        }

        // 찜버튼
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
