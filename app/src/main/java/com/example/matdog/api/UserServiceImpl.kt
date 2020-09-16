package com.example.matdog.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserServiceImpl {
    private const val BASE_URL = "http://3.34.224.42:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService: UserService = retrofit.create(UserService::class.java)
    val ListService: ListInter = retrofit.create(ListInter::class.java)
    val SignupService: SignupInter = retrofit.create(SignupInter::class.java)
    val IdCheckService: IdCheckInter = retrofit.create(IdCheckInter::class.java)
}