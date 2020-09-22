package com.example.matdog.main.dog_miss

/*
* WriteMissActivity.kt
* 유기견 찾기_ 실종 공고등록
* activity_write_miss.xml
 */

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseIntArray
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.api.RegisterResponseMiss
import com.example.matdog.api.SharedPreferenceController
import com.example.matdog.api.UserServiceImpl
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write_miss.*
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


class Write_Miss_Activity : AppCompatActivity() {
    private var token : String = ""
    private val registerStatus: Int = 2 //공고 상태 "실종-miss" 고정
    var dogfile : MultipartBody.Part? = null // dogimg

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_miss)

        picture() // 앨범에서 사진 가져오기

        // --------------- 데이터 저장 --------------------
        // 성별
        var sexCd : String = "F" // 여 기본
        radioGroupgender_miss.setOnCheckedChangeListener { group, checkedId ->
            Log.v("성별라디오 버튼","라디오버튼 선택함")
            if(checkedId == R.id.radioFemale_miss){ // 여 선택
                if(radioFemale_miss.isChecked == true){
                    radioFemale_miss.isChecked = true
                    radioMale_miss.isChecked = false
                    sexCd = "F"
                    Log.v("성별라디오 버튼",sexCd)
                }
            }else if(checkedId == R.id.radioMale_miss) // 남 선택
                if(radioMale_miss.isChecked == true){
                    radioFemale_miss.isChecked = false
                    radioMale_miss.isChecked = true
                    sexCd = "M"
                    Log.v("성별라디오 버튼",sexCd)
                }
        }

        ic_back_miss.setOnClickListener {// 뒤로가기 버튼 눌렀을 때
            finish()
        }

        btn_okwrite_miss.setOnClickListener {// 등록하기 버튼 눌렀을 때
            if(species_name_miss.getText().toString().equals(""))
                Toast.makeText(this, "종을 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edtweight_miss.getText().toString().equals(""))
                Toast.makeText(this, "몸무게를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edtyear_miss.getText().toString().equals(""))
                Toast.makeText(this, "나이를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edtmissplace_miss.getText().toString().equals(""))
                Toast.makeText(this, "잃어버린장소 입력해주세요.", Toast.LENGTH_LONG).show()
            else if(edtmissday_miss.getText().toString().equals(""))
                Toast.makeText(this, "잃어버린날짜을 입력해주세요.", Toast.LENGTH_LONG).show()
            else{
                // ---------- 데이터저장------------
                var kindCd = RequestBody.create(MediaType.parse("text/plain"),species_name_miss.getText().toString()) // 종
                var sexCd_rb = RequestBody.create(MediaType.parse("text/plain"),sexCd)  //성별
                var weight = RequestBody.create(MediaType.parse("text/plain"),edtweight_miss.getText().toString()) // 몸무게
                var age = RequestBody.create(MediaType.parse("text/plain"),edtyear_miss.getText().toString()) // 나이
                var lostPlace = RequestBody.create(MediaType.parse("text/plain"),edtmissplace_miss.getText().toString()) // 실종 장소 = 잃어버린 장소
                var lostDate = RequestBody.create(MediaType.parse("text/plain"),edtmissday_miss.getText().toString()) // 실종 날짜 = 잃어버린 날짜

                // 등록일
                val now = LocalDateTime.now()
                val happenDt = RequestBody.create(
                    MediaType.parse("text/plain"),now.format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")))

                var specialMark = RequestBody.create(MediaType.parse("text/plain"),edtfeature_miss.getText().toString()) //특징

                //연락처 수정
                var careTel : RequestBody? = null
                if(intent.hasExtra("tel")){
                    careTel = RequestBody.create(MediaType.parse("text/plain"),intent.getStringExtra("tel").toString())
                }
                var email : RequestBody? = null
                if(intent.hasExtra("email")){
                    email = RequestBody.create(MediaType.parse("text/plain"),intent.getStringExtra("email").toString())
                }
                var dm : RequestBody? = null
                if(intent.hasExtra("dm")){
                    dm = RequestBody.create(MediaType.parse("text/plain"),intent.getStringExtra("dm").toString())
                }

                // ------------server -------------
                token = SharedPreferenceController.getUserToken(this)
                val callRegisterResponseMiss = UserServiceImpl.AnnouncementRegisterService.announcementRegister_miss(
                    token,
                    registerStatus,
                    kindCd,
                    sexCd_rb,
                    weight,
                    age,
                    happenDt,
                    lostDate,
                    lostPlace,
                    specialMark,
                    careTel,
                    email,
                    dm,
                    dogfile
                )

                callRegisterResponseMiss.enqueue(object : Callback<RegisterResponseMiss> {
                    override fun onFailure(call: Call<RegisterResponseMiss>, t: Throwable) {
                        Log.d("*****Write_Miss_Activity::", t.toString())
                    }

                    override fun onResponse(call: Call<RegisterResponseMiss>, response: Response<RegisterResponseMiss>) {
                        if (response.isSuccessful) {
//                        Log.v("check", callRegisterResponse.safeEnqueue().toString())
                            Log.v("실종공고등록성공", response.body()!!.message)
                            Log.v("공고등록응답확인", response.body()!!.toString())

                            finish()
                        } else {
                            Log.v("실종공고등록(notsucess)", response.body()!!.message)
                            Log.v("공고등록응답확인(notsucess)", response.body()!!.toString())
                        }
                    }
                })
            }
        }

        species_modify_miss.setOnClickListener {// 종 수정버튼 눌렀을 때,
            // 종 수정가능해짐
            species_name_miss.isEnabled = true
        }


        radionotouch_miss.setOnClickListener {// 연락처수정 라디오버튼을 눌렀을 때,
            // 연락처수정할 수 있는 팝업창 띄움
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivity(i)

        }
    }

    private fun picture() {
        // + 버튼 클릭이벤트
        picture_write1_miss.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(
                        permissions,
                        PERMISSION_CODE
                    )
                } else {
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
            ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270)
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
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            data?.let{
                var selectedPictureUri= it.data
                val options = BitmapFactory.Options()
                val inputStream: InputStream? = contentResolver.openInputStream(selectedPictureUri!!) // !! 강제로 not null로 바꿔줌..
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)

                val file2 = File(selectedPictureUri.toString())
                Log.v("강아지사진file2객체",file2.toString())

                // 가져온 File 객체를 RequestBody 객체로 변환
                val photoBody =  RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray()) // null값 나옴..
                Log.v("bytearray확인",byteArrayOutputStream.toByteArray().toString())
                Log.v("강아지사진requestbody객체",photoBody.toString())

                // 우리에게 필요한 Multipart.Part로 변환
                dogfile = MultipartBody.Part.createFormData("dogimg",file2.name+".jpg",photoBody)
                Log.v("강아지사진",dogfile.toString())

                picture_write1_miss.setImageURI(data?.data)
            }
        }
    }

}