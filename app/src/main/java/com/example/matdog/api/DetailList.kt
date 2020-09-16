package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.io.File
import java.net.URI
import java.util.*

// 공고 상세화면 - 보호소/실종/임시보호 공고 상세조회
interface callMatchingDetail{
    @GET("/program/viewall/{registerStatus}/{registerIdx}")
    fun matchingDetailResponse(
        @Header("authorization") token : String, // 토큰값
        @Path("registerStatus") registerStatus: Int,
        @Path("registerIdx") registerIdx: Int
    ): Call<DetailResponse>

    @GET("/program/viewalllost/{registerStatus}/{registerIdx}")
    fun matchingDetailResponse_miss(
        @Header("authorization") token : String, // 토큰값
        @Path("registerStatus") registertatus: Int,
        @Path("registerIdx") registerIdx: Int
    ): Call<DetailResponseMiss>

    @GET("/program/viewallspot/{registerStatus}/{registerIdx}")
    fun matchingDetailResponse_protect(
        @Header("authorization") token : String, // 토큰값
        @Path("registerStatus") registerStatus: Int,
        @Path("registerIdx") registerIdx: Int
    ): Call<DetailResponseProtect>
}

// 일반(보호소) 공고
data class DetailResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("register")
    val detailregister: DetailData
)

//register
data class DetailData(
    @SerializedName("registerStatus") //공고 상태값
    val registerStatus : Int,
    @SerializedName("kindCd") //종
    val kindCd : String,
    @SerializedName("sexCd") //성별
    val sexCd : String,
    @SerializedName("neuterYn") //중성화 여부
    val neuterYn : String,
    @SerializedName("weight") //몸무게
    val weight : String,
    @SerializedName("age") //나이
    val age	: String,
    @SerializedName("orgNm") //관할기관
    val orgNm : String?,
    @SerializedName("careAddr") //보호장소
    val careAddr : String,
    @SerializedName("happenDt") //등록일
    val happenDt : String,
    @SerializedName("specialMark") //특징
    val specialMark : String?,
    @SerializedName("careTel") //전화번호
    val careTel : String?,
    @SerializedName("email") //이메일
    val email : String?,
    @SerializedName("dm") //dm
    val dm : String?,
    @SerializedName("filename") //공고 등록 강아지 사진
    val filename : String?
)


// 실종 공고
data class DetailResponseMiss(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("register")
    val missregister: DetailDataMiss
)
//lost
data class DetailDataMiss(
    @SerializedName("registerStatus") //공고 상태값
    val registerStatus : Int,
    @SerializedName("kindCd") //종
    val kindCd : String,
    @SerializedName("sexCd") //성별
    val sexCd : String,
    @SerializedName("weight") //몸무게
    val weight : String?,
    @SerializedName("age") //나이
    val age	: String,
    @SerializedName("happenDt") //등록일
    val happenDt : String,
    @SerializedName("lostDate") //실종 날자 = 잃어버린 날짜
    val lostDate : String?,
    @SerializedName("lostPlace") //실종 장소 = 잃어버린 장소
    val lostPlace : String,
    @SerializedName("specialMark") //특징
    val specialMark : String?,
    @SerializedName("careTel") //전화번호
    val careTel : String?,
    @SerializedName("email") //이메일
    val email : String?,
    @SerializedName("dm") //dm
    val dm : String?,
    @SerializedName("filename") //공고 등록 강아지 사진
    val filename : String?
)

// 임시보호 공고
data class DetailResponseProtect(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("register")
    val protectregister: DetailDataProtect
)
//spot->protect?로 이름 바꾸는거 좋을듯
data class DetailDataProtect(
    @SerializedName("registerStatus") //공고 상태값
    val registerStatus : Int,
    @SerializedName("kindCd") //종
    val kindCd : String,
    @SerializedName("sexCd") //성별
    val sexCd : String,
    @SerializedName("weight") //몸무게
    val weight : String?,
    @SerializedName("age") //나이
    val age	: String,
    @SerializedName("careAddr") //보호장소
    val careAddr : String,
    @SerializedName("findPlace") //발견장소
    val findPlace : String,
    @SerializedName("findDate") //발견 날짜
    val findDate : String,
    @SerializedName("happenDt") //등록일
    val happenDt : String,
    @SerializedName("specialMark") //특징
    val specialMark : String?,
    @SerializedName("careTel") //전화번호
    val careTel : String?,
    @SerializedName("email") //이메일
    val email : String?,
    @SerializedName("dm") //dm
    val dm : String?,
    @SerializedName("filename") //공고 등록 강아지 사진
    val filename : String?
)