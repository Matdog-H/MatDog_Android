package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

// 공고 등록 - 보호소/실종/임시보호 공고 등록
interface AnnouncementRegister{
    @Multipart
    @POST("/program/register")
    fun announcementRegister(
        @Header("authorization") token : String, // 토큰값
        //@Body RegisterRequest : RegisterRequest
        @Part("registerStatus") registerStatus: Int, //공고 상태값
        @Part("kindCd") kindCd: RequestBody, //종
        @Part("sexCd") exCd: RequestBody, //성별
        @Part("neuterYn") neuterYn: RequestBody, //중성화 여부
        @Part("weight") weight: RequestBody, //몸무게
        @Part("age") age: RequestBody, //나이
        @Part("orgNm") orgNm: RequestBody, //관할기관
        @Part("careAddr") careAddr: RequestBody, //보호장소
        @Part("happenDt") happenDt: RequestBody, //등록일
        @Part("specialMark") specialMark: RequestBody?, //특징
        @Part("careTel")  careTel: RequestBody?, //전화번호
        @Part("email")  email: RequestBody?, //이메일
        @Part("dm") dm: RequestBody?, //dm
        @Part dogimg : MultipartBody.Part? // 강아지사진, file
   ): Call<RegisterResponse>

    @Multipart
    @POST("/program/lost")
    fun announcementRegister_miss(
        @Header("authorization") token : String, // 토큰값
        @Part("registerStatus") registerStatus: Int, //공고 상태값
        @Part("kindCd") kindCd: RequestBody, //종
        @Part("sexCd") exCd: RequestBody, //성별
        @Part("weight") weight: RequestBody, //몸무게
        @Part("age") age: RequestBody, //나이
        @Part("happenDt") happenDt: RequestBody, //등록일
        @Part("lostDate") lostDate: RequestBody, //실종 날짜 = 잃어버린 날짜
        @Part("lostPlace") lostPlace: RequestBody, //실종 장소 = 잃어버린 장소
        @Part("specialMark") specialMark: RequestBody?, //특징
        @Part("careTel")  careTel: RequestBody?, //전화번호
        @Part("email")  email: RequestBody?, //이메일
        @Part("dm") dm: RequestBody?, //dm
        @Part dogimg : MultipartBody.Part? // 강아지사진, file
    ): Call<RegisterResponseMiss>

    @Multipart
    @POST("/program/spot")
    fun announcementRegister_protect(
        @Header("authorization") token : String, // 토큰값
        @Part("registerStatus") registerStatus: Int, //공고 상태값
        @Part("kindCd") kindCd: RequestBody, //종
        @Part("sexCd") exCd: RequestBody, //성별
        @Part("weight") weight: RequestBody, //몸무게
        @Part("age") age: RequestBody, //나이
        @Part("careAddr") careAddr: RequestBody, //보호장소
        @Part("findPlace") findPlace: RequestBody, //발견장소
        @Part("findDate") findDate: RequestBody, //발견 날짜
        @Part("happenDt") happenDt: RequestBody, //등록일
        @Part("specialMark") specialMark: RequestBody?, //특징
        @Part("careTel")  careTel: RequestBody?, //전화번호
        @Part("email")  email: RequestBody?, //이메일
        @Part("dm") dm: RequestBody?, //dm
        @Part dogimg : MultipartBody.Part? // 강아지사진, file
    ): Call<RegisterResponseProtect>
}

// 일반(보호소) 공고 응답
data class RegisterResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val RegisterRequest: RegisterRequest
)

// 일반(보호소) 공고 등록 요청
data class RegisterRequest(
    @SerializedName("registerStatus") //공고 상태값
    val registerStatus: Int,
    @SerializedName("kindCd") //종
    val kindCd: String,
    @SerializedName("sexCd") //성별
    val sexCd: String,
    @SerializedName("neuterYn") //중성화 여부
    val neuterYn: String,
    @SerializedName("weight") //몸무게
    val weight: String,
    @SerializedName("age") //나이
    val age: String,
    @SerializedName("orgNm") //관할기관
    val orgNm: String,
    @SerializedName("careAddr") //보호장소
    val careAddr: String,
    @SerializedName("happenDt") //등록일
    val happenDt: String,
    @SerializedName("specialMark") //특징
    val specialMark: String?,
    @SerializedName("careTel") //전화번호
    val careTel: String?,
    @SerializedName("email") //이메일
    val email: String?,
    @SerializedName("dm") //dm
    val dm: String?,
    @SerializedName("dogimg") //공고 등록 강아지 사진
    val dogimg: String?
)


// 실종 공고 응답
data class RegisterResponseMiss(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val RegisterRequestMiss: RegisterRequestMiss
)

// 실종 공고 등록 요청
data class RegisterRequestMiss(
    @SerializedName("registerStatus") //공고 상태값
    val registerStatus : Int,
    @SerializedName("kindCd") //종
    val kindCd : String,
    @SerializedName("sexCd") //성별
    val sexCd : String,
    @SerializedName("weight") //몸무게
    val weight : String,
    @SerializedName("age") //나이
    val age	: String,
    @SerializedName("happenDt") //등록일
    val happenDt : String,
    @SerializedName("lostDate") //실종 날짜 = 잃어버린 날짜
    val lostDate : String,
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
    @SerializedName("dogimg") //공고 등록 강아지 사진
    val dogimg : String?
)

// 임시보호 공고 응답
data class RegisterResponseProtect(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val RegisterRequestProtect: RegisterRequestProtect
)

// 임시보호 공고 등록 요청
data class RegisterRequestProtect(
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
    @SerializedName("dogimg") //공고 등록 강아지 사진
    val dogimg : String?
)