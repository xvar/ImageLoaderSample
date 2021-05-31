package com.allgoritm.youla.image_loader.api.builder

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

interface TargetBuilder : BaseBuilder {

    fun onBitmapFailed(callback: (Drawable?) -> Unit)

    fun onBitmapLoaded(callback: (Bitmap) -> Unit)

    fun onPrepareLoad(callback: (Drawable?) -> Unit)

}