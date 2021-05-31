package com.allgoritm.youla.image_loader.impl.builder

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.allgoritm.youla.image_loader.api.builder.TargetBuilder

internal class TargetBuilderImpl : BaseBuilderImpl(), TargetBuilder {

    var onBitmapFailed: ((Drawable?) -> Unit)? = null
    var onBitmapLoaded: ((Bitmap) -> Unit)? = null
    var onPrepareLoad: ((Drawable?) -> Unit)? = null

    override fun onBitmapFailed(callback: (Drawable?) -> Unit) {
        onBitmapFailed = callback
    }

    override fun onBitmapLoaded(callback: (Bitmap) -> Unit) {
        onBitmapLoaded = callback
    }

    override fun onPrepareLoad(callback: (Drawable?) -> Unit) {
        onPrepareLoad = callback
    }

}