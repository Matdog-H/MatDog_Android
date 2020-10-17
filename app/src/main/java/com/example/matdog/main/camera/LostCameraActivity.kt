package com.example.matdog.main.camera

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
import android.provider.MediaStore
import android.util.Log
import android.util.SparseIntArray
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.matdog.R
import com.example.matdog.main.Share_files.List_share.List_Activity
import com.example.matdog.main.camera.image.DogDetector
import com.example.matdog.main.camera.image.DogView
import kotlinx.android.synthetic.main.activity_lost_camera.*
import kotlinx.android.synthetic.main.activity_lost_camera.ic_back
import java.io.InputStream
import java.util.*

class LostCameraActivity : AppCompatActivity(), DogView {

    private lateinit var dogDetector2: DogDetector
    private var breed_data: String = ""

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
        setContentView(R.layout.activity_lost_camera)

        breed_data = ""
        dogDetector2 = DogDetector(this)
        dogDetector2.view = this

        ic_back.setOnClickListener {
            finish()
        }

        finddog.setOnClickListener {
            val i1 = Intent(this, List_Activity::class.java)
            if (breed_data == "") {
                i1.putExtra("state1", "1") // 임시보호 리스트로
            } else {
                i1.putExtra("state4", "4")
                i1.putExtra("breed2", breed_data)
            }

            Log.v("암보 로그값", breed_data)
            startActivity(i1)
        }

        findjuin.setOnClickListener {
            val i2 = Intent(this, List_Activity::class.java)
            if (breed_data == "") {
                i2.putExtra("state2", "2") // 실종 리스트로
            } else {
                i2.putExtra("state5", "5") // 실종 리스트로
                i2.putExtra("breed3", breed_data)
            }
            startActivity(i2)
        }

        button_camera2.setOnClickListener {
            picture_button2.isVisible = false
            imageView_lost.isVisible = true
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).let {
                if (it.resolveActivity(packageManager) != null) {
                    startActivityForResult(it, REQUEST_IMAGE_CAPTURE)
                }
            }
        }

        picture()
    }

    private fun picture() {
        // 앨범(갤러리에서) 사진 가져오기
        get_photo_lost.setOnClickListener {
            picture_button2.isVisible = true
            imageView_lost.isVisible = false
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
            data?.let {
                var selectedPictureUri = it.data
                val options = BitmapFactory.Options()
                val inputStream: InputStream? =
                    contentResolver.openInputStream(selectedPictureUri!!) // !! 강제로 not null로 바꿔줌..
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream, null, options)!!

                dogDetector2.recognizeDog(bitmap = bitmap)

                picture_button2.setImageURI(data?.data)
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (data?.extras?.get("data") as Bitmap).apply {
                Bitmap.createBitmap(this, 0, height / 2 - width / 2, width, width).let {
                    imageView_lost.setImageBitmap(it)
                    imageView_lost.scaleType = ImageView.ScaleType.CENTER_CROP
                    dogDetector2.recognizeDog(bitmap = it)
                }
            }
        }
    }

    private fun enable() {
        finddog.setBackgroundResource(R.drawable.btnpink)
        findjuin.setBackgroundResource(R.drawable.btnpink)
    }

    private fun disenable() {
        finddog.setBackgroundResource(R.drawable.graybtn)
        findjuin.setBackgroundResource(R.drawable.graybtn)
    }
//------------------------------카메라---------------------------------------

    override fun onDestroy() {
        dogDetector2.view = null
        super.onDestroy()
    }

    override fun displayDogBreed(dogBreed: String, winPercent: Float) {
        if (winPercent < 30) {
            disenable()
            textView2.setText("인식 실패하였습니다!")
            breed_data = ""
        } else if (winPercent < 50) {
            breed_data = "(" + dogBreed + ")믹스견"
            textView2.text = String.format(
                Locale.FRANCE, getString(R.string.dog_result), breed_data,
                winPercent
            )
            breed_data = "믹스견"
            enable()
            Log.v("믹스견믹스견믹스견", breed_data)
        } else {
            breed_data = dogBreed
            textView2.text = String.format(
                Locale.FRANCE, getString(R.string.dog_result), breed_data,
                winPercent
            )
            enable()
            Log.v("견견견견견", breed_data)
        }
    }

    override fun displayError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }
}

