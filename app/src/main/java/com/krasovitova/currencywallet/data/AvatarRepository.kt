package com.krasovitova.currencywallet.data

import com.krasovitova.currencywallet.api.ImageApi
import com.krasovitova.currencywallet.avatar.ImageUi
import com.krasovitova.currencywallet.avatar.mapToUi
import javax.inject.Inject

class AvatarRepository @Inject constructor(
    private val imageApi: ImageApi
) {

    suspend fun getImages(): List<ImageUi> {
        return imageApi.getImages().mapToUi()
    }
}