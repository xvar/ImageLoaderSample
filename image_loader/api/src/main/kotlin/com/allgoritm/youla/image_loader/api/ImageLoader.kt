package com.allgoritm.youla.image_loader.api

import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.allgoritm.youla.image_loader.api.builder.ImageViewBuilder
import com.allgoritm.youla.image_loader.api.builder.TargetBuilder
import com.allgoritm.youla.image_loader.api.builder.UrlResizeImageViewBuilder
import java.io.File

interface ImageLoader {

    fun load(file: File, build: TargetBuilder.() -> Unit): Cancelable

    fun load(file: File, imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable

    fun load(@DrawableRes drawableId: Int, build: TargetBuilder.() -> Unit): Cancelable

    fun load(@DrawableRes drawableId: Int, imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable

    fun load(url: String?, build: TargetBuilder.() -> Unit): Cancelable

    fun load(url: String?, imageView: ImageView, build: UrlResizeImageViewBuilder.() -> Unit): Cancelable

    fun load(uri: Uri?, build: TargetBuilder.() -> Unit): Cancelable

    fun load(uri: Uri?, imageView: ImageView, build: ImageViewBuilder.() -> Unit): Cancelable

}