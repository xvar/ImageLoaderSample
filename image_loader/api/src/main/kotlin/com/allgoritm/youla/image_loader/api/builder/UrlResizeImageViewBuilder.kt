package com.allgoritm.youla.image_loader.api.builder

import com.allgoritm.youla.image_loader.api.UrlResize

interface UrlResizeImageViewBuilder : ImageViewBuilder {

    fun urlResize(urlResize: UrlResize)

}