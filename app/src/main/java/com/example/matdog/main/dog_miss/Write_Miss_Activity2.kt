package com.example.matdog.main.dog_miss

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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

class Write_Miss_Activity2 :AppCompatActivity(){
    private var token: String = ""
    private val registerStatus: Int = 1 //공고 상태 "보호소-shelter" 고정
    var dogfile: MultipartBody.Part? = null // dogimg

    // 연락처 수정 팝업
    var careTel_rb: RequestBody? = null//전화번호
    var email_rb: RequestBody? = null
    var dm_rb: RequestBody? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_miss)
    }
}