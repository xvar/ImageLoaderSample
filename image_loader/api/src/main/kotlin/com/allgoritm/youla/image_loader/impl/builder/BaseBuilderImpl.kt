package com.allgoritm.youla.image_loader.impl.builder

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.allgoritm.youla.image_loader.api.MemoryPolicy
import com.allgoritm.youla.image_loader.api.NetworkPolicy
import com.allgoritm.youla.image_loader.api.Priority
import com.allgoritm.youla.image_loader.api.builder.BaseBuilder
import com.allgoritm.youla.image_loader.impl.Error
import com.allgoritm.youla.image_loader.impl.Resize
import com.allgoritm.youla.image_loader.impl.Stroke
import com.allgoritm.youla.image_loader.impl.Transformation

open class BaseBuilderImpl : BaseBuilder {

    var error: Error? = null
    var memoryPolicy: MemoryPolicy? = null
    var networkPolicy: NetworkPolicy? = null
    var priority: Priority? = null
    var resize: Resize? = null
    var transformation: Transformation? = null

    override fun error(drawable: Drawable) {
        error = Error.Drawable(drawable)
    }

    override fun error(@DrawableRes drawableId: Int) {
        error = Error.Resource(drawableId)
    }

    override fun memoryPolicy(memoryPolicy: MemoryPolicy) {
        this.memoryPolicy = memoryPolicy
    }

    override fun networkPolicy(networkPolicy: NetworkPolicy) {
        this.networkPolicy = networkPolicy
    }

    override fun priority(priority: Priority) {
        this.priority = priority
    }

    override fun resize(width: Int, height: Int) {
        resize = Resize(width, height)
    }

    override fun transformationCircle() {
        transformation = Transformation.Circle()
    }

    override fun transformationCircle(@ColorInt color: Int, width: Int) {
        val stroke = Stroke(color, width)
        transformation = Transformation.Circle(stroke)
    }

    override fun transformationRounded(radius: Int) {
        transformationRounded(radius, radius, radius, radius)
    }

    override fun transformationRounded(radius: Int, @ColorInt color: Int, width: Int) {
        transformationRounded(radius, radius, radius, radius, color, width)
    }

    override fun transformationRounded(leftBottomRadius: Int, leftTopRadius: Int, rightBottomRadius: Int, rightTopRadius: Int) {
        transformation = Transformation.Rounded(leftBottomRadius, leftTopRadius, rightBottomRadius, rightTopRadius)
    }

    override fun transformationRounded(
        leftBottomRadius: Int,
        leftTopRadius: Int,
        rightBottomRadius: Int,
        rightTopRadius: Int,
        @ColorInt color: Int,
        width: Int
    ) {
        val stroke = Stroke(color, width)
        transformation = Transformation.Rounded(leftBottomRadius, leftTopRadius, rightBottomRadius, rightTopRadius, stroke)
    }

}