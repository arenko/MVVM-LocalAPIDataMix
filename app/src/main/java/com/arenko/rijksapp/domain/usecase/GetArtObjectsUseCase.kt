package com.arenko.rijksapp.domain.usecase

import com.arenko.rijksapp.data.model.ArtObject
import com.arenko.rijksapp.domain.repository.RijksRepoInterface
import javax.inject.Inject

class GetArtObjectsUseCase @Inject constructor(private val rijksRepoInterface: RijksRepoInterface) {
    suspend fun getArtObjects(page: Int): List<ArtObject> = rijksRepoInterface.getArtObjects(page)
    suspend fun getArtObjectsDetail(code:String): ArtObject = rijksRepoInterface.getArtObjectsDetail(code)
    fun cleanDB() = rijksRepoInterface.cleanDatabase()
}