package com.example.matdog.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.api.*
import com.example.matdog.main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {

    private lateinit var loginData: Token

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        //로그인 버튼 이벤트
        btn_login.setOnClickListener {
            var id = editText_id.text.toString()
            var pw = editText_pw.text.toString()

            if(id.equals(""))
                Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_LONG).show()
            else if (pw.equals(""))
                Toast.makeText(this,"비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            else{
                Log.v("1111111111","111111111")
                val callLogin = UserServiceImpl.userService.requestSignIn(SigninRequest(id, pw))

                Log.v("222222222","2222222")
                callLogin.safeEnqueue(onResponse = {
                    Log.v("3333333333","3333333333")
                    if(it.isSuccessful){
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG)
                        Log.v("4444444444","4444444444")
                        loginData=it.body()!!.data
                        Log.v("5555555555","55555555")
                        SharedPreferenceController.setUserToken(this, loginData.token)
                        Log.v("token", loginData.token)
                        val login = Intent(this, MainActivity::class.java)
                        startActivity(login)
                    }
                })
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            }
        }

        //회원가입 버튼 이벤트
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
