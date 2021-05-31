package com.allgoritm.youla.image_loader.impl.transformation

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import com.allgoritm.youla.image_loader.impl.Stroke
import com.squareup.picasso.Transformation
import kotlin.math.min

internal class CircleTransformation(
    private val stroke: Stroke?
) : Transformation {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val key: String

    init {
        borderPaint.style = Paint.Style.STROKE
        val stringBuilder = StringBuilder()
        stringBuilder.append("CircleTransformation(")
        stroke?.apply {
            borderPaint.color = color
            borderPaint.strokeWidth = width * 2F
            stringBuilder.append("stroke = Stroke(color = $color, width = $width)")
        }
        stringBuilder.append(")")
        key = stringBuilder.toString()
    }

    override fun transform(source: Bitmap): Bitmap {
        val oldBitmap = copyIfNeeded(source)
        val oldCanvas = Canvas(oldBitmap)
        path.reset()
        val radius = min(oldBitmap.width, oldBitmap.height) / 2F
        path.addCircle(oldBitmap.width / 2F, oldBitmap.height / 2F, radius, Path.Direction.CW)
        stroke?.apply { oldCanvas.drawPath(path, borderPaint) }
        val newBitmap = Bitmap.createBitmap(oldBitmap.width, oldBitmap.height, Bitmap.Config.ARGB_8888)
        val newCanvas = Canvas(newBitmap)
        pathPaint.shader = BitmapShader(oldBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        newCanvas.drawPath(path, pathPaint)
        pathPaint.shader = null
        oldBitmap.recycle()
        return newBitmap
    }

    override fun key(): String {
        return key
    }

    private fun copyIfNeeded(oldBitmap: Bitmap): Bitmap {
        if (oldBitmap.isMutable) return oldBitmap
        val newBitmap = oldBitmap.copy(oldBitmap.config, true)
        oldBitmap.recycle()
        return newBitmap
    }

}