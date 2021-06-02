package com.allgoritm.youla.image_loader.coil_impl

import android.net.Uri
import android.widget.ImageView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.ImageResult
import com.allgoritm.youla.image_loader.api.Cancelable
import com.allgoritm.youla.image_loader.api.builder.ImageViewBuilder
import com.allgoritm.youla.image_loader.api.builder.TargetBuilder
import com.allgoritm.youla.image_loader.api.builder.UrlResizeImageViewBuilder
import com.allgoritm.youla.image_loader.impl.Error
import com.allgoritm.youla.image_loader.impl.builder.UrlResizeImageViewBuilderImpl
import java.io.File

class CoilImageLoaderImpl(
    private val coil: ImageLoader
) : com.allgoritm.youla.image_loader.api.ImageLoader {

    override fun load(file: File, build: TargetBuilder.() -> Unit): Cancelable {
        TODO("Not yet implemented")
    }

    override fun load(
        file: File,
        imageView: ImageView,
        build: ImageViewBuilder.() -> Unit
    ): Cancelable {
        TODO("Not yet implemented")
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
        val resizeImageViewBuilder = UrlResizeImageViewBuilderImpl()
        resizeImageViewBuilder.build()
        val imageRequestBuilder = ImageRequest.Builder(imageView.context)
        //set error
        when (val error = resizeImageViewBuilder.error) {
            is Error.Drawable -> imageRequestBuilder.error(error.drawable)
            is Error.Resource -> imageRequestBuilder.error(error.drawableId)
        }
        imageRequestBuilder.listener(object : ImageRequest.Listener {
            override fun onCancel(request: ImageRequest) {
                super.onCancel(request)
            }

            override fun onError(request: ImageRequest, throwable: Throwable) {
                super.onError(request, throwable)
                resizeImageViewBuilder.onError?.invoke()
            }

            override fun onStart(request: ImageRequest) {
                super.onStart(request)
            }

            override fun onSuccess(request: ImageRequest, metadata: ImageResult.Metadata) {
                super.onSuccess(request, metadata)
                resizeImageViewBuilder.onSuccess?.invoke()
            }
        })
        imageRequestBuilder.data(url)
            .target(imageView)

        val disposable = coil.enqueue(imageRequestBuilder.build())

        return Cancelable { disposable.dispose() }
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