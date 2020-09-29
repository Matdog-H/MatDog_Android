package com.example.matdog.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface DeleteInter{
    @DELETE("/program/register/{registerIdx}")
    fun delete_request_shelter(
        @Header("authorization") key : String, // 토큰값
        @Path("registerIdx") registerIdx:Int
    ): Call<DeleteResponse>

    @DELETE("/program/lost/{registerIdx}")
    fun delete_request_miss(
        @Header("authorization") key : String, // 토큰값
        @Path("registerIdx") registerIdx:Int
    ): Call<DeleteResponse>

    @DELETE("/program/spot/{registerIdx}")
    fun delete_request_protected(
        @Header("authorization") key : String, // 토큰값
        @Path("registerIdx") registerIdx:Int
    ): Call<DeleteResponse>

    data class DeleteResponse(
        @SerializedName("status")
        val status : Int,
        @SerializedName("message")
        val message : String,
        @SerializedName("data")
        val signupdata: String?
    )
}