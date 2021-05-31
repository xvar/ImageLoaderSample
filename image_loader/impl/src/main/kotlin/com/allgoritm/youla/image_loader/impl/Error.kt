package com.allgoritm.youla.image_loader.impl

import androidx.annotation.DrawableRes
import android.graphics.drawable.Drawable as AndroidDrawable

internal sealed class Error {

    class Drawable(
        val drawable: AndroidDrawable
    ) : Error()

    class Resource(
        @DrawableRes val drawableId: Int
    ) : Error()

}