package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

// 분양공고보기 - 최신순.ver, 나이순.ver
interface ListInter{

    //----------품종 공고 리스트 가져오기 ---------------------
    @GET("/program/register/finddog/{sort}")
    fun list_register_result(
        @Path("sort") sort : Int,
        @Query("kindCd") kindCd: String
        //@Field("kindCd") kindCd: String
    ): Call<ListResponse>

    @GET("/program/lost/finddog/{sort}")
    fun list_lost_result(
        @Path("sort") sort : Int,
        @Query("kindCd") kindCd: String
    ): Call<ListResponse>

    @GET("/program/spot/finddog/{sort}")
    fun list_spot_result(
        @Path("sort") sort : Int,
        @Query("kindCd") kindCd: String
    ): Call<ListResponse>

    //-----------공고 검색----------------------
    @GET("/program/register/search/{sort}")
    fun list_register_search(
        @Path("sort") sort : Int,
        @Query("keyword") keyword: String?
    ): Call<ListResponse>

    @GET("/program/lost/search/{sort}")
    fun list_lost_search(
        @Path("sort") sort : Int,
        @Query("keyword") keyword: String?
    ): Call<ListResponse>

    @GET("/program/spot/search/{sort}")
    fun list_spot_search(
        @Path("sort") sort : Int,
        @Query("keyword") keyword: String?
    ): Call<ListResponse>

}

data class ListResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val listdata: ArrayList<ListAllData>?
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
    val filename : String?
)
