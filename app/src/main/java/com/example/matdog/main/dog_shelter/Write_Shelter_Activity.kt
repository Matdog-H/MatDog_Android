package com.example.matdog.main.dog_shelter

/*
* WriteActivity.kt
* 분양 공고 등록
* activity_write.xml
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
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.SparseIntArray
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.api.*
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write.btn_okwrite
import kotlinx.android.synthetic.main.activity_write.ic_back
import kotlinx.android.synthetic.main.activity_write.radionotouch
import kotlinx.android.synthetic.main.activity_write.species_modify
import kotlinx.android.synthetic.main.activity_write.species_name
import kotlinx.android.synthetic.main.activity_write_miss.*
import kotlinx.android.synthetic.main.activity_write_protect.*
import retrofit2.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Write_Shelter_Activity : AppCompatActivity() {
    private var token: String = ""
    private val registerStatus: Int = 1 //공고 상태 "보호소-shelter" 고정
    var dogfile: MultipartBody.Part? = null // dogimg

    // 연락처 수정 팝업
    var careTel_rb: RequestBody? = null//전화번호
    var email_rb: RequestBody? = null
    var dm_rb: RequestBody? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        var dog_breed = intent.getStringExtra("breed1") // 보호소 리스트 - 분석결과값
        var dog_breed2 = intent.getStringExtra("breed")
        var BITMAP:Bitmap? = intent.getParcelableExtra("image")

        if (dog_breed != "")
            species_name.setText(dog_breed)
        else if(dog_breed2 !="")
            species_name.setText(dog_breed2)
        else
            species_name.setText("")
        if(BITMAP != null) picture_write1.setImageBitmap(BITMAP)

        picture() // 앨범에서 사진 가져오기

        // --------------- 데이터 저장 --------------------
        // 성별
        var sexCd: String = "F" // 여 기본
        radioGroupgender.setOnCheckedChangeListener { group, checkedId ->
            Log.v("성별라디오 버튼", "라디오버튼 선택함")
            if (checkedId == R.id.radioFemale) { // 여 선택
                if (radioFemale.isChecked == true) {
                    radioFemale.isChecked = true
                    radioMale.isChecked = false
                    sexCd = "F"
                    Log.v("성별라디오 버튼", sexCd)
                }
            } else if (checkedId == R.id.radioMale) // 남 선택
                if (radioMale.isChecked == true) {
                    radioFemale.isChecked = false
                    radioMale.isChecked = true
                    sexCd = "M"
                    Log.v("성별라디오 버튼", sexCd)
                }
        }

        // 중성화 여부
        var neuterYn: String = "Y" //Y기본
        radioGroupNeutrali.setOnCheckedChangeListener { group, checkedId ->
            Log.v("중성화라디오 버튼", "라디오버튼 선택함")
            if (checkedId == R.id.radioYes) { // Y 선택
                if (radioYes.isChecked == true) {
                    radioYes.isChecked = true
                    radioNo.isChecked = false
                    radioNon.isChecked = false
                    neuterYn = "Y"
                    Log.v("중성화라디오 버튼", neuterYn)
                }
            } else if (checkedId == R.id.radioNo) { // N 선택
                if (radioNo.isChecked == true) {
                    radioYes.isChecked = false
                    radioNo.isChecked = true
                    radioNon.isChecked = false
                    neuterYn = "N"
                    Log.v("중성화라디오 버튼", neuterYn)
                }
            } else if (checkedId == R.id.radioNon) { // 모름 선택
                if (radioNon.isChecked == true) {
                    radioYes.isChecked = false
                    radioNo.isChecked = false
                    radioNon.isChecked = true
                    neuterYn = "U"
                    Log.v("중성화라디오 버튼", neuterYn)
                }
            }
        }

        ic_back.setOnClickListener { // 뒤로가기 버튼 눌렀을 때
            finish()
        }

        btn_okwrite.setOnClickListener { // 등록하기 버튼 눌렀을 때

            if (species_name.getText().toString().equals(""))
                Toast.makeText(this, "종을 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtweight.getText().toString().equals(""))
                Toast.makeText(this, "몸무게를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtyear.getText().toString().equals(""))
                Toast.makeText(this, "나이를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtplace.getText().toString().equals(""))
                Toast.makeText(this, "보호장소를 입력해주세요.", Toast.LENGTH_LONG).show()
            else if (edtShlter.getText().toString().equals(""))
                Toast.makeText(this, "관할기관을 입력해주세요.", Toast.LENGTH_LONG).show()
            else {
                // ---------- 데이터저장------------
                var kindCd = RequestBody.create(
                    MediaType.parse("text/plain"),
                    species_name.getText().toString()
                ) // 종
                var sexCd_rb = RequestBody.create(MediaType.parse("text/plain"), sexCd)  //성별
                var neuterYn_rb =
                    RequestBody.create(MediaType.parse("text/plain"), neuterYn) //중성화여부
                var weight = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtweight.getText().toString()
                ) // 몸무게
                var age = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtyear.getText().toString()
                ) // 나이
                var orgNm = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtShlter.getText().toString()
                ) // 관할기관
                var careAddr = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtplace.getText().toString()
                ) // 보호장소

                // 등록일
                val now = LocalDateTime.now()
                val happenDt = RequestBody.create(
                    MediaType.parse("text/plain"),
                    now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                )

                var specialMark = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtfeature.getText().toString()
                ) //특징


                // ------------server -------------
                token = SharedPreferenceController.getUserToken(this)
                val callRegisterResponse =
                    UserServiceImpl.AnnouncementRegisterService.announcementRegister(
                        token,
                        registerStatus,
                        kindCd,
                        sexCd_rb,
                        neuterYn_rb,
                        weight,
                        age,
                        orgNm,
                        careAddr,
                        happenDt,
                        specialMark,
                        careTel_rb,
                        email_rb,
                        dm_rb,
                        dogfile
                    )

                callRegisterResponse.enqueue(object : Callback<RegisterResponse> {
                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Log.d("*****Write_Shelter_Activity::", t.toString())
                    }

                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if (response.isSuccessful) {
//                        Log.v("check", callRegisterResponse.safeEnqueue().toString())
                            Log.v("보호소공고등록성공", response.body()!!.message)
                            Log.v("공고등록응답확인", response.body()!!.toString())
                            finish()
                            Toast.makeText(
                                this@Write_Shelter_Activity,
                                "등록되었습니다.",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Log.v("보호소공고등록(notsucess)", response.body()!!.message)
                            Log.v("공고등록응답확인(notsucess)", response.body()!!.toString())
                        }
                    }
                })

            }
        }

        species_modify.setOnClickListener { // 종 수정버튼 눌렀을 때,
            // 종 수정가능해짐
            species_name.isEnabled = true
            species_name.setTextColor(Color.parseColor("#fb777a"))
        }
        radioretouch.setOnClickListener { // "이전 연락처 그대로" 라디오버튼 눌렀을때,
            if (radioretouch.isChecked == true) {
                radioretouch.isChecked = true
                radionotouch.isChecked = false
            }
            // 연락처 다시 null값으로 초기화
            careTel_rb = null//전화번호
            email_rb = null
            dm_rb = null
        }
        radionotouch.setOnClickListener { // 연락처수정 라디오버튼을 눌렀을 때,
            if (radionotouch.isChecked == true) {
                radionotouch.isChecked = true
                radioretouch.isChecked = false
            }
            // 연락처수정할 수 있는 팝업창 띄움
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivityForResult(i, 11111)

        }

    }

    private fun picture() {
        // +버튼 ->이미지 클릭이벤트.
        picture_write1.setOnClickListener {
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
                } else { // 권한 있을 때
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
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            data?.let {
                var selectedPictureUri = it.data
                val options = BitmapFactory.Options()
                val inputStream: InputStream? =
                    contentResolver.openInputStream(selectedPictureUri!!) // !! 강제로 not null로 바꿔줌..
                val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)

                //val file = seletedPictureUri?.toFile() // android 용 kotlin 확장을 사용한 경우
//                val file = File(selectedPictureUri?.path)
                val file2 = File(selectedPictureUri.toString())
//                Log.v("강아지사진file객체",file.toString())
                Log.v("강아지사진file2객체", file2.toString())

                // 가져온 File 객체를 RequestBody 객체로 변환
                //val photoBody =  RequestBody.create(MediaType.parse("image/jpg"), file2)
                val photoBody = RequestBody.create(
                    MediaType.parse("image/jpg"),
                    byteArrayOutputStream.toByteArray()
                ) // null값 나옴..
                Log.v("bytearray확인", byteArrayOutputStream.toByteArray().toString())
                Log.v("강아지사진requestbody객체", photoBody.toString())

                // 우리에게 필요한 Multipart.Part로 변환
                //dogfile = MultipartBody.Part.createFormData("dogimg",file?.name,photoBody)
                dogfile =
                    MultipartBody.Part.createFormData("dogimg", file2.name + ".jpg", photoBody)
                Log.v("강아지사진", dogfile.toString())
                // Glide을 사진 URI를 ImageView에 넣은 방식. 외부 uri가 아니라 굳이 이렇게 안넣어도됨..
//               Glide.with(this).load(seletedPictureUri).thumbnail(0.1f)
//                   .into(picture_write1)
                picture_write1.setImageURI(data?.data)

            }
        } else if (resultCode == 12345) {
            if (requestCode == 11111) {
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