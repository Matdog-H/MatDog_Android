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
import android.media.ExifInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.SparseIntArray
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.main.dog_shelter.Write_Shelter_Activity
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write_miss.*
import kotlinx.android.synthetic.main.activity_write_protect.*
import kotlinx.android.synthetic.main.activity_write_protect.btn_okwrite_protect
import kotlinx.android.synthetic.main.activity_write_protect.ic_back_protect
import kotlinx.android.synthetic.main.activity_write_protect.radionotouch_protect
import kotlinx.android.synthetic.main.activity_write_protect.species_modify_protect
import kotlinx.android.synthetic.main.activity_write_protect.species_name_protect

class Write_Protect_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_protect)

        ic_back_protect.setOnClickListener {// 뒤로가기 버튼 눌렀을 때
            finish()
        }

        btn_okwrite_protect.setOnClickListener {// 등록하기 버튼 눌렀을 때
            finish()
        }

        species_modify_protect.setOnClickListener {// 종 수정버튼 눌렀을 때,
            // 종 수정가능해짐
            species_name_protect.isEnabled = true
        }


        radionotouch_protect.setOnClickListener {// 연락처수정 라디오버튼을 눌렀을 때,
            // 연락처수정할 수 있는 팝업창 띄움
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivity(i)

        }
        picture() // 앨범에서 사진 가져오기
    }

    private fun picture() {
        picture_write1_protect.setOnClickListener{
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
            picture_write1_protect.setImageURI(data?.data)
        }
    }

}