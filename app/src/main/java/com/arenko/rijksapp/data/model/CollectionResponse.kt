package com.arenko.rijksapp.data.model

import com.google.gson.annotations.Expose

data class CollectionResponse(
    @Expose
    val artObjects: ArrayList<ArtObject>
)