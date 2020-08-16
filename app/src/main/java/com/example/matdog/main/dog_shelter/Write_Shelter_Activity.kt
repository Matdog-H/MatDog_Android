package com.example.matdog.main.dog_shelter

/*
* WriteActivity.kt
* 분양 공고 등록
* activity_write.xml
 */

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.main.CameraActivity
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write.ic_back

class Write_Shelter_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        ic_back.setOnClickListener { // 뒤로가기 버튼 눌렀을 때
            finish()
        }

        btn_okwrite.setOnClickListener { // 등록하기 버튼 눌렀을 때
            finish()
        }

        species_modify.setOnClickListener { // 종 수정버튼 눌렀을 때,
            // 종 수정가능해짐
            species_name.isEnabled = true
        }

        radionotouch.setOnClickListener { // 연락처수정 라디오버튼을 눌렀을 때,
            // 연락처수정할 수 있는 팝업창 띄움
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivity(i)

        }

        picture()

    }

    private fun picture() {
        // 앨범(갤러리에서) 사진 가져오기
        get_photo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {

                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,
                        PERMISSION_CODE
                    )
                } else {
                    pickImageFromGallery()
                }
            }
        }
    }

    // 앨범에서 이미지 가져오기
    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true) // 여러장 선택가능
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object{
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if(data == null){

            }else{

            }

            picture_button1.setImageURI(data?.data)
        }
    }
}