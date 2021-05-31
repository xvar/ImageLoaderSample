package com.allgoritm.youla.image_loader.impl

internal sealed class Transformation {

    class Circle(
        val stroke: Stroke? = null
    ) : Transformation()

    class Rounded(
        val leftBottomRadius: Int,
        val leftTopRadius: Int,
        val rightBottomRadius: Int,
        val rightTopRadius: Int,
        val stroke: Stroke? = null
    ) : Transformation()

}