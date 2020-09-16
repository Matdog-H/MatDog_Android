package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

// 분양공고보기 - 최신순.ver, 나이순.ver
interface ListInter{
    @GET("/program/allregister")
    fun listResponse_new(
        //@Header("authorization") key : String // 토큰값
    ): Call<ListResponse>

    @GET("/program/allregisterAge")
    fun listResponse_age(
        //@Header("authorization") key : String // 토큰값
    ): Call<ListResponse>
}

data class ListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val listdata: List<ListAllData>
)

data  class ListAllData(
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
