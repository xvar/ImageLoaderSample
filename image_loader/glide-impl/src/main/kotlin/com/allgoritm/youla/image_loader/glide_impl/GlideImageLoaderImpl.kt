package com.allgoritm.youla.image_loader.glide_impl

import android.net.Uri
import android.widget.ImageView
import com.allgoritm.youla.image_loader.api.Cancelable
import com.allgoritm.youla.image_loader.api.ImageLoader
import com.allgoritm.youla.image_loader.api.builder.ImageViewBuilder
import com.allgoritm.youla.image_loader.api.builder.TargetBuilder
import com.allgoritm.youla.image_loader.api.builder.UrlResizeImageViewBuilder
import com.allgoritm.youla.image_loader.impl.Error
import com.allgoritm.youla.image_loader.impl.builder.ImageViewBuilderImpl
import com.allgoritm.youla.image_loader.impl.builder.UrlResizeImageViewBuilderImpl
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.io.File

class GlideImageLoaderImpl(
    private val glide: Glide
) : ImageLoader {

    override fun load(file: File, build: TargetBuilder.() -> Unit): Cancelable {
        TODO("Not yet implemented")
    }

    override fun load(
        file: File,
        imageView: ImageView,
        build: ImageViewBuilder.() -> Unit
    ): Cancelable {
        return glide.requestManagerRetriever[imageView].load(file)
            .loadForImageView(imageView, build)
    }

    override fun load(drawableId: Int, build: TargetBuilder.() -> Unit): Cancelable {
        TODO("Not yet implemented")
    }

    override fun load(
        drawableId: Int,
        imageView: ImageView,
        build: ImageViewBuilder.() -> Unit
    ): Cancelable {
        TODO("Not yet implemented")
    }

    override fun load(url: String?, build: TargetBuilder.() -> Unit): Cancelable {
        TODO("Not yet implemented")
    }

    override fun load(
        url: String?,
        imageView: ImageView,
        build: UrlResizeImageViewBuilder.() -> Unit
    ): Cancelable {
        return glide.requestManagerRetriever[imageView].load(url)
            .loadResizeForImageView(imageView, build)
    }

    override fun load(uri: Uri?, build: TargetBuilder.() -> Unit): Cancelable {
        TODO("Not yet implemented")
    }

    override fun load(
        uri: Uri?,
        imageView: ImageView,
        build: ImageViewBuilder.() -> Unit
    ): Cancelable {
        TODO("Not yet implemented")
    }
}

private fun <TranscodeType> RequestBuilder<TranscodeType>.loadResizeForImageView(
    imageView: ImageView,
    build: UrlResizeImageViewBuilder.() -> Unit
): Cancelable {
    val imageViewBuilder = UrlResizeImageViewBuilderImpl()
    imageViewBuilder.build()
    return loadForImageView(imageView, imageViewBuilder)
}

private fun <TranscodeType> RequestBuilder<TranscodeType>.loadForImageView(
    imageView: ImageView,
    build: ImageViewBuilder.() -> Unit
): Cancelable {
    val imageViewBuilder = ImageViewBuilderImpl()
    imageViewBuilder.build()
    return loadForImageView(imageView, imageViewBuilder)
}

private fun <TranscodeType> RequestBuilder<TranscodeType>.loadForImageView(
    imageView: ImageView,
    builder: ImageViewBuilderImpl
): Cancelable {
    val option = when (val error = builder.error) {
        is Error.Drawable -> error(error.drawable)
        is Error.Resource -> error(error.drawableId)
        else -> null
    }
    option?.let { this.apply(it) }
    val listener = object : RequestListener<TranscodeType> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<TranscodeType>?,
            isFirstResource: Boolean
        ): Boolean {
            builder.onError?.invoke() //e
            return false
        }

        override fun onResourceReady(
            resource: TranscodeType,
            model: Any?,
            target: Target<TranscodeType>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            builder.onSuccess?.invoke()
            return false
        }
    }
    val into = this.addListener(listener)
        .into(imageView)
    return Cancelable { into.clearOnDetach() }
}
