package com.example.matdog.api

data class SigninResponse(
    val status: Int,
    val message: String,
    val data: Token
)

data  class Token(
    val token : String
)
