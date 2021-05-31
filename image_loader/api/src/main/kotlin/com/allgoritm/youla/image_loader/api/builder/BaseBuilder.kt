package com.allgoritm.youla.image_loader.api.builder

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.allgoritm.youla.image_loader.api.MemoryPolicy
import com.allgoritm.youla.image_loader.api.NetworkPolicy
import com.allgoritm.youla.image_loader.api.Priority

interface BaseBuilder {

    fun error(drawable: Drawable)

    fun error(@DrawableRes drawableId: Int)

    fun memoryPolicy(memoryPolicy: MemoryPolicy)

    fun networkPolicy(networkPolicy: NetworkPolicy)

    fun priority(priority: Priority)

    fun resize(width: Int, height: Int)

    fun transformationCircle()

    fun transformationCircle(@ColorInt color: Int, width: Int)

    fun transformationRounded(radius: Int)

    fun transformationRounded(radius: Int, @ColorInt color: Int, width: Int)

    fun transformationRounded(
        leftBottomRadius: Int,
        leftTopRadius: Int,
        rightBottomRadius: Int,
        rightTopRadius: Int
    )

    fun transformationRounded(
        leftBottomRadius: Int,
        leftTopRadius: Int,
        rightBottomRadius: Int,
        rightTopRadius: Int,
        @ColorInt color: Int,
        width: Int
    )

}