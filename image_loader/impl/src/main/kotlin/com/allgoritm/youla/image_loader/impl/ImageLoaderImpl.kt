package com.allgoritm.youla.image_loader.impl

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.view.doOnPreDraw
import com.allgoritm.youla.image_loader.api.Cancelable
import com.allgoritm.youla.image_loader.api.ImageLoader
import com.allgoritm.youla.image_loader.api.MemoryPolicy
import com.allgoritm.youla.image_loader.api.NetworkPolicy
import com.allgoritm.youla.image_loader.api.Priority
import com.allgoritm.youla.image_loader.api.UrlResize
import com.allgoritm.youla.image_loader.api.builder.ImageViewBuilder
import com.allgoritm.youla.image_loader.api.builder.TargetBuilder
import com.allgoritm.youla.image_loader.api.builder.UrlResizeImageViewBuilder
import com.allgoritm.youla.image_loader.impl.builder.ImageViewBuilderImpl
import com.allgoritm.youla.image_loader.impl.builder.TargetBuilderImpl
import com.allgoritm.youla.image_loader.impl.builder.UrlResizeImageViewBuilderImpl
import com.allgoritm.youla.image_loader.impl.transformation.CircleTransformation
import com.allgoritm.youla.image_loader.impl.transformation.RoundedTransformation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import java.io.File
import javax.inject.Inject
import com.squareup.picasso.MemoryPolicy as PicassoMemoryPolicy
import com.squareup.picasso.NetworkPolicy as PicassoNetworkPolicy
import com.squareup.picasso.Picasso.Priority as PicassoPriority

private const val ORIG_URL_PART = "images/orig"

class ImageLoaderImpl @Inject constructor(
    private val picasso: Picasso
) : ImageLoader {

    override fun load(file: File, build: TargetBuilder.() -> Unit): Cancelable {
        return picasso
            .load(file)
            .loadForTarget(build)
    }

    override fun load(file: File, imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable {
        return picasso
            .load(file)
            .loadForImageView(imageView, build)
    }

    override fun load(@DrawableRes drawableId: Int, build: TargetBuilder.() -> Unit): Cancelable {
        return picasso
            .load(drawableId)
            .loadForTarget(build)
    }

    override fun load(@DrawableRes drawableId: Int, imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable {
        return picasso
            .load(drawableId)
            .loadForImageView(imageView, build)
    }

    override fun load(url: String?, build: TargetBuilder.() -> Unit): Cancelable {
        val isUrlNullOrBlank = url.isNullOrBlank()
        val requestCreator = if (isUrlNullOrBlank) picasso.load(null as String?) else picasso.load(url)
        return requestCreator.loadForTarget(build)
    }

    override fun load(url: String?, imageView: ImageView, build: UrlResizeImageViewBuilder.() -> Unit): Cancelable {
        val urlResizeImageViewBuilder = UrlResizeImageViewBuilderImpl()
        urlResizeImageViewBuilder.build()
        val isUrlNullOrBlank = url.isNullOrBlank()
        if (isUrlNullOrBlank) {
            return picasso
                .load(null as String?)
                .loadForImageView(imageView, urlResizeImageViewBuilder)
        }
        when (urlResizeImageViewBuilder.urlResize) {
            UrlResize.NO -> {
                return picasso
                    .load(url)
                    .loadForImageView(imageView, urlResizeImageViewBuilder)
            }
            UrlResize.ORIGINAL_RATIO -> return loadWithResize(url!!, imageView, true, urlResizeImageViewBuilder)
            UrlResize.SCALED_RATIO -> return loadWithResize(url!!, imageView, false, urlResizeImageViewBuilder)
            null -> {
                return picasso
                    .load(url)
                    .loadForImageView(imageView, urlResizeImageViewBuilder)
            }
        }
    }

    override fun load(uri: Uri?, build: TargetBuilder.() -> Unit): Cancelable {
        return picasso
            .load(uri)
            .loadForTarget(build)
    }

    override fun load(uri: Uri?, imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable {
        return picasso
            .load(uri)
            .loadForImageView(imageView, build)
    }

    private fun getResizedUrl(url: String, width: Int, height: Int, originalRatio: Boolean): String {
        if (width > 0 && height > 0) {
            val containsOrigUrlPart = url.contains(ORIG_URL_PART)
            if (containsOrigUrlPart) {
                if (originalRatio) {
                    return url.replace(ORIG_URL_PART, "images/${width}_${height}_out")
                } else {
                    return url.replace(ORIG_URL_PART, "images/${width}_${height}")
                }
            }
        }
        return url
    }

    private fun loadWithResize(
        url: String,
        imageView: ImageView,
        originalRatio: Boolean,
        urlResizeImageViewBuilder: UrlResizeImageViewBuilderImpl
    ): Cancelable {
        setPlaceholder(imageView, urlResizeImageViewBuilder.placeholder)
        var cancelable: Cancelable? = null
        val oneShotPreDrawListener = imageView.doOnPreDraw {
            val newUrl = getResizedUrl(url, it.width, it.height, originalRatio)
            cancelable = picasso
                .load(newUrl)
                .loadForImageView(imageView, urlResizeImageViewBuilder)
        }
        return Cancelable {
            oneShotPreDrawListener.removeListener()
            cancelable?.cancel()
            cancelable = null
        }
    }

    private fun setPlaceholder(imageView: ImageView, placeholder: Placeholder?) {
        when (placeholder) {
            is Placeholder.Drawable -> imageView.setImageDrawable(placeholder.drawable)
            is Placeholder.Resource -> imageView.setImageResource(placeholder.drawableId)
        }
    }

    private fun RequestCreator.loadForImageView(imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable {
        val imageViewBuilder = ImageViewBuilderImpl()
        imageViewBuilder.build()
        return loadForImageView(imageView, imageViewBuilder)
    }

    private fun RequestCreator.loadForImageView(imageView: ImageView, imageViewBuilder: ImageViewBuilderImpl): Cancelable {
        val callback = object : Callback {

            override fun onSuccess() {
                imageViewBuilder.onSuccess?.invoke()
            }

            override fun onError() {
                imageViewBuilder.onError?.invoke()
            }

        }
        this
            .centerCrop(imageViewBuilder.centerCrop)
            .centerInside(imageViewBuilder.centerInside)
            .error(imageViewBuilder.error)
            .fit(imageViewBuilder.fit)
            .memoryPolicy(imageViewBuilder.memoryPolicy)
            .networkPolicy(imageViewBuilder.networkPolicy)
            .noFade(imageViewBuilder.noFade)
            .onlyScaleDown(imageViewBuilder.onlyScaleDown)
            .placeholder(imageViewBuilder.placeholder)
            .priority(imageViewBuilder.priority)
            .resize(imageViewBuilder.resize)
            .transformation(imageViewBuilder.transformation)
            .into(imageView, callback)
        return Cancelable { picasso.cancelRequest(imageView) }
    }

    private fun RequestCreator.loadForTarget(build: TargetBuilder.() -> Unit): Cancelable {
        val targetBuilder = TargetBuilderImpl()
        targetBuilder.build()
        val target = object : Target {

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                targetBuilder.onBitmapLoaded?.invoke(bitmap)
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
                targetBuilder.onBitmapFailed?.invoke(errorDrawable)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                targetBuilder.onPrepareLoad?.invoke(placeHolderDrawable)
            }

        }
        this
            .error(targetBuilder.error)
            .memoryPolicy(targetBuilder.memoryPolicy)
            .networkPolicy(targetBuilder.networkPolicy)
            .priority(targetBuilder.priority)
            .resize(targetBuilder.resize)
            .transformation(targetBuilder.transformation)
            .into(target)
        return Cancelable { picasso.cancelRequest(target) }
    }

    private fun RequestCreator.centerCrop(centerCrop: Boolean): RequestCreator {
        if (centerCrop) centerCrop()
        return this
    }

    private fun RequestCreator.centerInside(centerInside: Boolean): RequestCreator {
        if (centerInside) centerInside()
        return this
    }

    private fun RequestCreator.error(error: Error?): RequestCreator {
        when (error) {
            is Error.Drawable -> return error(error.drawable)
            is Error.Resource -> return error(error.drawableId)
            null -> return this
        }
    }

    private fun RequestCreator.fit(fit: Boolean): RequestCreator {
        if (fit) fit()
        return this
    }

    private fun RequestCreator.memoryPolicy(memoryPolicy: MemoryPolicy?): RequestCreator {
        when (memoryPolicy) {
            MemoryPolicy.NO_CACHE -> return memoryPolicy(PicassoMemoryPolicy.NO_CACHE)
            MemoryPolicy.NO_STORE -> return memoryPolicy(PicassoMemoryPolicy.NO_STORE)
            null -> return this
        }
    }

    private fun RequestCreator.networkPolicy(networkPolicy: NetworkPolicy?): RequestCreator {
        when (networkPolicy) {
            NetworkPolicy.NO_CACHE -> return networkPolicy(PicassoNetworkPolicy.NO_CACHE)
            NetworkPolicy.NO_STORE -> return networkPolicy(PicassoNetworkPolicy.NO_STORE)
            NetworkPolicy.OFFLINE -> return networkPolicy(PicassoNetworkPolicy.OFFLINE)
            null -> return this
        }
    }

    private fun RequestCreator.noFade(noFade: Boolean): RequestCreator {
        if (noFade) noFade()
        return this
    }

    private fun RequestCreator.onlyScaleDown(onlyScaleDown: Boolean): RequestCreator {
        if (onlyScaleDown) onlyScaleDown()
        return this
    }

    private fun RequestCreator.placeholder(placeholder: Placeholder?): RequestCreator {
        when (placeholder) {
            is Placeholder.Drawable -> return placeholder(placeholder.drawable)
            is Placeholder.Resource -> return placeholder(placeholder.drawableId)
            null -> return this
        }
    }

    private fun RequestCreator.priority(priority: Priority?): RequestCreator {
        when (priority) {
            Priority.HIGH -> return priority(PicassoPriority.HIGH)
            Priority.LOW -> return priority(PicassoPriority.LOW)
            Priority.NORMAL -> return priority(PicassoPriority.NORMAL)
            null -> return this
        }
    }

    private fun RequestCreator.resize(resize: Resize?): RequestCreator {
        resize ?: return this
        return resize(resize.width, resize.height)
    }

    private fun RequestCreator.transformation(transformation: Transformation?): RequestCreator {
        when (transformation) {
            is Transformation.Circle -> {
                val circleTransformation = CircleTransformation(transformation.stroke)
                return transform(circleTransformation)
            }
            is Transformation.Rounded -> {
                val roundedTransformation = RoundedTransformation(
                    transformation.leftBottomRadius,
                    transformation.leftTopRadius,
                    transformation.rightBottomRadius,
                    transformation.rightTopRadius,
                    transformation.stroke
                )
                return transform(roundedTransformation)
            }
            null -> return this
        }
    }

}