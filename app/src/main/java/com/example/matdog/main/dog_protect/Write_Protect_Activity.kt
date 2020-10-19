package com.example.matdog.main.dog_protect

/*
* WriteProtectActivity.kt
* 유기견 찾기_ 임시보호 공고등록
* activity_write_protect.xml
 */

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.ExifInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseIntArray
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.api.RegisterResponseProtect
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write_miss.*
import kotlinx.android.synthetic.main.activity_write_protect.*
import kotlinx.android.synthetic.main.activity_write_protect.btn_okwrite_protect
import kotlinx.android.synthetic.main.activity_write_protect.ic_back_protect
import kotlinx.android.synthetic.main.activity_write_protect.radionotouch_protect
import kotlinx.android.synthetic.main.activity_write_protect.species_modify_protect
import kotlinx.android.synthetic.main.activity_write_protect.species_name_protect
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Write_Protect_Activity : AppCompatActivity() {
    private var token: String = ""
    private val registerStatus: Int = 3 //공고 상태 "임시보호-protect" 고정
    var dogfile: MultipartBody.Part? = null // dogimg

    // 연락처 수정 팝업
    var careTel_rb: RequestBody? = null//전화번호
    var email_rb: RequestBody? = null
    var dm_rb: RequestBody? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_protect)

        var dog_breed = intent.getStringExtra("breed") // 보호소 리스트 - 분석결과값

        species_name_protect.setText(dog_breed)

        picture() // 앨범에서 사진 가져오기

        // --------------- 데이터 저장 --------------------
        // 성별
        var sexCd: String = "F" // 여 기본
        radioGroupgender_protect.setOnCheckedChangeListener { group, checkedId ->
            Log.v("성별라디오 버튼", "라디오버튼 선택함")
            if (checkedId == R.id.radioFemale_protect) { // 여 선택
                if (radioFemale_protect.isChecked == true) {
                    radioFemale_protect.isChecked = true
                    radioMale_protect.isChecked = false
                    sexCd = "F"
                    Log.v("성별라디오 버튼", sexCd)
                }
            } else if (checkedId == R.id.radioMale_protect) // 남 선택
                if (radioMale_protect.isChecked == true) {
                    radioFemale_protect.isChecked = false
                    radioMale_protect.isChecked = true
                    sexCd = "M"
                    Log.v("성별라디오 버튼", sexCd)
                }
        }


        ic_back_protect.setOnClickListener {// 뒤로가기 버튼 눌렀을 때
            finish()
        }

        btn_okwrite_protect.setOnClickListener {// 등록하기 버튼 눌렀을 때

            if (species_name_protect.getText().toString().equals(""))
                Toast.makeText(this, "종을 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtweight_protect.getText().toString().equals(""))
                Toast.makeText(this, "몸무게를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtyear_protect.getText().toString().equals(""))
                Toast.makeText(this, "나이를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtplace_protect.getText().toString().equals(""))
                Toast.makeText(this, "보호장소를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtmissplace_protect.getText().toString().equals(""))
                Toast.makeText(this, "발견한 장소를 입력해주세요.", Toast.LENGTH_LONG).show()
            //else if (edtmissday_protect.getText().toString().equals(""))
                //Toast.makeText(this, "발견한 날짜를 입력해주세요.", Toast.LENGTH_LONG).show()
             else if(edt_findday_year.getText().toString().equals(""))
                Toast.makeText(this, "발견 년도를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edt_findday_year.length()!=4)
                Toast.makeText(this, "올바른 년도 형식을 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edt_findday_month.getText().toString().equals(""))
                Toast.makeText(this, "발견된 달을 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edt_findday_day.getText().toString().equals(""))
                Toast.makeText(this, "발견된 날을 입력해주세요.", Toast.LENGTH_LONG).show()
            else {
                // ---------- 데이터저장------------
                var kindCd = RequestBody.create(
                    MediaType.parse("text/plain"),
                    species_name_protect.getText().toString()
                ) // 종
                var sexCd_rb = RequestBody.create(MediaType.parse("text/plain"), sexCd)  //성별
                var weight = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtweight_protect.getText().toString()
                ) // 몸무게
                var age = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtyear_protect.getText().toString()
                ) // 나이
                var careAddr = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtplace_protect.getText().toString()
                ) // 보호장소
                var findPlace = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtmissplace_protect.getText().toString()
                ) // 발견장소

                //var findDate = RequestBody.create(
//                    MediaType.parse("text/plain"),
//                    edtmissday_protect.getText().toString()
//                ) // 발견날짜

                var find_day_year :String = edt_findday_year.text.toString()
                var find_day_month :String = edt_findday_month.text.toString()
                var find_day_day :String = edt_findday_day.text.toString()
                var find_date : String =find_day_year+"-"+find_day_month+"-"+find_day_day

                var findDate = RequestBody.create(
                    MediaType.parse("text/plain"),
                   find_date
                ) // 발견날짜


                // 등록일
                val now = LocalDateTime.now()
                val happenDt = RequestBody.create(
                    MediaType.parse("text/plain"), now.format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                )

                var specialMark = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtfeature_protect.getText().toString()
                ) //특징

                //연락처 수정
                var careTel: RequestBody? = null
                if (intent.hasExtra("tel")) {
                    careTel = RequestBody.create(
                        MediaType.parse("text/plain"),
                        intent.getStringExtra("tel").toString()
                    )
                }
                var email: RequestBody? = null
                if (intent.hasExtra("email")) {
                    email = RequestBody.create(
                        MediaType.parse("text/plain"),
                        intent.getStringExtra("email").toString()
                    )
                }
                var dm: RequestBody? = null
                if (intent.hasExtra("dm")) {
                    dm = RequestBody.create(
                        MediaType.parse("text/plain"),
                        intent.getStringExtra("dm").toString()
                    )
                }

                // ------------server -------------
                token = SharedPreferenceController.getUserToken(this)
                val callRegisterResponseProtect =
                    UserServiceImpl.AnnouncementRegisterService.announcementRegister_protect(
                        token,
                        registerStatus,
                        kindCd,
                        sexCd_rb,
                        weight,
                        age,
                        careAddr,
                        findPlace,
                        findDate,
                        happenDt,
                        specialMark,
                        careTel,
                        email,
                        dm,
                        dogfile
                    )

                callRegisterResponseProtect.enqueue(object : Callback<RegisterResponseProtect> {
                    override fun onFailure(call: Call<RegisterResponseProtect>, t: Throwable) {
                        Log.d("*****Write_Protect_Activity::", t.toString())
                    }

                    override fun onResponse(
                        call: Call<RegisterResponseProtect>,
                        response: Response<RegisterResponseProtect>
                    ) {
                        if (response.isSuccessful) {
                            Log.v("임시보호공고등록성공", response.body()!!.message)
                            Log.v("공고등록응답확인", response.body()!!.toString())
                            finish()
                            Toast.makeText(
                                this@Write_Protect_Activity,
                                "등록되었습니다.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.v("임시보호공고등록(notsucess)", response.body()!!.message)
                            Log.v("공고등록응답확인(notsucess)", response.body()!!.toString())
                        }
                    }
                })
            }
        }

        species_modify_protect.setOnClickListener {// 종 수정버튼 눌렀을 때,
            // 종 수정가능해짐
            species_name_protect.isEnabled = true
            species_name_protect.setTextColor(Color.parseColor("#fb777a"))
        }

        radioretouch_protect.setOnClickListener{ // "이전 연락처 그대로" 라디오버튼 눌렀을때,
            if(radioretouch_protect.isChecked == true){
                radioretouch_protect.isChecked = true
                radionotouch_protect.isChecked = false
            }
            // 연락처 다시 null값으로 초기화
            careTel_rb = null//전화번호
            email_rb = null
            dm_rb = null
        }

        radionotouch_protect.setOnClickListener {// 연락처수정 라디오버튼을 눌렀을 때,
            if(radionotouch_protect.isChecked == true){
                radionotouch_protect.isChecked = true
                radioretouch_protect.isChecked = false
            }
            // 연락처수정할 수 있는 팝업창 띄움
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivityForResult(i, 33333)

        }
    }

    private fun picture() {
        // +버튼 ->이미지 클릭이벤트.
        picture_write1_protect.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    // 권한 없어서 요청
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(
                        permissions,
                        PERMISSION_CODE
                    )
                } else {// 권한 있을 때
                    pickImageFromGallery()
                }
            }
        }
    }

    // 앨범에서 이미지 가져오기
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001

        //카메라
        const val CAMERA_BACK = "0"
        const val CAMERA_FRONT = "1"

        private val ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 90)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 180)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 270)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 360)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.let {
                var selectedPictureUri = it.data
                val options = BitmapFactory.Options()
                val inputStream: InputStream? =
                    contentResolver.openInputStream(selectedPictureUri!!) // !! 강제로 not null로 바꿔줌..
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)

                val file = File(selectedPictureUri.toString())
                Log.v("강아지사진file2객체", file.toString())

                // 가져온 File 객체를 RequestBody 객체로 변환
                val photoBody = RequestBody.create(
                    MediaType.parse("image/jpg"),
                    byteArrayOutputStream.toByteArray()
                ) // null값 나옴..
                Log.v("bytearray확인", byteArrayOutputStream.toByteArray().toString())
                Log.v("강아지사진requestbody객체", photoBody.toString())

                // 우리에게 필요한 Multipart.Part로 변환
                dogfile = MultipartBody.Part.createFormData("dogimg", file.name + ".jpg", photoBody)
                Log.v("강아지사진", dogfile.toString())

                picture_write1_protect.setImageURI(data?.data)
            }
        } else if (resultCode == 12345) {
            if (requestCode == 33333) {
                var careTel = data?.getStringExtra("tel")
                var email = data?.getStringExtra("email")
                var dm = data?.getStringExtra("dm")

                Log.v("연락처 수정", "전화번호:" + careTel + "이메일" + email + "디엠" + dm)
                //연락처 수정
                careTel_rb = RequestBody.create(MediaType.parse("text/plain"), careTel.toString())
                email_rb = RequestBody.create(MediaType.parse("text/plain"), email.toString())
                dm_rb = RequestBody.create(MediaType.parse("text/plain"), dm.toString())

                Log.v("연락처 수정rb", "전화번호:" + careTel_rb + "이메일" + email_rb + "디엠" + dm_rb)
                Toast.makeText(
                    this,
                    "수정되었습니다.",
                    android.widget.Toast.LENGTH_LONG
                ).show()

            }
        }
    }
}