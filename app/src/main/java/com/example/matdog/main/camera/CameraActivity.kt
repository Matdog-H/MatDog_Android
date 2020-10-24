package com.example.matdog.main.camera

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.RectF
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.util.Size
import android.util.SparseIntArray
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.matdog.main.dog_shelter.Write_Shelter_Activity
import kotlinx.android.synthetic.main.activity_camera.*
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
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import kotlin.math.min
import kotlin.random.Random

class CameraActivity : AppCompatActivity() , DogView {

    private lateinit var dogDetector: DogDetector
    private lateinit var breed_data : String
    private lateinit var BITMAP : Bitmap

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val IMG_MINE_TYPE = "image/jpg"

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
                    tfInputSize.height, tfInputSize.width, ResizeOp.ResizeMethod.NEAREST_NEIGHBOR
                )
            )
            .add(Rot90Op(-imageRotationDegrees / 90))
            .add(NormalizeOp(0f, 1f))
            .build()
    }

    private val tflite by lazy {
        Interpreter(
            FileUtil.loadMappedFile(this, MODEL_PATH),
            Interpreter.Options().addDelegate(NnApiDelegate())
        )
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


    //--------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        breed_data= ""
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
            intent1.putExtra("breed1", breed_data)
            Log.v("breed_data 값 확인하기", breed_data)
            startActivity(intent1)
        }

        //분양 공고 등록
        btn_camera_register.setOnClickListener {
            val intent2 = Intent(this, Write_Shelter_Activity::class.java)
            intent2.putExtra("breed1", breed_data)
            intent2.putExtra("image",BITMAP)
            startActivity(intent2)
        }

        camera_capture_button?.setOnClickListener {

            // Disable all camera controls
            it.isEnabled = false

            if (pauseAnalysis) {
                // If image analysis is in paused state, resume it
                // 이미지 분석이 일시 중지 된 상태이면 다시 시작
                pauseAnalysis = false
                image_predicted.visibility = View.GONE
                textView.setText("카메라에 얼굴을 인식해 주세요.")
                disenable()
            } else {
                // Otherwise, pause image analysis and freeze image
                // 그렇지 않으면, 이미지 분석을 중지하고 이미지를 고정
                pauseAnalysis = true
                val matrix = Matrix().apply {
                    postRotate(imageRotationDegrees.toFloat())
                    if (isFrontFacing) postScale(-1f, 1f)
                }
                val uprightImage = Bitmap.createBitmap(
                    bitmapBuffer, 0, 0, bitmapBuffer.width, bitmapBuffer.height, matrix, true
                )
                Log.i("camera_capture_button_bitmapToFile", "비트맵"+uprightImage);
                bitmapToFile(uprightImage)
                Log.i("camera_capture_button_bitmapToFile", "Call");
                galleryAddPic() // 갤러리에 사진저장
                Log.i("camera_capture_button_galleryAddPic", "Call");

                image_predicted.setImageBitmap(uprightImage)
                dogDetector.recognizeDog(bitmap = uprightImage)
                image_predicted.visibility = View.VISIBLE
                BITMAP = uprightImage
            }

            // Re-enable camera controls
            it.isEnabled = true
        }
    }

    private fun enable(){
        btn_camera_list.isEnabled=true
        btn_camera_list.setBackgroundResource(R.drawable.btnpink)
        btn_camera_register.isEnabled=true
        btn_camera_register.setBackgroundResource(R.drawable.btnpink)
    }

    private fun disenable(){
        btn_camera_list.isEnabled=false
        btn_camera_list.setBackgroundResource(R.drawable.graybtn)
        btn_camera_register.isEnabled=false
        btn_camera_register.setBackgroundResource(R.drawable.graybtn)
    }

    private fun picture() {
        // 앨범(갤러리에서) 사진 가져오기
        get_photo.setOnClickListener {
            picture_button1.isVisible=true
            imageView.isVisible=false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //black_blind.setBackgroundColor(Color.WHITE)
                //button_camera.isEnabled = false
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

    private var mCurrentPhotoPath : String? = null //이미지 저장 경로

    // 이미지 갤러리에 저장
    private fun galleryAddPic() {
        Log.i("galleryAddPic", "Call");
        val mediaScanIntent: Intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        // 해당 경로에 있는 파일을 객체화(새로 파일을 만든다는 것으로 이해하면 안 됨)
        val f: File = File(mCurrentPhotoPath);
        val contentUri: Uri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        Log.i("galleryAddPic", "사진앨범저장");
        sendBroadcast(mediaScanIntent);
        Log.i("galleryAddPic", "사진이 앨범에 저장되었습니다.");
        Toast.makeText(this, "사진이 앨범에 저장되었습니다.", Toast.LENGTH_SHORT).show();
    }


    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap:Bitmap): File? {
        Log.i("bitmapToFile", "Call");
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        // 새 파일 인스턴스를 초기화하여 비트 맵 객체 저장
        // 초기루트는 프로젝트 내장메모리로 -> /data/user/0/com.example.matdog/app_Images
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        Log.i("bitmapToFile1111", "filename"+file);

        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_$timeStamp.jpg"

        // 이미지 저장할 경로 생성 -> /storage/emulated/0/Pictures/Matdog/JPEG_20201023_011930.jpg
        var storageDir = File(
            Environment.getExternalStorageDirectory().toString() + "/Pictures",
            "Matdog"
        )
        if (!storageDir.exists()) {
            Log.i("mCurrentPhotoPath", storageDir.toString())
            storageDir.mkdirs()
        }
        file = File(storageDir,imageFileName)
        Log.i("bitmapToFile2222", "filename"+file);
        mCurrentPhotoPath = file.absolutePath

        try{
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            stream.flush()
            stream.close()
            Log.i("bitmap and save in jpg format", "stream"+stream);
        }catch (e:IOException){
            Log.i("bitmap and save in jpg format", "error");
            e.printStackTrace()
        }

        // Return the saved bitmap file
        return file
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

        if (requestCode == permissionsRequestCode && hasPermissions(this)) {
            bindCameraUseCases()
            try{
                galleryAddPic();
            }catch (e: Exception){
                Log.e("permissionsRequestCode", e.toString());
            }
        } else {
            finish() // If we don't have the required permissions, we can't run
        }
    }

    //여기서 강아지 품종 분석 텐서플로우를 연결한다!----------------------------------------------------------------------
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) { //앨범에서 이미지 가져옴
            data?.let {
                var selectedPictureUri = it.data
                val options = BitmapFactory.Options()
                val inputStream: InputStream? =
                    contentResolver.openInputStream(selectedPictureUri!!) // !! 강제로 not null로 바꿔줌..
                val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream, null, options)!!
                BITMAP = bitmap
                dogDetector.recognizeDog(bitmap = bitmap)

                picture_button1.setImageURI(data?.data)
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            (data?.extras?.get("data") as Bitmap).apply {
                Bitmap.createBitmap(this, 0, height / 2 - width / 2, width, width).let {
//                    bitmapToFile(it)
//                    Log.i("onActivityResult_bitmapToFile", "Call");
//                    galleryAddPic() // 갤러리에 사진저장
//                    Log.i("onActivityResult_galleryAddPic", "Call");
                    imageView.setImageBitmap(it)
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    dogDetector.recognizeDog(bitmap = it)
                    BITMAP = it
                }
            }
        }
    }

//------------------------------강아지 품종 분석---------------------------------------

    override fun onDestroy() {
        dogDetector.view = null
        super.onDestroy()
    }

    override fun displayDogBreed(dogBreed: String, winPercent: Float) {
        if(winPercent<30) {
            disenable()
            textView.setText("인식 실패하였습니다!")
            breed_data=""
        }else if(winPercent< 50){
            breed_data = "("+dogBreed+")믹스견"
            textView.text = String.format(
                Locale.FRANCE, getString(R.string.dog_result), breed_data, winPercent
            )
            breed_data = "믹스견"
            enable()
        }else{
            breed_data = dogBreed
            textView.text = String.format(
                Locale.FRANCE, getString(R.string.dog_result), breed_data,
                winPercent
            )
            enable()
        }
    }

    override fun displayError() {
        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show()
    }


    //---------------------------------------
    /** Declare and bind preview and analysis use cases */
    //미리보기 및 분석 사용 사례 선언 및 바인딩
    @SuppressLint("UnsafeExperimentalUsageError")
    private fun bindCameraUseCases() = view_finder.post {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {

            // Camera provider is now guaranteed to be available
            val cameraProvider = cameraProviderFuture.get()

            // Set up the view finder use case to display camera preview
            // 카메라 미리보기를 표시하도록 뷰 파인더 사용 사례 설정
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(view_finder.display.rotation)
                .build()

            // Set up the image analysis use case which will process frames in real time
            // 실시간으로 프레임을 처리 할 이미지 분석 사용 사례 설정
            val imageAnalysis = ImageAnalysis.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .setTargetRotation(view_finder.display.rotation)
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
                        image.width, image.height, Bitmap.Config.ARGB_8888
                    )
                }

                // Early exit: image analysis is in paused state
                if (pauseAnalysis) {
                    image.close()
                    return@Analyzer
                }

                // Convert the image to RGB and place it in our shared buffer
                image.use { converter.yuvToRgb(image.image!!, bitmapBuffer) }

                // Process the image in Tensorflow
                val tfImage = tfImageProcessor.process(tfImageBuffer.apply { load(bitmapBuffer) })

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
                this as LifecycleOwner, cameraSelector, preview, imageAnalysis
            )

            // Use the camera object to link our preview use case with the view
            preview.setSurfaceProvider(view_finder.createSurfaceProvider())

        }, ContextCompat.getMainExecutor(this))
    }

    private fun reportPrediction(
        prediction: ObjectDetectionHelper.ObjectPrediction?
    ) = view_finder.post {

        // Early exit: if prediction is not good enough, don't report it
        // 조기 종료 : 예측이 충분하지 않으면 보고하지 마십시오.
        if (prediction == null || prediction.score < ACCURACY_THRESHOLD) {
            box_prediction.visibility = View.GONE
            //text_prediction.visibility = View.GONE
            return@post
        }

        // Location has to be mapped to our local coordinates
        // 위치는 로컬 좌표에 매핑되어야합니다.
        val location = mapOutputCoordinates(prediction.location)

        // Update the text and UI
        //text_prediction.text = "${"%.2f".format(prediction.score)} ${prediction.label}"
        (box_prediction.layoutParams as ViewGroup.MarginLayoutParams).apply {
            topMargin = location.top.toInt()
            leftMargin = location.left.toInt()
            width = min(view_finder.width, location.right.toInt() - location.left.toInt())
            height = min(view_finder.height, location.bottom.toInt() - location.top.toInt())
        }

        // Make sure all UI elements are visible
        box_prediction.visibility = View.VISIBLE
        //text_prediction.visibility = View.VISIBLE
    }

    /**
     * Helper function used to map the coordinates for objects coming out of
     * the model into the coordinates that the user sees on the screen.
     *
     * 밖으로 나오는 물체의 좌표를 매핑하는 데 사용되는 도우미 기능
     * 모델을 사용자가 화면에서 보는 좌표로 입력합니다.
     */
    private fun mapOutputCoordinates(location: RectF): RectF {

        // Step 1: map location to the preview coordinates
        // 미리보기 좌표에 위치 매핑
        val previewLocation = RectF(
            location.left * view_finder.width,
            location.top * view_finder.height,
            location.right * view_finder.width,
            location.bottom * view_finder.height
        )

        // Step 2: compensate for camera sensor orientation and mirroring
        // 카메라 센서 방향 및 미러링 보정
        val isFrontFacing = lensFacing == CameraSelector.LENS_FACING_FRONT
        val correctedLocation = if (isFrontFacing) {
            RectF(
                view_finder.width - previewLocation.right,
                previewLocation.top,
                view_finder.width - previewLocation.left,
                previewLocation.bottom
            )
        } else {
            previewLocation
        }

        // Step 3: compensate for 1:1 to 4:3 aspect ratio conversion + small margin
        //1 : 1 ~ 4 : 3 종횡비 변환 + 작은 여백 보정
        val margin = 0.1f
        val requestedRatio = 4f / 3f
        val midX = (correctedLocation.left + correctedLocation.right) / 2f
        val midY = (correctedLocation.top + correctedLocation.bottom) / 2f
        return if (view_finder.width < view_finder.height) {
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
        // 언제든지 취소 할 수 있으므로 앱이 다시 시작될 때마다 권한을 요청하세요.
        if (!hasPermissions(this)) {
            ActivityCompat.requestPermissions(
                this, permissions.toTypedArray(), permissionsRequestCode
            )
        } else {
            bindCameraUseCases()
        }
    }

    /** Convenience method used to check if all permissions required by this app are granted
     *이 앱에 필요한 모든 권한이 부여되었는지 확인하는 데 사용되는 편리한 방법*/
    private fun hasPermissions(context: Context) = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }


}
