package com.example.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import coil.load
import com.example.camera.databinding.ActivityImageListBinding
import com.example.camera.util.PathUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class ImageListActivity : AppCompatActivity() {

    companion object {
        const val URI_LIST_KEY = "uriList"

        fun newIntent(activity: Activity, uriList: List<Uri>) =
            Intent(activity, ImageListActivity::class.java).apply {
                putExtra(URI_LIST_KEY, ArrayList<Uri>().apply { uriList.forEach { add(it) } })
            }
    }

    private lateinit var binding: ActivityImageListBinding
    private val uriList by lazy<List<Uri>?> { intent.getParcelableArrayListExtra(URI_LIST_KEY) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(binding.toolbar)
        initImageViews()
        setupImageList()
    }

    private fun initImageViews() {
        val imageViews = listOf(binding.ivFirst, binding.ivSecond, binding.ivThird, binding.ivFourth)
        uriList?.zip(imageViews) { uri: Uri, imageView: ImageView ->
            imageView.load(uri)
        }
    }

    private fun setupImageList() = with(binding) {
        shareButton.setOnClickListener {
            val capturedBitmap = captureView(binding.clImageContainer)
            ivMain.load(capturedBitmap)
            saveBitmapToFile(capturedBitmap)
        }
    }

    private fun captureView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        // ConstraintLayout의 레이아웃 파라미터 생성
        val layoutParams = ConstraintLayout.LayoutParams(view.width, view.height)
        view.layoutParams = layoutParams

        // ConstraintLayout을 그리는 작업 수행
        view.draw(canvas)
        return bitmap
    }



    private fun saveBitmapToFile(bitmap: Bitmap) {
        // 비트맵을 파일로 저장
        val file = File(
            PathUtil.getOutputDirectory(this),
            SimpleDateFormat(
                MainActivity.FILENAME_FORMAT, Locale.KOREA
            ).format(System.currentTimeMillis()) + ".jpg"
        )
        try {
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()
            // 파일 저장 성공
        } catch (e: IOException) {
            e.printStackTrace()
            // 파일 저장 실패
        }
    }
}
