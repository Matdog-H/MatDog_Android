package com.example.matdog.main


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.matdog.R
import com.example.matdog.main.Share_files.List_share.List_Activity
import com.example.matdog.main.dog_shelter.Write_Shelter_Activity
import com.example.matdog.sign.SignUpActivity
import kotlinx.android.synthetic.main.activity_camera.*

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        init()
        picture()
    }

    private fun init(){

        //뒤로가기
        ic_back.setOnClickListener{
            finish()
        }


        //추천 공고 보기
        btn_camera_list.setOnClickListener {
            val intent1 = Intent(this, List_Activity::class.java)
            intent1.putExtra("state0","0") //상태값=0
            startActivity(intent1)
        }


        //분양 공고 등록
        btn_camera_register.setOnClickListener {
            val intent2 = Intent(this,Write_Shelter_Activity::class.java)
            startActivity(intent2)
        }



    }


    private fun picture() {
        //Change profile Image
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

    //Change profile image
    private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,
            IMAGE_PICK_CODE
        )
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
            picture_button1.setImageURI(data?.data)
        }
    }
}
