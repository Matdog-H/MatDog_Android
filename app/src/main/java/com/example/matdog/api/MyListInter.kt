package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface MyListInter{
    // @GET("/program/allregister")
    //    fun listResponse_new(
    //        //@Header("authorization") key : String // 토큰값
    //    ): Call<ListResponse>

    @GET("/user/likes")
    fun listResponse_mylike(
        //@Header("authorization") key : String // 토큰값
    ): Call<My_ListResponse>

    @GET("/user/write")
    fun listResponse_mywrite(
        //@Header("authorization") key : String // 토큰값
    ): Call<My_ListResponse>
}

data class My_ListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("registerRes")
    val mylistdata: List<MyListAllData>
)

data class MyListAllData(
    @SerializedName("userIdx")
    val userIdx : Int,
    @SerializedName("registerIdx")
    val registerIdx : Int,
    @SerializedName("registerStatus")
    val registerStatus : Int,
    @SerializedName("kindCd")
    val kindCd : String,
    @SerializedName("sexCd")
    val sexCd : String,
    @SerializedName("age")
    val age : String,
    @SerializedName("happenDt")
    val happenDt : String,
    @SerializedName("filename")
    val filename : String
)