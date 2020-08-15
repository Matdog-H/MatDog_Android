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
import com.example.matdog.main.pop_up.Renew_popupActivity
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.activity_write.ic_back

class Write_Shelter_Activity : AppCompatActivity() {

    final val PICTURE_REQUEST_CODE = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        ic_back.setOnClickListener {
            finish()
        }

        btn_okwrite.setOnClickListener {
            finish()
        }

        species_modify.setOnClickListener {
            species_name.isEnabled = true // 종 수정하기
        }

        picture()


        radionotouch.setOnClickListener {
            val i = Intent(this, Renew_popupActivity::class.java)
            startActivity(i)

//            val intent = Intent(this@ListActivity, WriteActivity::class.java)
//            startActivity(intent)
        }
    }

    private fun picture() {
        //Change profile Image
        picture_write1_plus.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI)
            // 사진 여러개 선택
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            intent.setType("Image/*")
            startActivityForResult(Intent.createChooser(intent,"Select picture"),PICTURE_REQUEST_CODE) // 액티비티의 결과를 받기위한..
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode == PICTURE_REQUEST_CODE ){
          if(resultCode == Activity.RESULT_OK){
              // 기존 이미지지우기
              picture_write1.setImageResource(0)
              picture_write2.setImageResource(0)
              picture_write3.setImageResource(0)

              // ClipData 또는 URI를 가져옴
              var currentImageUri : Uri? = data?.data

              try{
                  for(i in 0..2){
                      when(i){
                          0 -> picture_write1.setImageURI(currentImageUri)
                          1 -> picture_write2.setImageURI(currentImageUri)
                          2 -> picture_write3.setImageURI(currentImageUri)
                      }
                  }
              }catch(e:Exception){
                  e.printStackTrace()
              }

          }
        }
    }
}