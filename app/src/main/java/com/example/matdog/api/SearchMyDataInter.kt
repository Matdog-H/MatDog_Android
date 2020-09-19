package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface SearchMyDataInter{
    @GET("/user/my")
    fun Search_mydata(
        @Header("authorization") key : String // 토큰값
    ): Call<MydataResponse>
}

data class MydataResponse(
    @SerializedName("status")
    val status : Int,
    @SerializedName("message")
    val message : String,
    @SerializedName("data")
    val mydata: MyData
)

data class MyData(
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