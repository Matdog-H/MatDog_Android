package com.example.matdog.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    /**
     *로그인 V
     */
    @POST("/user/signin")
    fun requestSignIn(
        @Body signinRequest: SigninRequest
    ): Call<SigninResponse>
}