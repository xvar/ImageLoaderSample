package com.allgoritm.youla.image_loader.impl.builder

import com.allgoritm.youla.image_loader.api.UrlResize
import com.allgoritm.youla.image_loader.api.builder.UrlResizeImageViewBuilder

internal class UrlResizeImageViewBuilderImpl : ImageViewBuilderImpl(), UrlResizeImageViewBuilder {

    var urlResize: UrlResize? = null

    override fun urlResize(urlResize: UrlResize) {
        this.urlResize = urlResize
    }

}