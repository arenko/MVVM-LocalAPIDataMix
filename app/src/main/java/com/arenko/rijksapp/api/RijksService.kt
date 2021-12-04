package com.arenko.rijksapp.api

import com.arenko.rijksapp.data.Constants
import com.arenko.rijksapp.data.model.CollectionResponse
import com.arenko.rijksapp.data.model.DetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksService {

    @GET("collection?key=${Constants.API_KEY}")
    suspend fun getArtObjects(@Query("p") page: Int): Response<CollectionResponse>

    @GET("collection/{code}?key=${Constants.API_KEY}")
    suspend fun getArtObjectDetail(@Path("code") code: String): Response<DetailResponse>
}