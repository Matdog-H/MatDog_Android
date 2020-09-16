package com.example.matdog.main.dog_shelter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_miss.*
import kotlinx.android.synthetic.main.activity_detail_popup.*
import kotlinx.android.synthetic.main.activity_detail_popup.view.*


class Detail_Shelter_Activity : AppCompatActivity() {
    internal lateinit var viewpager : ViewPager
    var CHECK_NUM = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // -----------server--------------
        val callDetail = UserServiceImpl.matchingDetailService.matchingDetailResponse(
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJNYXREb2ciLCJ1c2VySWR4Ijo2fQ.IuYm_J1zncxiL00mMH5n_Sc7eBmT5elC9_8H86lKiH0",
            registerStatus = 1, //상태 "보호소" 고정
            registerIdx = 10 // 공고 id
        )

        callDetail.safeEnqueue {
            if(it.isSuccessful){
                val detailregister = it.body()!!.detailregister
                // 데이터 줌
                txt_detail_happenDt.setText(detailregister.happenDt) // 등록일
                txt_detail_jong.setText(detailregister.kindCd) //종
                txt_detail_age.setText("나이 "+detailregister.age) //나이
                txt_detail_transgender.setText(detailregister.neuterYn) //중성화여부
                txt_detail_scale.setText(detailregister.weight) //체중
                txt_detail_location.setText(detailregister.careAddr) //보호장소
                txt_detail_institution.setText(detailregister.orgNm) //관할기관
                txt_detail_feature.setText(detailregister.specialMark) //특징

                Log.v("개ㅐ애애"+detailregister.filename,"개이미지") //개이미지 string 못받아옴 null값
                Glide.with(this)
                    .load(detailregister.filename)
                    .into(detail_img) //사진

                //성별
                var sexCd : String = detailregister.sexCd
                if(sexCd == "M"){
                    //detail_sex.setImageResource(R.drawable.gender_man)
                    Glide.with(this)
                        .load(R.drawable.gender_man)
                        .into(detail_sex)
                }else{
                    //detail_sex.setImageResource(R.drawable.gender_woman)
                    Glide.with(this)
                        .load(R.drawable.gender_woman)
                        .into(detail_sex)
                }

            }
        }

        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        //마이페이지에서 넘어왔을 때
        if (delete_state != null && delete_state.equals("delete_shelter")){
            btn_delete.setVisibility(View.VISIBLE)
            btn_delete.isEnabled=true

            //삭제 버튼
            btn_delete.setOnClickListener {
                //해당 공고 삭제
                finish()
            }
        }

        init()

    }

    private fun init(){
        //연락처 팝업버튼
        btn_detail_call.setOnClickListener {
            val i = Intent(this, Call_popupActivity::class.java)
            startActivity(i)
        }

        // 뒤로가기
        btn_detail_back.setOnClickListener {
            finish()
        }

        // 찜버튼
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
