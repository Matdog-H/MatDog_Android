package com.example.matdog.main.dog_protect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.pop_up.Call_Protect_popupActivity
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_protect.*

class Detail_Protect_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_protect)

        // -----------server--------------
        val callProtectDetail = UserServiceImpl.matchingDetailService.matchingDetailResponse_protect(
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNYXREb2ciLCJ1c2VySWR4Ijo2fQ.IuYm_J1zncxiL00mMH5n_Sc7eBmT5elC9_8H86lKiH0",
            registerStatus = 3, //상태 "임시보호-protect" 고정
            registerIdx = 31 // 공고 id
        )

        callProtectDetail.safeEnqueue {
            if(it.isSuccessful){
                val protectregister = it.body()!!.protectregister
                // 데이터 줌
                txt_detail_happenDt_protect.setText(protectregister.happenDt) // 등록일
                txt_detail_jong_protect.setText(protectregister.kindCd) //종
                txt_detail_age_protect.setText("나이 "+protectregister.age) //나이
                txt_detail_scale_protect.setText(protectregister.weight) //체중
                txt_detail_protect_location.setText(protectregister.findPlace) //발견장소
                txt_detail_protect_date.setText(protectregister.findDate) //발견날짜
                txt_detail_protect_location_missing.setText(protectregister.careAddr) // 보호장소
                txt_detail_feature_protect.setText(protectregister.specialMark) //특징

                Log.v("개ㅐ애애"+protectregister.filename,"개이미지") //개이미지 string 못받아옴 null값
                Glide.with(this)
                    .load(protectregister.filename)
                    .into(detail_protect_img) //사진

                //성별
                var sexCd : String = protectregister.sexCd
                if(sexCd == "M"){
                    //detail_sex.setImageResource(R.drawable.gender_man)
                    Glide.with(this)
                        .load(R.drawable.gender_man)
                        .into(detail_sex_protect)
                }else{
                    //detail_sex.setImageResource(R.drawable.gender_woman)
                    Glide.with(this)
                        .load(R.drawable.gender_woman)
                        .into(detail_sex_protect)
                }

            }
        }

        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        // 사진 주기
        detail_protect_img.setImageResource(R.drawable.taepoong2)

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
        //연락처 팝업버튼
        btn_detail_call_protect.setOnClickListener {
            val i = Intent(this, Call_Protect_popupActivity::class.java)
            startActivity(i)
        }

        // 뒤로가기
        btn_detail_back_protect.setOnClickListener {
            finish()
        }

        // 찜버튼
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
