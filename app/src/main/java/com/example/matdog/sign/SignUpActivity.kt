package com.example.matdog.sign

import android.R.id.text2
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matdog.R
import com.example.matdog.api.*
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    var check : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //이전 화면 돌아가기
        btn_prev.setOnClickListener {
            finish()
        }


            //아이디 중복 확인
            btn_check.setOnClickListener {
                if(signup_id.getText().toString().equals("")){
                    Toast.makeText(this,"아이디를 입력해주세요",Toast.LENGTH_LONG).show()
                }
                else{

                   val callIdCheck = UserServiceImpl.IdCheckService.SignUpIdCheck(signup_id.getText().toString())

                    callIdCheck.safeEnqueue {
                        if(it.isSuccessful) {
                            if (it.body()!!.status == 200) {
                                check = 1
                                Toast.makeText(
                                    this,
                                    "사용 가능한 아이디입니다.",
                                    android.widget.Toast.LENGTH_LONG
                                ).show()

                            } else {

                                Toast.makeText(
                                    this,
                                    "이미 존재하는 아이디입니다. 다시 입력해주세요.",
                                    android.widget.Toast.LENGTH_LONG
                                ).show()
                                check = 0
                            }
                        }
                    }

                }

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

        //회원가입 버튼 이벤트
        btn_oksignup.setOnClickListener {
            val id = signup_id.getText().toString()
            val pw = signup_pw.getText().toString()
            val pwcheck=txt_checkpw.getText().toString()
            val name = signup_name.getText().toString()
            val addr = signup_address.getText().toString()
            val birth = signup_birth.getText().toString()
            val tel : String?= signup_birth.getText().toString()
            val email : String? = signup_email.getText().toString()
            val dm : String? = signup_dm.getText().toString()
            Log.v("아이디값", id)
            Log.v("비밀번호값",pw)
            Log.v("이름값", name)
            Log.v("주소값",addr)
            Log.v("생일",birth)
            Log.v("전화번호",tel)

            var telcheck : Int = 0
            var emailcheck : Int = 0
            var dmcheck : Int = 0

            //개인정보 공개여부
            if(checkbox_phone.isChecked() === true){
                telcheck = 1
            }
            else {
                telcheck = 0
            }

            if(checkbox_email.isChecked() === true){
                emailcheck = 1
            }
            else {
                emailcheck = 0
            }

            if(checkbox_dm.isChecked() === true){
                dmcheck = 1
            }
            else {
                dmcheck = 0
            }

            //조건1 : 아이디가 공백이 아니고 중복확인 버튼을 눌렀는지 <-> 중복확인 버튼을 클릭 후 버튼 텍스트가 '사용 가능'으로 바꼈는지 체크
            //조건2 : 비밀번호가 일치하는지
            //조건3 : 이름, 주소, 생년월일이 공백이 아닌지
            //조건4 : 개인정보 체크박스 최소 하나 이상 선택되어야 함


            //아이디 체크
            if(check==1){
                if (pw.equals(""))
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
                        Log.v("1","1")
                        Log.v("id",id)

                        val callSignup = UserServiceImpl.SignupService.requestSignUp(signupRequest = SignupRequest(id, pw, name, addr, birth, tel, telcheck, email, emailcheck, dm, dmcheck))
                        Log.v("2","2")


                        callSignup.safeEnqueue {
                            if(it.isSuccessful){
                                Log.v("callSignup", it.body().toString())
                                Log.v("데이터베이스에러",callSignup.request().toString())
                                Log.v("3","3")

                                Toast.makeText(this, "가입이 완료되었습니다.", android.widget.Toast.LENGTH_LONG).show()
                                finish()
                            }
                        }
                    }
                    else
                        Toast.makeText(this, "개인 정보를 선택해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
            else{
                Toast.makeText(this, "아이디 중복 확인을 완료해주세요", Toast.LENGTH_LONG).show()

            }

        }
    }
}


