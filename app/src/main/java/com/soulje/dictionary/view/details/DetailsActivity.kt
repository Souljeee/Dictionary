package com.soulje.dictionary.view.details

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import com.soulje.dictionary.R
import com.soulje.dictionary.databinding.ActivityDetailsBinding
import com.soulje.dictionary.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl: String = intent.getStringExtra("IMAGE_URL")!!
        val text: String = intent.getStringExtra("TEXT")!!
        val translation: String = intent.getStringExtra("TRANSLATION")!!

        binding.textDetails.text = text
        binding.translationDetails.text = translation
        useCoilToLoadPhoto(binding.wordImage,imageUrl)
    }

    @RequiresApi(31)
    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(this)
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                },
                onError = {
                    Log.d("tag","image_error")
                }
            )
//            .transformations(
//                CircleCropTransformation(),
//            )
            .build()
        val blurEffect = RenderEffect.createBlurEffect(16f, 16f, Shader.TileMode.MIRROR)
        imageView.setRenderEffect(blurEffect)
        ImageLoader(this).execute(request)
    }
}