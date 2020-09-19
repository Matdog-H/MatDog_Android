package com.example.matdog.api
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//회원가입
interface SignupInter{
    @POST("/user/signup")
    fun requestSignUp(
        @Body signupRequest: SignupRequest
    ): Call<SignupResponse>

}

//요청
data class SignupRequest(
    @SerializedName("id") //아이디
    val id : String,
    @SerializedName("pw") //비밀번호
    val pw : String,
    @SerializedName("name") //이름
    val name : String,
    @SerializedName("addr") //주소
    val addr : String,
    @SerializedName("birth") //생년월일
    val birth : String,
    @SerializedName("tel") //전화번호
    val tel : String?,
    @SerializedName("telcheck") //전화번호 공개여부
    val telcheck : Int,
    @SerializedName("email") //이메일
    val email : String?,
    @SerializedName("emailcheck") //이메일 공개여부
    val emailcheck : Int,
    @SerializedName("dm") //DM
    val dm : String?,
    @SerializedName("dmcheck") //DM 공개여부
    val dmcheck : Int
)


//응답
data class SignupResponse(
    val status: Int,
    val message: String,
    val data: String?
)
