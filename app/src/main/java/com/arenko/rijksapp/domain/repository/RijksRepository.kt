package com.arenko.rijksapp.domain.repository

import com.arenko.rijksapp.data.datasource.ArtsLocalDataSource
import com.arenko.rijksapp.data.datasource.ArtsRemoteDataSource
import com.arenko.rijksapp.data.model.ArtObject
import javax.inject.Inject


class RijksRepository @Inject
constructor(
    private val artsRemoteDataSource: ArtsRemoteDataSource,
    private val artsLocalDataSource: ArtsLocalDataSource
) : RijksRepoInterface {

    override suspend fun getArtObjects(page: Int): List<ArtObject> {
        return getArtObjectsFromDB(page)
    }

    suspend fun getArtObjectsFromAPI(page: Int): List<ArtObject> {
        lateinit var artList: List<ArtObject>
        try {
            val response = artsRemoteDataSource.getArtObjects(page)
            val body = response.body()
            if (body != null) {
                for (art in body.artObjects) {
                    art.page = page
                }
                artList = body.artObjects
            }
        } catch (exception: Exception) {
        }
        return artList
    }

    suspend fun getArtObjectsFromDB(page: Int): List<ArtObject> {
        lateinit var artList: List<ArtObject>
        try {
            artList = artsLocalDataSource.getArtsFromDB(page)
        } catch (exception: Exception) {
        }
        if (artList.size > 0) {
            return artList
        } else {
            artList = getArtObjectsFromAPI(page)
            artsLocalDataSource.saveArtsToDB(artList)
        }

        return artList
    }

    override fun cleanDatabase() {
        artsLocalDataSource.clearAll()
    }

    override suspend fun getArtObjectsDetail(code: String): ArtObject {
        lateinit var art :ArtObject
        try {
            val response = artsRemoteDataSource.getArtObjectDetail(code)
            val body = response.body()
            if (body != null) {
                art = body.artObject
            }
        } catch (exception: Exception) {
        }
        return art
    }
}