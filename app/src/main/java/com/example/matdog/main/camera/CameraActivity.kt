package com.example.matdog.main.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.ExifInterface
import android.os.*
import android.provider.MediaStore
import android.util.SparseIntArray
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matdog.R
import com.example.matdog.main.Share_files.List_share.List_Activity
import com.example.matdog.main.camera.image.DogDetector
import com.example.matdog.main.camera.image.DogView
import com.example.matdog.main.dog_shelter.Write_Shelter_Activity
import kotlinx.android.synthetic.main.activity_camera.*
import java.util.*

class CameraActivity : AppCompatActivity() , DogView {

    private lateinit var dogDetector: DogDetector

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
        private const val REQUEST_IMAGE_CAPTURE = 1

        private val ORIENTATIONS = SparseIntArray()

        init {
            ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        dogDetector = DogDetector(this)
        dogDetector.view = this

        init()
        picture()

    }

    private fun init() {

        //뒤로가기
        ic_back.setOnClickListener {
            finish()
        }


        //추천 공고 보기
        btn_camera_list.setOnClickListener {
            val intent1 = Intent(this, List_Activity::class.java)
            intent1.putExtra("state3", "3") //보호소 리스트로
            startActivity(intent1)
        }


        //분양 공고 등록
        btn_camera_register.setOnClickListener {
            val intent2 = Intent(this, Write_Shelter_Activity::class.java)
            startActivity(intent2)
        }

        button_camera.isEnabled = true
        button_camera.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).let {
                if (it.resolveActivity(packageManager) != null) {
                    startActivityForResult(it, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

    }


    private fun picture() {
        // 앨범(갤러리에서) 사진 가져오기
        get_photo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                black_blind.setBackgroundColor(Color.WHITE)
                button_camera.isEnabled = false
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

    //앨범에서 이미지 가져오기
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(
            intent,
            IMAGE_PICK_CODE
        )
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
            picture_button1.setImageURI(data?.data)
        }
        else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (data?.extras?.get("data") as Bitmap).apply {
                Bitmap.createBitmap(this, 0, height / 2 - width / 2, width, width).let {
                    imageView.setImageBitmap(it)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    dogDetector.recognizeDog(bitmap = it)
                }
            }
        }
    }


//------------------------------카메라---------------------------------------


    override fun onDestroy() {
        dogDetector.view = null
        super.onDestroy()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            (data?.extras?.get("data") as Bitmap).apply {
//                Bitmap.createBitmap(this, 0, height / 2 - width / 2, width, width).let {
//                    imageView.setImageBitmap(it)
//                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//                    dogDetector.recognizeDog(bitmap = it)
//                }
//            }
//        }
//    }

    override fun displayDogBreed(dogBreed: String, winPercent: Float) {
        textView.text = String.format(
            Locale.FRANCE, getString(R.string.dog_result), dogBreed,
            winPercent)
    }

    override fun displayError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }


}
