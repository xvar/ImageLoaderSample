package com.allgoritm.youla.image_loader.impl.builder

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.allgoritm.youla.image_loader.api.builder.ImageViewBuilder
import com.allgoritm.youla.image_loader.impl.Placeholder

open class ImageViewBuilderImpl : BaseBuilderImpl(), ImageViewBuilder {

    var centerCrop = false
    var centerInside = false
    var fit = false
    var noFade = false
    var onlyScaleDown = false
    var placeholder: Placeholder? = null
    var onError: (() -> Unit)? = null
    var onSuccess: (() -> Unit)? = null

    override fun centerCrop() {
        centerCrop = true
    }

    override fun centerInside() {
        centerInside = true
    }

    override fun fit() {
        fit = true
    }

    override fun noFade() {
        noFade = true
    }

    override fun onError(callback: () -> Unit) {
        onError = callback
    }

    override fun onSuccess(callback: () -> Unit) {
        onSuccess = callback
    }

    override fun onlyScaleDown() {
        onlyScaleDown = true
    }

    override fun placeholder(drawable: Drawable) {
        placeholder = Placeholder.Drawable(drawable)
    }

    override fun placeholder(@DrawableRes drawableId: Int) {
        placeholder = Placeholder.Resource(drawableId)
    }

}