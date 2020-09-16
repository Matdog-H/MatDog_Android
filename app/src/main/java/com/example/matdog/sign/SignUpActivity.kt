package com.example.matdog.sign

import android.R.id.text2
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matdog.R
import com.example.matdog.api.SigninRequest
import com.example.matdog.api.SignupRequest
import com.example.matdog.api.SignupResponse
import com.example.matdog.api.UserServiceImpl
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //이전 화면 돌아가기
        btn_prev.setOnClickListener {
            finish()
        }



        //비밀번호 일치 여부
        signup_checkpw.addTextChangedListener(object : TextWatcher {
            //입력이 끝났을 때
            override fun afterTextChanged(p0: Editable?) {
                if(signup_pw.getText().toString().equals(signup_checkpw.getText().toString())){
                    txt_checkpw.setText("비밀번호가 일치합니다.")
                }
                else
                    txt_checkpw.setText("비밀번호가 일치하지 않습니다.")
            }
            //입력하기 전
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //텍스트 변화가 있을 시
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })


        /*
        //체크박스 체크 시 연락방법 공개로 설정하기. (서버)
        checkbox_phone.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { arg0, arg1 -> // 체크되면 모두 보이도록 설정

            if (checkbox_phone.isChecked() === true)
                signup_phone.isEnabled=true
             else
                signup_phone.isEnabled=false

        })

        checkbox_email.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { arg0, arg1 -> // 체크되면 모두 보이도록 설정
            if (checkbox_email.isChecked() === true)
                signup_email.isEnabled=true
            else
                signup_email.isEnabled=false

        })

        checkbox_dm.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { arg0, arg1 -> // 체크되면 모두 보이도록 설정
            if (checkbox_dm.isChecked() === true)
                signup_dm.isEnabled=true
            else
                signup_dm.isEnabled=false

        })
        */

        val id = signup_id.getText().toString()
        val pw = signup_pw.getText().toString()
        val pwcheck=txt_checkpw.getText().toString()
        val name = signup_name.getText().toString()
        val addr = signup_address.getText().toString()
        val birth = signup_birth.getText().toString()
        val tel = signup_birth.getText().toString()
        val email = signup_email.getText().toString()
        val dm = signup_dm.getText().toString()

        val telcheck : Int
        val emailcheck : Int
        val dmcheck : Int

        //개인정보 공개여부
        if(checkbox_phone.isChecked() === true){
            telcheck = 0
        }
        else {
            telcheck = 1
        }

        if(checkbox_email.isChecked() === true){
            emailcheck = 0
        }
        else {
            emailcheck = 1
        }

        if(checkbox_dm.isChecked() === true){
            dmcheck = 0
        }
        else {
            dmcheck = 1
        }

        //회원가입 버튼 이벤트
        btn_oksignup.setOnClickListener {
            //조건1 : 아이디가 공백이 아니고 중복확인 버튼을 눌렀는지 <-> 중복확인 버튼을 클릭 후 버튼 텍스트가 '사용 가능'으로 바꼈는지 체크
            //조건2 : 비밀번호가 일치하는지
            //조건3 : 이름, 주소, 생년월일이 공백이 아닌지
            //조건4 : 개인정보 체크박스 최소 하나 이상 선택되어야 함
            if(id.equals(""))
                Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_LONG).show()
            else if (pw.equals(""))
                Toast.makeText(this,"비밀번호를 입력해주세요",Toast.LENGTH_LONG).show()
            else if(pwcheck.equals("비밀번호가 일치하지 않습니다.")){
                Toast.makeText(this,"비밀번호가 틀렸습니다.",Toast.LENGTH_LONG).show()
            }
            else if(pwcheck.equals("비밀번호를 다시 입력해 주세요")){
                Toast.makeText(this,"비밀번호를 입력해 주세요",Toast.LENGTH_LONG).show()
            }
            else if(name.equals("")){
                Toast.makeText(this,"이름을 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else if(addr.equals("")){
                Toast.makeText(this,"주소를 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else if(birth.equals("")) {
                Toast.makeText(this, "생년월일을 입력해주세요.", Toast.LENGTH_LONG).show()
            }
            else
            {
                if(checkbox_phone.isChecked()||checkbox_email.isChecked()||checkbox_dm.isChecked()) {
                    val callSignup = UserServiceImpl.SignupService.requestSignUp(SignupRequest(id,pw,name,addr,birth,tel,telcheck,email,emailcheck,dm,dmcheck))

                    callSignup.safeEnqueue(onResponse={
                        if(it.isSuccessful){

                        }
                    })

                    Toast.makeText(this, "가입이 완료되었습니다.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                    Toast.makeText(this, "개인 정보를 선택해 주세요.", Toast.LENGTH_LONG).show()

            }


        }
    }
}


