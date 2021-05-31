package com.allgoritm.youla.image_loader.api.builder

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

interface ImageViewBuilder : BaseBuilder {

    fun centerCrop()

    fun centerInside()

    fun fit()

    fun noFade()

    fun onError(callback: () -> Unit)

    fun onSuccess(callback: () -> Unit)

    fun onlyScaleDown()

    fun placeholder(drawable: Drawable)

    fun placeholder(@DrawableRes drawableId: Int)

}