package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

//회원가입
interface IdCheckInter{
    //아이디 중복 체크
    @POST("/user/check/{id}")
    fun SignUpIdCheck(
        //@Header("authorization") key : String // 토큰값
        @Path("id") id:String
    ): Call<IdCheckResponse>
}


//아이디 중복 검사
data class IdCheckResponse(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String
//    @SerializedName("data")
//    val signupdata: SignupData
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
    @SerializedName("telcheck")
    val telcheck : Int,
    @SerializedName("email")
    val email : String,
    @SerializedName("emailcheck")
    val emailcheck : Int,
    @SerializedName("dm")
    val dm : String,
    @SerializedName("dmcheck")
    val dmcheck : Int
)

