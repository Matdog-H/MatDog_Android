package com.example.matdog.main.dog_miss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.pop_up.Call_Miss_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_miss.*

class Detail_Miss_Activity : AppCompatActivity() {
    private var token : String = ""
    private val registerStatus: Int = 2 // 공고 상태 "실종-miss" 고정
    private var registerIdx: Int = 8 //공고 id -> 나중에 값 받아옴
    private var CHECK_NUM = 0 // 찜 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_miss)


        val registerIdx_intent = intent /*데이터 수신*/
        registerIdx = intent.getIntExtra("registerIdx",0)

        // -----------server--------------
        token = SharedPreferenceController.getUserToken(this)
        val callDetailMiss = UserServiceImpl.matchingDetailService.matchingDetailResponse_miss(
            token = token,
            registerStatus = registerStatus, //상태 "실종" 고정
            registerIdx = registerIdx // 공고 id
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

                Log.v("이미지주소"+missregister.filename,"개이미지") //개이미지 string 못받아옴 null값
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
                //찜 상태 받아오기
                var likeStatus = it.body()!!.likeStatus
                if (likeStatus == 0) {
                    btn_zzim_missing.setSelected(false)
                    CHECK_NUM = 0
                } else {
                    btn_zzim_missing.setSelected(true)
                    CHECK_NUM = 1
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
            Log.v("실종 상세화면 연락처팝업",token+registerIdx)
            i.putExtra("token_miss",token)
            i.putExtra("registerIdx_miss",registerIdx)
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
