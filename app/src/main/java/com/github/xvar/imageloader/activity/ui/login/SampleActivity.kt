package com.github.xvar.imageloader.activity.ui.login

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import coil.imageLoader
import com.allgoritm.youla.image_loader.api.ImageLoader
import com.allgoritm.youla.image_loader.coil_impl.CoilImageLoaderImpl
import com.allgoritm.youla.image_loader.glide_impl.GlideImageLoaderImpl
import com.allgoritm.youla.image_loader.impl.ImageLoaderImpl
import com.bumptech.glide.Glide
import com.github.xvar.imageloader.databinding.SampleActivityBinding
import com.squareup.picasso.Picasso

class SampleActivity : AppCompatActivity() {

    private lateinit var binding: SampleActivityBinding

    private lateinit var imLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //imLoader = ImageLoaderImpl(Picasso.with(this.applicationContext))
        //imLoader = GlideImageLoaderImpl(Glide.get(this.applicationContext))
        imLoader = CoilImageLoaderImpl(this.applicationContext.imageLoader)


        binding = SampleActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = "https://image.shutterstock.com/image-photo/cat-medical-mask-protective-antiviral-260nw-1684423789.jpg"
        imLoader.load(url, binding.ivSample) {
            placeholder(ColorDrawable(Color.GREEN))
            fit()
            centerCrop()
            onError { Log.e("###error", "some error loading") }
        }
    }
}
