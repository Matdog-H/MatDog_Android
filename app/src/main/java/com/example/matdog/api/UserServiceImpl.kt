package com.example.matdog.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UserServiceImpl {
    private const val BASE_URL = "http://13.209.24.149:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userService: UserService = retrofit.create(UserService::class.java)
    val ListService: ListInter = retrofit.create(ListInter::class.java)
    val SignupService: SignupInter = retrofit.create(SignupInter::class.java)

    val IdCheckService: IdCheckInter = retrofit.create(IdCheckInter::class.java)
    val EditMyService : EditMypageInter = retrofit.create(EditMypageInter::class.java)
    val MyService : SearchMyDataInter= retrofit.create(SearchMyDataInter::class.java)
    val MyListService : MyListInter= retrofit.create(MyListInter::class.java)

    val matchingDetailService : CallMatchingDetail = retrofit.create(CallMatchingDetail::class.java)
    val LikeStatusService : LikeStatus = retrofit.create(LikeStatus::class.java)
    val AnnouncementRegisterService : AnnouncementRegister = retrofit.create((AnnouncementRegister::class.java))

    val DeleteService : DeleteInter = retrofit.create(DeleteInter::class.java)

}