package com.example.matdog.api
import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST

//회원가입
//interface SignupInter{
//    @POST("/user/signup")
//    fun requestSignUp(
//        @Body signupRequest: SignupRequest
//    ): Call<SignupResponse>
//}

//요청
data class SignupRequest(
    @SerializedName("id")
    val id : String,
    @SerializedName("pw")
    val pw : String,
    @SerializedName("name")
    val name : String,
    @SerializedName("addr")
    val addr : String,
    @SerializedName("birth")
    val birth : String,
    @SerializedName("tel")
    val tel : String?,
    @SerializedName("email")
    val email : String?,
    @SerializedName("dm")
    val dm : String?
)


//응답
data class SignupResponse(
    val status: Int,
    val message: String,
    val data: String?
)
