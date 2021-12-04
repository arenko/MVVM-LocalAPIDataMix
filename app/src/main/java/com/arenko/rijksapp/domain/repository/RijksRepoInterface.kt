package com.arenko.rijksapp.domain.repository

import com.arenko.rijksapp.data.model.ArtObject


interface RijksRepoInterface {

    suspend fun getArtObjects(page: Int): List<ArtObject>
    suspend fun getArtObjectsDetail(code: String): ArtObject
    fun cleanDatabase()

}