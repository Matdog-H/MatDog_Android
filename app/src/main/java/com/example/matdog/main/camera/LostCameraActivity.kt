package com.example.matdog.main.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import android.media.ExifInterface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import com.example.matdog.R
import com.example.matdog.main.Share_files.List_share.List_Activity
import com.example.matdog.main.camera.image.DogDetector
import com.example.matdog.main.camera.image.DogView
import com.example.matdog.main.camera.image.ObjectDetectionHelper
import com.example.matdog.main.camera.image.YuvToRgbConverter
import kotlinx.android.synthetic.main.activity_lost_camera.*
import kotlinx.android.synthetic.main.activity_lost_camera.ic_back
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.nnapi.NnApiDelegate
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp
import org.tensorflow.lite.support.image.ops.Rot90Op
import java.io.InputStream
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.random.Random

class LostCameraActivity : AppCompatActivity(), DogView {

    private lateinit var dogDetector2: DogDetector
    private var breed_data: String = ""

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
        private const val REQUEST_IMAGE_CAPTURE = 1

        private val ORIENTATIONS = SparseIntArray()

        private val TAG = CameraActivity::class.java.simpleName

        private const val ACCURACY_THRESHOLD = 0.5f
        private const val MODEL_PATH = "coco_ssd_mobilenet_v1_1.0_quant.tflite"
        private const val LABELS_PATH = "coco_ssd_mobilenet_v1_1.0_labels.txt"

        init {
            ORIENTATIONS.append(ExifInterface.ORIENTATION_NORMAL, 0)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_90, 90)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_180, 180)
            ORIENTATIONS.append(ExifInterface.ORIENTATION_ROTATE_270, 270)
        }
    }

    //---------------------------
    private lateinit var bitmapBuffer: Bitmap

    private val executor = Executors.newSingleThreadExecutor()
    private val permissions = listOf(Manifest.permission.CAMERA)
    private val permissionsRequestCode = Random.nextInt(0, 10000)

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private val isFrontFacing get() = lensFacing == CameraSelector.LENS_FACING_FRONT

    private var pauseAnalysis = false
    private var imageRotationDegrees: Int = 0
    private val tfImageBuffer = TensorImage(DataType.UINT8)

    private val tfImageProcessor by lazy {
        val cropSize = minOf(bitmapBuffer.width, bitmapBuffer.height)
        ImageProcessor.Builder()
            .add(ResizeWithCropOrPadOp(cropSize, cropSize))
            .add(
                ResizeOp(
                    tfInputSize.height, tfInputSize.width, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR)
            )
            .add(Rot90Op(-imageRotationDegrees / 90))
            .add(NormalizeOp(0f, 1f))
            .build()
    }

    private val tflite by lazy {
        Interpreter(
            FileUtil.loadMappedFile(this, MODEL_PATH),
            Interpreter.Options().addDelegate(NnApiDelegate()))
    }

    private val detector by lazy {
        ObjectDetectionHelper(
            tflite,
            FileUtil.loadLabels(this, LABELS_PATH)
        )
    }

    private val tfInputSize by lazy {
        val inputIndex = 0
        val inputShape = tflite.getInputTensor(inputIndex).shape()
        Size(inputShape[2], inputShape[1]) // Order of axis is: {1, height, width, 3}
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

            // Disable all camera controls
            it.isEnabled = false

            if (pauseAnalysis) {
                // If image analysis is in paused state, resume it
                pauseAnalysis = false
                image_predicted_lost.visibility = View.GONE
                textView2.setText("카메라에 얼굴을 인식해 주세요.")

                disenable()
                breed_data=""
            } else {
                // Otherwise, pause image analysis and freeze image
                pauseAnalysis = true
                val matrix = Matrix().apply {
                    postRotate(imageRotationDegrees.toFloat())
                    if (isFrontFacing) postScale(-1f, 1f)
                }
                val uprightImage = Bitmap.createBitmap(
                    bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height, matrix, true)
                image_predicted_lost.setImageBitmap(uprightImage)
                dogDetector2.recognizeDog(bitmap = uprightImage)
                image_predicted_lost.visibility = View.VISIBLE
            }

            // Re-enable camera controls
            it.isEnabled = true
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

//------------------------------강아지품종분---------------------------------------

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

    //---------------------------------------
    /** Declare and bind preview and analysis use cases */
    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindCameraUseCases() = view_finder_lost.post {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {

            // Camera provider is now guaranteed to be available
            val cameraProvider = cameraProviderFuture.get()

            // Set up the view finder use case to display camera preview
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(view_finder_lost.display.rotation)
                .build()

            // Set up the image analysis use case which will process frames in real time
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(view_finder_lost.display.rotation)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            var frameCounter = 0
            var lastFpsTimestamp = System.currentTimeMillis()
            val converter = YuvToRgbConverter(this)

            imageAnalysis.setAnalyzer(executor, ImageAnalysis.Analyzer { image ->
                if (!::bitmapBuffer.isInitialized) {
                    // The image rotation and RGB image buffer are initialized only once
                    // the analyzer has started running
                    imageRotationDegrees = image.imageInfo.rotationDegrees
                    bitmapBuffer = Bitmap.createBitmap(
                        image.width, image.height, Bitmap.Config.ARGB_8888)
                }

                // Early exit: image analysis is in paused state
                if (pauseAnalysis) {
                    image.close()
                    return@Analyzer
                }

                // Convert the image to RGB and place it in our shared buffer
                image.use { converter.yuvToRgb(image.image!!, bitmapBuffer) }

                // Process the image in Tensorflow
                val tfImage =  tfImageProcessor.process(tfImageBuffer.apply { load(bitmapBuffer) })

                // Perform the object detection for the current frame
                val predictions = detector.predict(tfImage)

                // Report only the top prediction
                reportPrediction(predictions.maxBy { it.score })

                // Compute the FPS of the entire pipeline
                val frameCount = 10
                if (++frameCounter % frameCount == 0) {
                    frameCounter = 0
                    val now = System.currentTimeMillis()
                    val delta = now - lastFpsTimestamp
                    val fps = 1000 * frameCount.toFloat() / delta
                    Log.d(TAG, "FPS: ${"%.02f".format(fps)}")
                    lastFpsTimestamp = now
                }
            })

            // Create a new camera selector each time, enforcing lens facing
            val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

            // Apply declared configs to CameraX using the same lifecycle owner
            cameraProvider.unbindAll()
            val camera = cameraProvider.bindToLifecycle(
                this as LifecycleOwner, cameraSelector, preview, imageAnalysis)

            // Use the camera object to link our preview use case with the view
            preview.setSurfaceProvider(view_finder_lost.createSurfaceProvider())

        }, ContextCompat.getMainExecutor(this))
    }

    private fun reportPrediction(
        prediction: ObjectDetectionHelper.ObjectPrediction?
    ) = view_finder_lost.post {

        // Early exit: if prediction is not good enough, don't report it
        if (prediction == null || prediction.score < ACCURACY_THRESHOLD) {
            box_prediction_lost.visibility = View.GONE
//            text_prediction_lost.visibility = View.GONE
            return@post
        }

        // Location has to be mapped to our local coordinates
        val location = mapOutputCoordinates(prediction.location)

        // Update the text and UI
//        text_prediction_lost.text = "${"%.2f".format(prediction.score)} ${prediction.label}"
        (box_prediction_lost.layoutParams as ViewGroup.MarginLayoutParams).apply {
            topMargin = location.top.toInt()
            leftMargin = location.left.toInt()
            width = min(view_finder_lost.width, location.right.toInt() - location.left.toInt())
            height = min(view_finder_lost.height, location.bottom.toInt() - location.top.toInt())
        }

        // Make sure all UI elements are visible
        box_prediction_lost.visibility = View.VISIBLE
//        text_prediction_lost.visibility = View.VISIBLE
    }

    /**
     * Helper function used to map the coordinates for objects coming out of
     * the model into the coordinates that the user sees on the screen.
     */
    private fun mapOutputCoordinates(location: RectF): RectF {

        // Step 1: map location to the preview coordinates
        val previewLocation = RectF(
            location.left * view_finder_lost.width,
            location.top * view_finder_lost.height,
            location.right * view_finder_lost.width,
            location.bottom * view_finder_lost.height
        )

        // Step 2: compensate for camera sensor orientation and mirroring
        val isFrontFacing = lensFacing == CameraSelector.LENS_FACING_FRONT
        val correctedLocation = if (isFrontFacing) {
            RectF(
                view_finder_lost.width - previewLocation.right,
                previewLocation.top,
                view_finder_lost.width - previewLocation.left,
                previewLocation.bottom)
        } else {
            previewLocation
        }

        // Step 3: compensate for 1:1 to 4:3 aspect ratio conversion + small margin
        val margin = 0.1f
        val requestedRatio = 4f / 3f
        val midX = (correctedLocation.left + correctedLocation.right) / 2f
        val midY = (correctedLocation.top + correctedLocation.bottom) / 2f
        return if (view_finder_lost.width < view_finder_lost.height) {
            RectF(
                midX - (1f + margin) * requestedRatio * correctedLocation.width() / 2f,
                midY - (1f - margin) * correctedLocation.height() / 2f,
                midX + (1f + margin) * requestedRatio * correctedLocation.width() / 2f,
                midY + (1f - margin) * correctedLocation.height() / 2f
            )
        } else {
            RectF(
                midX - (1f - margin) * correctedLocation.width() / 2f,
                midY - (1f + margin) * requestedRatio * correctedLocation.height() / 2f,
                midX + (1f - margin) * correctedLocation.width() / 2f,
                midY + (1f + margin) * requestedRatio * correctedLocation.height() / 2f
            )
        }
    }

    override fun onResume() {
        super.onResume()

        // Request permissions each time the app resumes, since they can be revoked at any time
        if (!hasPermissions(this)) {
            ActivityCompat.requestPermissions(
                this, permissions.toTypedArray(), permissionsRequestCode)
        } else {
            bindCameraUseCases()
        }
    }

    /** Convenience method used to check if all permissions required by this app are granted */
    private fun hasPermissions(context: Context) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


}


