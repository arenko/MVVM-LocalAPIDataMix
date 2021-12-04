package com.arenko.rijksapp

import com.arenko.rijksapp.data.model.ArtImage
import com.arenko.rijksapp.data.model.ArtObject
import com.arenko.rijksapp.domain.repository.RijksRepoInterface

class FakeArtRepository : RijksRepoInterface {
    private val artObjects = mutableListOf<ArtObject>()
    private val artObject = ArtObject("SK-C", "title3", "maker3", "desc3", ArtImage(""))

    init {
        artObjects.add(ArtObject("SK-A", "title1", "maker1", "desc1", ArtImage("")))
        artObjects.add(ArtObject("SK-B", "title2", "maker2", "desc2", ArtImage("")))
    }


    override suspend fun getArtObjects(page: Int): List<ArtObject> {
        return artObjects
    }

    override suspend fun getArtObjectsDetail(code: String): ArtObject {
        return artObject
    }

    override fun cleanDatabase() {

    }


}