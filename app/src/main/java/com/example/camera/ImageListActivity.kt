package com.example.camera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.load
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.camera.databinding.ActivityImageListBinding

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

        uriList?.zip(imageViews) { uri:Uri, imageView:ImageView ->
            imageView.load(uri)
        }
    }

    private var currentUri: Uri? = null

    private fun setupImageList() = with(binding) {
        shareButton.setOnClickListener {
            currentUri?.let { uri ->

            }
        }
    }
}
