package com.allgoritm.youla.image_loader.impl.transformation

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import com.allgoritm.youla.image_loader.impl.Stroke
import com.squareup.picasso.Transformation

internal class RoundedTransformation(
    private val leftBottomRadius: Int,
    private val leftTopRadius: Int,
    private val rightBottomRadius: Int,
    private val rightTopRadius: Int,
    private val stroke: Stroke?
) : Transformation {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val key: String

    private val Int.asFloat: Float
        get() = this.toFloat()

    init {
        borderPaint.style = Paint.Style.STROKE
        val stringBuilder = StringBuilder()
        stringBuilder.append("RoundedTransformation(")
        stringBuilder.append("leftBottomRadius = $leftBottomRadius")
        stringBuilder.append(", leftTopRadius = $leftTopRadius")
        stringBuilder.append(", rightBottomRadius = $rightBottomRadius")
        stringBuilder.append(", rightTopRadius = $rightTopRadius")
        stroke?.apply {
            borderPaint.color = color
            borderPaint.strokeWidth = width * 2F
            stringBuilder.append(", stroke = Stroke(color = $color, width = $width)")
        }
        stringBuilder.append(")")
        key = stringBuilder.toString()
    }

    override fun transform(source: Bitmap): Bitmap {
        val oldBitmap = copyIfNeeded(source)
        val oldCanvas = Canvas(oldBitmap)
        path.reset()
        val rectF = RectF(0F, 0F, oldBitmap.width.asFloat, oldBitmap.height.asFloat)
        val radii = floatArrayOf(
            leftTopRadius.asFloat,
            leftTopRadius.asFloat,
            rightTopRadius.asFloat,
            rightTopRadius.asFloat,
            rightBottomRadius.asFloat,
            rightBottomRadius.asFloat,
            leftBottomRadius.asFloat,
            leftBottomRadius.asFloat
        )
        path.addRoundRect(rectF, radii, Path.Direction.CW)
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