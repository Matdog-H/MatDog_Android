package com.example.matdog.api

import android.telecom.Call
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

//회원가입
interface IdCheckInter{
    //아이디 중복 체크
    @POST("/user/check/{id}")
    fun requestSignUpIdCheck(
        @Body idcheckRequest: IdCheckRequest
    ): Call<IdCheckResponse>
}


//아이디 중복 검사
data class IdCheckRequest(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val signupdata: List<SignupData>
)


data  class SignupData(
    @SerializedName("userIdx")
    val userIdx : Int,
    @SerializedName("id")
    val id : String,
    @SerializedName("pw")
    val pw : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("addr")
    val addr : String,
    @SerializedName("birth")
    val birth : String,
    @SerializedName("tel")
    val tel : String,
    @SerializedName("email")
    val email : String,
    @SerializedName("memo")
    val memo : String
)


//응답
data class IdCheckResponse(
    val status: Int,
    val message: String,
    val data: String?
)