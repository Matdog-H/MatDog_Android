package com.example.matdog.api

import retrofit2.http.GET
import retrofit2.http.Header

data class SigninResponse(
    val status: Int,
    val message: String,
    val data: Token
)

data  class Token(
    val token : String
)