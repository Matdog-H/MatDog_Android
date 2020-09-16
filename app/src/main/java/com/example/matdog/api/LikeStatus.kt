package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

//공고 좋아요(찜) 상태변경 요청
interface LikeStatus {
    @POST("like/{registerIdx}/{registerStatus}/{likestatus}")
    fun likeStatusRequest(
        @Header("authorization") token : String, // 토큰값
        @Path("registerIdx") registerIdx: Int,
        @Path("registerStatus") registerStatus: Int,
        @Path("likestatus") likestatus: Int
    ): Call<LikeStatusResponse>
}

//응답
data class LikeStatusResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String?
)