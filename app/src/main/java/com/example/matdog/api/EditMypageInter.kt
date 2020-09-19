package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface EditMypageInter{
    @PUT("/user")
        fun requsetEditmyData(
        @Header("authorization") key : String, // 토큰값
        @Body editmydataRequest : EditMyDataRequest
    ): Call<EditMydataResponse>
}

data class EditMyDataRequest(
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
    @SerializedName("dm")
    val dm : String,
    @SerializedName("telcheck")
    val telcheck : Int,
    @SerializedName("emailcheck")
    val emailcheck : Int,
    @SerializedName("dmcheck")
    val dmcheck : Int


)

data class EditMydataResponse(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val edited_mydata: MyEditData
)

data class MyEditData(
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