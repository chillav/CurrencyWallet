package com.krasovitova.currencywallet.api

import com.krasovitova.currencywallet.Constants.FLICKR_API_KEY
import com.krasovitova.currencywallet.avatar.PhotosRequest
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    @GET("?method=flickr.interestingness.getList")
    suspend fun getImages(
        @Query("api_key") apiKey: String = FLICKR_API_KEY,
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: String = "1"
    ): PhotosRequest
}