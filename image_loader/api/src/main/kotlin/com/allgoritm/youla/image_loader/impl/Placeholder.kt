package com.allgoritm.youla.image_loader.impl

import androidx.annotation.DrawableRes
import android.graphics.drawable.Drawable as AndroidDrawable

sealed class Placeholder {

    class Drawable(
        val drawable: AndroidDrawable
    ) : Placeholder()

    class Resource(
        @DrawableRes val drawableId: Int
    ) : Placeholder()

}