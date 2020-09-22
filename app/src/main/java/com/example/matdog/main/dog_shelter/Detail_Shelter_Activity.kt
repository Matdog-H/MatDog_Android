package com.example.matdog.main.dog_shelter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.matdog.R
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.Token
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.api.safeEnqueue
import com.example.matdog.main.pop_up.Call_popupActivity
import kotlinx.android.synthetic.main.activity_detail.*



class Detail_Shelter_Activity : AppCompatActivity() {
    private var token : String = ""
    private val registerStatus: Int = 1 //공고 상태 "보호소-shelter" 고정
    private var registerIdx: Int = 10 //공고 id -> 나중에 값 받아옴
    private var CHECK_NUM = 0 // 찜 상태

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // -----------server--------------

//        val callAgeList = UserServiceImpl.ListService.listResponse_age()
//        callAgeList.safeEnqueue {
//            if(it.isSuccessful){
//                val data = it.body()!!.listdata
//                registerIdx = data.registerIdx
//            }
//        }

        token = SharedPreferenceController.getUserToken(this)
        val callDetail = UserServiceImpl.matchingDetailService.matchingDetailResponse(
            token = token,
            registerStatus = registerStatus, //상태 "보호소" 고정
            registerIdx = registerIdx // 공고 id
        )

        callDetail.safeEnqueue {
            if (it.isSuccessful) {
                val detailregister = it.body()!!.detailregister
                // 데이터 줌
                txt_detail_happenDt.setText(detailregister.happenDt) // 등록일
                txt_detail_jong.setText(detailregister.kindCd) //종
                txt_detail_age.setText("나이 " + detailregister.age) //나이
                txt_detail_transgender.setText(detailregister.neuterYn) //중성화여부
                txt_detail_scale.setText(detailregister.weight) //체중
                txt_detail_location.setText(detailregister.careAddr) //보호장소
                txt_detail_institution.setText(detailregister.orgNm) //관할기관
                txt_detail_feature.setText(detailregister.specialMark) //특징

                Log.v("이미지주소" + detailregister.filename, "개이미지") //개이미지 string 못받아옴 null값
                Glide.with(this)
                    .load(detailregister.filename)
                    .into(detail_img) //사진

                //성별
                var sexCd: String = detailregister.sexCd
                if (sexCd == "M") {
                    //detail_sex.setImageResource(R.drawable.gender_man)
                    Glide.with(this)
                        .load(R.drawable.gender_man)
                        .into(detail_sex)
                } else {
                    //detail_sex.setImageResource(R.drawable.gender_woman)
                    Glide.with(this)
                        .load(R.drawable.gender_woman)
                        .into(detail_sex)
                }
                //찜 상태 받아오기
                var likeStatus = it.body()!!.likeStatus
                if (likeStatus == 0) {
                    btn_zzim.setSelected(false)
                    CHECK_NUM = 0
                } else {
                    btn_zzim.setSelected(true)
                    CHECK_NUM = 1
                }
            }
        }

        val intent = intent /*데이터 수신*/
        val delete_state = intent.getStringExtra("delete")

        //마이페이지에서 넘어왔을 때
        if (delete_state != null && delete_state.equals("delete_shelter")) {
            btn_delete.setVisibility(View.VISIBLE)
            btn_delete.isEnabled = true

            //삭제 버튼
            btn_delete.setOnClickListener {
                //해당 공고 삭제
                finish()
            }
        }

        init()

    }

    private fun init() {
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

            if (CHECK_NUM == 0) {
                //btn_zzim.setBackgroundResource(R.drawable.ic_heart)
                btn_zzim.setSelected(true)
                CHECK_NUM = 1
            } else {
                btn_zzim.setSelected(false)
                CHECK_NUM = 0
            }

            // 찜상태 서버에 전송
            val LikeStatusService = UserServiceImpl.LikeStatusService.likeStatusRequest(
                token = token,
                registerIdx = registerIdx, // 공고 id
                registerStatus = registerStatus, //공고 상태 "보호소"
                likestatus = CHECK_NUM // 찜상태
            )
            LikeStatusService.safeEnqueue {
                if (it.isSuccessful) {
                    Log.v("shelter찜공고상태변화:"+CHECK_NUM, it.body()!!.message)
                }

            }

        }
    }
}
