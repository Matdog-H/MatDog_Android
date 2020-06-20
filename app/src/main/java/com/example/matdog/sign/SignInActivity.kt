package com.example.matdog.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.main.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        //로그인 버튼 이벤트
        btn_login.setOnClickListener {
            if(editText_id.getText().toString().equals(""))
                Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_LONG).show()
            else if (editText_pw.getText().toString().equals(""))
                Toast.makeText(this,"비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            else{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        //회원가입 버튼 이벤트
        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}
