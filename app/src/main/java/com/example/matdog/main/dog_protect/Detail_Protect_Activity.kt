package com.example.matdog.main.dog_protect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.pop_up.Call_Protect_popupActivity
import kotlinx.android.synthetic.main.activity_detail_miss.*
import kotlinx.android.synthetic.main.activity_detail_protect.*

class Detail_Protect_Activity : AppCompatActivity() {
    private var token : String = ""
    private val registerStatus: Int = 3 // 공고 상태 "임시보호-protect" 고정
    private var registerIdx: Int = 31 //공고 id -> 나중에 값 받아옴
    private var CHECK_NUM = 0 // 찜 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_protect)



        val registerIdx_intent = intent /*데이터 수신*/
        val registerIdx = intent.getIntExtra("registerIdx",0)

        // -----------server--------------
        token = SharedPreferenceController.getUserToken(this)
        val callProtectDetail = UserServiceImpl.matchingDetailService.matchingDetailResponse_protect(
            token = token,
            registerStatus = registerStatus, //상태 "임시보호-protect" 고정
            registerIdx = registerIdx // 공고 id
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

                Log.v("이미지주소"+protectregister.filename,"개이미지") //개이미지 string 못받아옴 null값
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
                //찜 상태 받아오기
                var likeStatus = it.body()!!.likeStatus
                if (likeStatus == 0) {
                    btn_zzim_protect.setSelected(false)
                    CHECK_NUM = 0
                } else {
                    btn_zzim_protect.setSelected(true)
                    CHECK_NUM = 1
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
            i.putExtra("registerIdx_protect",registerIdx)
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

            // 찜상태 서버에 전송
            val LikeStatusService = UserServiceImpl.LikeStatusService.likeStatusRequest(
                token = token,
                registerIdx = registerIdx, // 공고 id
                registerStatus = registerStatus, //공고 상태 "실종"
                likestatus = CHECK_NUM // 찜상태
            )
            LikeStatusService.safeEnqueue {
                if (it.isSuccessful) {
                    Log.v("miss찜공고상태변화:"+CHECK_NUM, it.body()!!.message)
                }

            }

        }

    }
}
