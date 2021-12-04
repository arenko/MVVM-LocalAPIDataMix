package com.arenko.rijksapp.data.datasource

import com.arenko.rijksapp.api.RijksService
import com.arenko.rijksapp.data.model.CollectionResponse
import com.arenko.rijksapp.data.model.DetailResponse
import retrofit2.Response
import javax.inject.Inject

class ArtsRemoteDataSource @Inject constructor(private val rijksService: RijksService) {

    suspend fun getArtObjects(page: Int): Response<CollectionResponse> =
        rijksService.getArtObjects(page)

    suspend fun getArtObjectDetail(code:String): Response<DetailResponse> =
        rijksService.getArtObjectDetail(code)

}